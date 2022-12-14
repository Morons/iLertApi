package za.co.ilert.core.data.websocket

import za.co.ilert.core.data.models.Message


data class WsServerMessage(
	val fromId: String,
	val toId: String,
	val text: String,
	val chatId: String?,
	val timestamp: Long
) {
	fun toMessage(): Message {
		return Message(
			fromId = fromId,
			toId = toId,
			text = text,
			chatId = chatId,
			timestamp = timestamp
		)
	}
}