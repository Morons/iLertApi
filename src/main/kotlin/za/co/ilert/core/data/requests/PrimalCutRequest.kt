package za.co.ilert.core.data.requests

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import za.co.ilert.core.data.models.PrimalCut
import java.time.Instant
import java.time.ZoneOffset

@Serializable
data class PrimalCutRequest(
	val blockTestId: String,
	val primalCutType: Int,
	val actualCutWeight: Double,
	val marketSellPrice: Double, // Market related sell price, what the local market will pay for this.
	val timestamp: Long = Instant.now().atOffset(ZoneOffset.UTC).toEpochSecond(),
	val primalCutId: String = ObjectId().toString()
) {

	fun toPrimalCut(): PrimalCut {
		return PrimalCut(
			blockTestId = blockTestId,
			primalCutType = primalCutType,
			actualCutWeight = actualCutWeight,
			marketSellPrice = marketSellPrice,
			timestamp = timestamp,
			primalCutId = primalCutId
		)
	}
}