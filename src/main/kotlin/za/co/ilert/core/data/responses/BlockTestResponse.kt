package za.co.ilert.core.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class BlockTestResponse(
	val carcassType: Int, // Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
	val carcassKgCostIncl: Double,
	val carcassWeight: Double,
	val carcassHangingWeight: Double,
	val carcassLoss: Double, // Weight after Cutting & W.Loss
	val carcassKgWeightLoss: Double,
	val weightLossParameter: Double,
	val cuttingLossParameter: Double,
	val waistParameter: Double,
	val percentDifferenceParameter: Double,
	val percentGpRequired: Double,
	val acceptablePriceVariance: Double,
	val trimmingWaste: Double,
	val measuredWeightAfterCuts: Double,
	val primalCuts: List<PrimalCutResponse>,
	val timestamp: Long,
	val blockTestId: String
) {
	val carcassCostIncl: Double
		get() = carcassKgCostIncl * carcassWeight

	val carcassEffectivePrice: Double
		get() = carcassCostIncl / carcassKgWeightLoss // (carcassKgPrice * carcassWeight) / carcassWeightLoss

	val percentCarcassWeightLoss: Double
		get() = (carcassWeight - carcassKgWeightLoss) / carcassWeight

	val adjustedKgCostKgIncl: Double
		get() = carcassCostIncl / carcassHangingWeight

	val cuttingLoss: Double = if (measuredWeightAfterCuts >= 0.0) carcassHangingWeight - measuredWeightAfterCuts else 0.0

	val percentWeightLoss: () -> Double = {
		if ((measuredWeightAfterCuts >= 0.0) && (trimmingWaste >= 0.0)) measuredWeightAfterCuts / trimmingWaste else 0.0
	}

	val percentCuttingLoss: () -> Double = {
		if (carcassHangingWeight >= 0.0) cuttingLoss / carcassHangingWeight else 0.0
	}
}