package za.co.ilert.core.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class BlockTestResponse(
	val carcassType: Int, // Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
	val carcassKgCostIncl: Long,
	val carcassWeight: Long,
	val carcassHangingWeight: Long,
	val carcassLoss: Long, // Weight after Cutting & W.Loss
	val carcassKgWeightLoss: Long,
	val weightLossParameter: Long,
	val cuttingLossParameter: Long,
	val waistParameter: Long,
	val percentDifferenceParameter: Long,
	val percentGpRequired: Long,
	val acceptablePriceVariance: Long,
	val trimmingWaste: Long,
	val measuredWeightAfterCuts: Long,
	val primalCuts: List<PrimalCutResponse>,
	val timestamp: Long,
	val blockTestId: String
) {
	val carcassCostIncl: Long
		get() = carcassKgCostIncl * carcassWeight

	val carcassEffectivePrice: Long
		get() = carcassCostIncl / carcassKgWeightLoss // (carcassKgPrice * carcassWeight) / carcassWeightLoss

	val percentCarcassWeightLoss: Long
		get() = (carcassWeight - carcassKgWeightLoss) / carcassWeight

	val adjustedKgCostKgIncl: Long
		get() = carcassCostIncl / carcassHangingWeight

	val cuttingLoss: Long = if (measuredWeightAfterCuts >= 0L) carcassHangingWeight - measuredWeightAfterCuts else 0L

	val percentWeightLoss: () -> Long = {
		if ((measuredWeightAfterCuts >= 0L) && (trimmingWaste >= 0L)) measuredWeightAfterCuts / trimmingWaste else 0L
	}

	val percentCuttingLoss: () -> Long = {
		if (carcassHangingWeight >= 0L) cuttingLoss / carcassHangingWeight else 0L
	}
}