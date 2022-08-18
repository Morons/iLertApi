package za.co.ilert.core.data.models

data class SubPrimal(
	val primalId: String,
	val cutWeight: Long,
	val marketSellPrice: Long, // Market related sell price, what the local market will pay for this.
) {
	val related: Long
		get() = cutWeight * marketSellPrice
}
