package za.co.ilert.core.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant.now
import java.time.ZoneOffset

@Serializable
data class BlockTest(
	val userId: String,
	val carcassType: Int, // Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
	val carcassKgCostIncl: Double,
	val carcassWeight: Double,
	val carcassHangingWeight: Double,
	val cutTrimWeight: Double = 0.0, // Weight after Cutting & W.Loss
	val carcassKgWeightLoss: Double = 0.0,
	val weightLossParameter: Double,
	val cuttingLossParameter: Double,
	val wasteParameter: Double,
	val percentDifferenceParameter: Double,
	val percentGpRequired: Double,
	val acceptablePriceVariance: Double,
	val trimmingWaste: Double,
	val measuredWeightAfterCuts: Double,
	val timestamp: Long = now().atOffset(ZoneOffset.UTC).toEpochSecond(),
	@BsonId
	val blockTestId: String = ObjectId().toString()
)