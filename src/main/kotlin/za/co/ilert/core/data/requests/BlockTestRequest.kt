package za.co.ilert.core.data.requests

import kotlinx.serialization.Serializable
import za.co.ilert.core.data.models.BlockTest

@Serializable
data class BlockTestRequest(
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
	val timestamp: Long,
	val blockTestId: String
) {
	fun toBlockTest(): BlockTest {
		return BlockTest(
			carcassType = carcassType,
			carcassKgCostIncl = carcassKgCostIncl,
			carcassWeight = carcassWeight,
			carcassHangingWeight = carcassHangingWeight,
			carcassLoss = carcassLoss,
			carcassKgWeightLoss = carcassKgWeightLoss,
			weightLossParameter = weightLossParameter,
			cuttingLossParameter = cuttingLossParameter,
			waistParameter = waistParameter,
			percentDifferenceParameter = percentDifferenceParameter,
			percentGpRequired = percentGpRequired,
			acceptablePriceVariance = acceptablePriceVariance,
			trimmingWaste = trimmingWaste,
			measuredWeightAfterCuts = measuredWeightAfterCuts,
			timestamp = timestamp,
			blockTestId = blockTestId
		)
	}
}