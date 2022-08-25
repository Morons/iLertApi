package za.co.ilert.core.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
	val chatId: String,
	val remoteUserId: String,
	val remoteUserName: String,
	val remoteAvatarAsString: String,
	val lastMessage: String,
	val lastMessageTimestamp: Long
)