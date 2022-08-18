package za.co.ilert.core.data.repository.chat

import za.co.ilert.core.data.models.Chat
import za.co.ilert.core.data.models.Message
import za.co.ilert.core.data.responses.ChatResponse

interface ChatRepository {

	suspend fun getMessagesForChat(chatId: String, page: Int, pageSize: Int): List<Message>

	suspend fun getChatsForSelfPaged(ownUserId: String, page: Int, pageSize: Int): List<ChatResponse>

	suspend fun doesChatBelongToUser(chatId: String, userId: String): Boolean

	suspend fun insertMessage(message: Message)

	suspend fun insertChat(fromId: String, toId: String, messageId: String)

	suspend fun getChatByUsers(fromId: String, toId: String): List<Chat>

	suspend fun doesChatByUsersExist(fromId: String, toId: String): Boolean

	suspend fun updateLastMessageIdForChat(chatId: String, lastMessageId: String)
}