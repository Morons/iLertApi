package za.co.ilert.core.data.requests


data class NewBlockTestRequest(
	val blockTestId: String,
	val userId: String,
	val organizationId: String,
	val carcassTypeId: String,
	val carcassKgCostIncl: Double,
	val carcassWeight: Double,
	val carcassHangingWeight: Double,
	val cutTrimWeight: Double = 0.0,
	val weightLossParameter: Double,
	val cuttingLossParameter: Double,
	val wasteParameter: Double,
	val percentGpRequired: Double,
	val notBalancingReason: String,
	val locked: Boolean,
	val timestamp: Long
)