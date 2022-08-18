package za.co.ilert.core.data.responses

data class ChatResponse(
	val chatId: String,
	val remoteUserId: String,
	val remoteUserName: String,
	val remoteAvatarAsString: String,
	val lastMessage: String,
	val lastMessageTimestamp: Long
)