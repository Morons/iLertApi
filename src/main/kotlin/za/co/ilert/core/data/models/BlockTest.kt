package za.co.ilert.core.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant.now
import java.time.ZoneOffset

@Serializable
data class BlockTest(
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
	val timestamp: Long = now().atOffset(ZoneOffset.UTC).toEpochSecond(),
	@BsonId
	val blockTestId: String = ObjectId().toString()
)