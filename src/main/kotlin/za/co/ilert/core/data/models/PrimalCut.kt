package za.co.ilert.core.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant
import java.time.ZoneOffset

/**
 * @property primalCutType
 *
 * Beef Front Quarter 1xx
 * Beef Hind Quarter 2xx
 * Pork 3xx
 * Lamb 4xx
 * Chicken 5xx
 *
 **/

@Serializable
data class PrimalCut(
	val blockTestId: String,
	val primalCutType: Int,
	val actualCutWeight: Double,
	val marketSellPrice: Double, // Market related sell price, what the local market will pay for this.
	val timestamp: Long = Instant.now().atOffset(ZoneOffset.UTC).toEpochSecond(),
	@BsonId
	val primalCutId: String = ObjectId().toString()
)