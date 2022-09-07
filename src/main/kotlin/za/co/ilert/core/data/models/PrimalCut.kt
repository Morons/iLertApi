package za.co.ilert.core.data.models

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


data class PrimalCut(
	val primalCutType: Int,
	val actualCutWeight: Double,
	val marketSellPrice: Double, // Market related sell price, what the local market will pay for this.
	val name: String,
	val displayName: String,
	val mutable: Boolean = false
)