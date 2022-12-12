package za.co.ilert.core.data.models

data class Cut(
	val cutId: String,
	val cutTypeId: String,
	val blockTestId: String,
	val actualCutWeight: Double,
	val marketSellPrice: Double, // Market related sell price, what the local market will pay for this.
	val marketValue: Double,
	val salesPrice: Double,  // Overridden Sales Price or Suggested Sales Price Incl VAT
	val actualValue:Double,
	val cutName: String,
	val displayName: String
)