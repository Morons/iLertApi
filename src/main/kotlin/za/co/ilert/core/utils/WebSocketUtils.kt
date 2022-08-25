package za.co.ilert.core.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.java.KoinJavaComponent
import za.co.ilert.core.data.util.WebSocketObject
import za.co.ilert.core.data.websocket.WsClientMessage
import za.co.ilert.presentation.services.chat.ChatController


suspend fun handleWebSocket(
	ownUserId: String,
	chatController: ChatController,
	jsonString: String,
	type: Int
) {
	val json by KoinJavaComponent.inject<Json>(Json::class.java)
	when (type) {
		WebSocketObject.MESSAGE.ordinal -> {
			val message = json.decodeFromString<WsClientMessage>(jsonString)
			chatController.sendMessage(ownUserId = ownUserId, json = json, message = message)
		}
	}
}