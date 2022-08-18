package za.co.ilert.core.data.requests

data class GetChatByUsersRequest(
	val fromId: String,
	val toId: String
)
