package za.co.ilert.core.data.responses

import za.co.ilert.core.data.models.PrimalCut


data class BlockTestResponse(
	val carcassType: Int, // Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
	val carcassKgCostIncl: Double,
	val carcassWeight: Double,
	val carcassHangingWeight: Double,
	val cutTrimWeight: Double, // Weight after Cutting & W.Loss
	val carcassKgWeightLoss: Double,
	val weightLossParameter: Double,
	val cuttingLossParameter: Double,
	val waistParameter: Double,
	val percentDifferenceParameter: Double,
	val percentGpRequired: Double,
	val acceptablePriceVariance: Double,
	val trimmingWaste: Double,
	val measuredWeightAfterCuts: Double,
	val primalCuts: List<PrimalCut>,
	val timestamp: Long,
	val blockTestId: String,
	val carcassCostIncl: Double,
	val carcassEffectivePrice: Double,
	val percentCarcassWeightLoss: Double,
	val adjustedKgCostKgIncl: Double,
	val cuttingLoss: Double,
	val percentWeightLoss: Double,
	val percentCuttingLoss: Double
)