package za.co.ilert.core.data.models

/**
 * @property cutType
 *
 * Beef Front Quarter 1xx
 * Beef Hind Quarter 2xx
 * Pork 3xx
 * Lamb 4xx
 * Chicken 5xx
 *
 **/


data class Cuts(
	val cutType: String,
	val actualCutWeight: Double,
	val marketSellPrice: Double, // Market related sell price, what the local market will pay for this.
	val cutName: String,
	val displayName: String,
	val isMutable: Boolean = false
)