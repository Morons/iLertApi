package za.co.ilert.presentation.services.chat

import za.co.ilert.core.data.models.Message
import za.co.ilert.core.data.repository.chat.ChatRepository
import za.co.ilert.core.data.responses.ChatResponse
import za.co.ilert.core.util.Constants.DEFAULT_PAGE_SIZE

class ChatService(
	private val chatRepository: ChatRepository
) {

	suspend fun doesChatBelongToUser(chatId: String, userId: String): Boolean {
		return chatRepository.doesChatBelongToUser(chatId, userId)
	}

	suspend fun getMessagesForChat(chatId: String, page: Int, pageSize: Int = DEFAULT_PAGE_SIZE): List<Message> {
		return chatRepository.getMessagesForChat(chatId = chatId, page = page, pageSize = pageSize)
	}

	suspend fun getChatsForSelfPaged(ownUserId: String, page: Int = 0, pageSize: Int = DEFAULT_PAGE_SIZE):
			List<ChatResponse> {
		return chatRepository.getChatsForSelfPaged(ownUserId = ownUserId, page = page, pageSize = pageSize)
	}

}