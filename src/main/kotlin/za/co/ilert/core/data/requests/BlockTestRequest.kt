package za.co.ilert.core.data.requests

import za.co.ilert.core.data.models.BlockTest
import za.co.ilert.core.data.models.Cut


data class BlockTestRequest(
	val blockTestId: String,
	val userId: String,
	val organizationId: String,
	val carcassTypeId: String, // Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
	val carcassKgCostIncl: Double,
	val carcassWeight: Double,
	val carcassHangingWeight: Double,
	val cutTrimWeight: Double = 0.0, // Weight after Cutting & W.Loss
	val weightLossParameter: Double,
	val cuttingLossParameter: Double,
	val wasteParameter: Double,
	val percentGpRequired: Double,
	val accumulatedFairValueMarketRelated: Double = 0.0,
	val cuts: List<Cut>,
	val notBalancingReason: String,
	val locked: Boolean,
	val timestamp: Long
) {
	fun toBlockTest(): BlockTest {
		return BlockTest(
			blockTestId = blockTestId,
			userId = userId,
			organizationId = organizationId,
			carcassTypeId = carcassTypeId,
			carcassKgCostIncl = carcassKgCostIncl,
			carcassWeight = carcassWeight,
			carcassHangingWeight = carcassHangingWeight,
			cutTrimWeight = cutTrimWeight,
			weightLossParameter = weightLossParameter,
			cuttingLossParameter = cuttingLossParameter,
			wasteParameter = wasteParameter,
			percentGpRequired = percentGpRequired,
			accumulatedFairValueMarketRelated = accumulatedFairValueMarketRelated,
			cuts = cuts,
			notBalancingReason = notBalancingReason,
			locked = locked,
			timestamp = timestamp
		)
	}
}