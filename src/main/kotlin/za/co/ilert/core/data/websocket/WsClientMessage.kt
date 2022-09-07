package za.co.ilert.core.data.websocket

import za.co.ilert.core.data.models.Message


data class WsClientMessage(
	val toId: String,
	val text: String,
	val chatId: String?
) {
	fun toMessage(fromId: String): Message {
		return Message(
			fromId = fromId,
			toId = toId,
			text = text,
			chatId = chatId,
			timestamp = System.currentTimeMillis()
		)
	}
}
