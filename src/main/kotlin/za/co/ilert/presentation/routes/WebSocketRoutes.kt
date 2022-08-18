package za.co.ilert.presentation.routes

import io.ktor.server.auth.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import za.co.ilert.core.data.util.userId
import za.co.ilert.core.util.Constants
import za.co.ilert.core.util.Constants.WEB_SOCKET
import za.co.ilert.core.util.handleWebSocket
import za.co.ilert.presentation.routes.authenticate
import za.co.ilert.presentation.services.chat.ChatController

fun Route.chatWebSocket(chatController: ChatController) {
	authenticate {
		webSocket(WEB_SOCKET) { // websocketSession
			chatController.onJoin(call.userId, socket = this)
			try {
				incoming.consumeEach { frame ->
					kotlin.run {
						when (frame) {
							is Frame.Text -> {
								val frameText = frame.readText()
								val delimiterIndex = frameText.indexOf(string = "#")
								if (delimiterIndex == -1) {
									println("No delimiterIndex")
									return@run
								}
								val type = frameText.substring(0, delimiterIndex).toIntOrNull() ?: return@run
								val json = frameText.substring(startIndex = delimiterIndex + 1)
								// TODO: Check is json is Valid using kotlinx.serialization
//								if (!json.isValidJson()) return@run
								handleWebSocket(
									ownUserId = call.userId,
									chatController = chatController,
									jsonString = json,
									type = type
								)
							}
							else -> Unit
						}
					}
				}
			} catch (e: Exception) {
				e.stackTrace
			} finally {
				chatController.onDisconnect(call.userId)
			}
		}
	}
}

fun Route.testRoute() {
	webSocket(Constants.WEB_SOCKET_TEST) {
		for (frame in incoming) {
			when (frame) {
				is Frame.Text -> {
					val text = frame.readText()
					outgoing.send(Frame.Text("YOU SAID: $text"))
					if (text.equals("bye", ignoreCase = true)) {
						close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
					}
				}
				else -> Unit
			}
		}
	}
}