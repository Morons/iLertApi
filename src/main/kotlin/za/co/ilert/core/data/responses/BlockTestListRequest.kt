package za.co.ilert.core.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class BlockTestListRequest(
	val carcassType: Int, // Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
	val timestamp: Long,
	val blockTestId: String
)