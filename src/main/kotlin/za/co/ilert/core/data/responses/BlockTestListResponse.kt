package za.co.ilert.core.data.responses


data class BlockTestListResponse(
	val carcassTypeId: String, // Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
	val timestamp: Long,
	val blockTestId: String
)