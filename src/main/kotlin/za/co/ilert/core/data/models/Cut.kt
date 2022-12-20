package za.co.ilert.core.data.models

data class Cut(
	val cutId: String,
	val cutTypeId: String,
	val blockTestId: String,
	val actualCutWeight: Double,
	val marketSellPrice: Double,
	var marketValueXVat: Double,
	var costPerCutXVat: Double,
	var salesPriceInclVat: Double,
	var actualValueXVat:Double,
	val cutName: String
)