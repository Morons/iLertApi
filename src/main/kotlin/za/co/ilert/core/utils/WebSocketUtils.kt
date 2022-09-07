package za.co.ilert.core.utils

import com.google.gson.Gson
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
	val gson by KoinJavaComponent.inject<Gson>(Gson::class.java)
	when (type) {
		WebSocketObject.MESSAGE.ordinal -> {
			val message = gson.fromJsonOrNull(jsonString, WsClientMessage::class.java) ?: return
			chatController.sendMessage(ownUserId = ownUserId, gson = gson, message = message)
		}
	}
}