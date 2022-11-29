package za.co.ilert.core.data.requests

import org.bson.types.ObjectId
import za.co.ilert.core.data.models.BlockTest
import za.co.ilert.core.data.models.Cut
import java.time.Instant
import java.time.ZoneOffset


data class BlockTestRequest(
	val userId: String,
	val carcassTypeId: String, // Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
	val carcassKgCostIncl: Double,
	val carcassWeight: Double,
	val carcassHangingWeight: Double,
	val cutTrimWeight: Double, // Weight after Cutting & W.Loss
	val carcassKgWeightLoss: Double = 0.0,
	val weightLossParameter: Double,
	val cuttingLossParameter: Double,
	val wasteParameter: Double,
	val percentDifferenceParameter: Double,
	val percentGpRequired: Double,
	val acceptablePriceVariance: Double,
	val cuts: List<Cut>,
	val sumPrimalsWeight: Double = 0.0,
	val trimmingWaste: Double = 0.0,
	val measuredWeightAfterCuts: Double = 0.0,
	val timestamp: Long = Instant.now().atOffset(ZoneOffset.UTC).toEpochSecond(),
	val blockTestId: String = ObjectId().toString()
) {
	fun toBlockTest(): BlockTest {
		return BlockTest(
			userId = userId,
			carcassTypeId = carcassTypeId,
			carcassKgCostIncl = carcassKgCostIncl,
			carcassWeight = carcassWeight,
			carcassHangingWeight = carcassHangingWeight,
			cutTrimWeight = cutTrimWeight,
			weightLossParameter = weightLossParameter,
			cuttingLossParameter = cuttingLossParameter,
			wasteParameter = wasteParameter,
			percentGpRequired = percentGpRequired,
			cuts = cuts,
			timestamp = timestamp,
			blockTestId = blockTestId
		)
	}
}