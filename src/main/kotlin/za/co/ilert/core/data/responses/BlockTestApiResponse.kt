package za.co.ilert.core.data.responses

import za.co.ilert.core.data.models.Cut


data class BlockTestApiResponse(
	val blockTestId: String,
	val blockTestName: String,
	val userId: String,
	val organizationId: String,
	val carcassTypeId: String, // Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
	val carcassKgCostIncl: Double,
	val carcassWeight: Double,
	val carcassHangingWeight: Double,
	val cutTrimWeight: Double, // Weight after Cutting & W.Loss
	val weightLossParameter: Double,
	val cuttingLossParameter: Double,
	val wasteParameter: Double,
	val percentGpRequired: Double,
	val notBalancingReason: String,
	val cuts: List<Cut>,
	val locked: Boolean,
	val timestamp: Long
)