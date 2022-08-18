package za.co.ilert.presentation.routes

import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*

fun Route.websocketSession(){
	webSocket(path = "/socket") { // websocketSession
		for (frame in incoming) {
			if (frame is Frame.Text) {
				val text = frame.readText()
				outgoing.send(element = Frame.Text(text = "YOU SAID: $text"))
				if (text.equals(other = "bye", ignoreCase = true)) {
					close(reason = CloseReason(code = CloseReason.Codes.NORMAL, message = "Client said BYE"))
				}
			}
		}
	}
}