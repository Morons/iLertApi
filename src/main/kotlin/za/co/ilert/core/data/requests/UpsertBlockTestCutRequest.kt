package za.co.ilert.core.data.requests

data class UpsertBlockTestCutRequest(
	val cutId: String,
	val cutTypeId: String,
	val blockTestId: String,
	val actualCutWeight: Double,
	val marketSellPrice: Double, // Market related sell price, what the local market will pay for this.
	val costPerCutXVat: Double,
	val marketValue: Double,
	val salesPrice: Double,  // Overridden Sales Price or Suggested Sales Price Incl VAT
	val actualValue:Double,
	val cutName: String
)