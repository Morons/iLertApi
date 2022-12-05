package za.co.ilert.core.data.models

data class Cut(
	val cutTypeId: String,
	val blockTestId: String,
	val actualCutWeight: Double,
	val marketSellPrice: Double, // Market related sell price, what the local market will pay for this.
	val salesPrice: Double,  // Overridden Sales Price or Suggested Sales Price Incl VAT
	val cutName: String,
	val displayName: String
)