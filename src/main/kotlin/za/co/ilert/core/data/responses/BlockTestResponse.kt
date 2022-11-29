package za.co.ilert.core.data.responses

import za.co.ilert.core.data.models.Cut


data class BlockTestResponse(
	val carcassTypeId: String, // Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
	val carcassKgCostIncl: Double,
	val carcassWeight: Double,
	val carcassHangingWeight: Double,
	val cutTrimWeight: Double, // Weight after Cutting & W.Loss
	val weightLossParameter: Double,
	val cuttingLossParameter: Double,
	val waistParameter: Double,
	val percentGpRequired: Double,
	val cuts: List<Cut>,
	val timestamp: Long,
	val blockTestId: String
)