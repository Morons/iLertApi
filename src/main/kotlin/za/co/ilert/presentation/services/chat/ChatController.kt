package za.co.ilert.presentation.services.chat

import com.google.gson.Gson
import io.ktor.websocket.*
import za.co.ilert.core.data.models.Chat
import za.co.ilert.core.data.models.Message
import za.co.ilert.core.data.repository.chat.ChatRepository
import za.co.ilert.core.data.util.WebSocketObject.MESSAGE
import za.co.ilert.core.data.websocket.WsClientMessage
import za.co.ilert.core.data.websocket.WsServerMessage
import java.util.concurrent.ConcurrentHashMap

class ChatController(
	private val chatRepository: ChatRepository
) {

	private val onlineUsers = ConcurrentHashMap<String, WebSocketSession>()

	fun onJoin(ownUserId: String, socket: WebSocketSession) {
		onlineUsers[ownUserId] = socket
	}

	fun onDisconnect(userId: String) {
		if (onlineUsers.containsKey(userId))
			onlineUsers.remove(userId)
	}

	suspend fun sendMessage(ownUserId: String, gson: Gson, message: WsClientMessage): Boolean {
		val messageEntity: Message = message.toMessage(ownUserId)
		val wsServerMessage = WsServerMessage(
			fromId = ownUserId,
			toId = message.toId,
			text = message.text,
			timestamp = System.currentTimeMillis(),
			chatId = message.chatId
		)
		val frameText = gson.toJson(wsServerMessage)
		onlineUsers[ownUserId]?.send(Frame.Text(text = "${MESSAGE.ordinal}#$frameText"))
		onlineUsers[message.toId]?.send(Frame.Text(text = "${MESSAGE.ordinal}#$frameText"))
		chatRepository.insertMessage(messageEntity)  // No chatId yet
		if (!chatRepository.doesChatByUsersExist(ownUserId, message.toId)
		) {
			chatRepository.insertChat(ownUserId, message.toId, messageEntity.messageId)
			val chatId = chatRepository.getChatByUsers(ownUserId, message.toId).first().chatId
			chatRepository.updateLastMessageIdForChat(chatId = chatId, lastMessageId = messageEntity.messageId)
		} else {
			messageEntity.chatId?.let {
				chatRepository.updateLastMessageIdForChat(chatId = it, lastMessageId = messageEntity.messageId)
			}
		}
		return true
	}

	suspend fun getChatByUsers(fromId: String, toId: String): List<Chat> {
		return chatRepository.getChatByUsers(fromId, toId)
	}

	suspend fun doesChatByUsersExist(fromId: String, toId: String): Boolean {
		return chatRepository.doesChatByUsersExist(fromId, toId)
	}
}