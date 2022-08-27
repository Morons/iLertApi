package za.co.ilert.core.data.responses

import kotlinx.serialization.Serializable
import za.co.ilert.core.data.models.PrimalCut

@Serializable
data class PrimalCutResponse(
	val blockTestId: String,
	val primalCutType: Int,
	val actualCutWeight: Double,
	val marketSellPrice: Double, // Market related sell price, what the local market will pay for this.
	val timestamp: Long,
	val primalCutId: String
) {
	val marketRelatedPrice: Double
		get() = actualCutWeight * marketSellPrice

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