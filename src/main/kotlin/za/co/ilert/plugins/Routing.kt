package za.co.ilert.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import za.co.ilert.data.session.ISession

fun Application.configureRouting() {

	routing {
		get(path = "/") {
			call.respondText(text = "Hello World!")
		}
		// Static plugin. Try to access `/static/index.html`
		static(remotePath = "/static") {
			resources(resourcePackage = "static")
		}
	}

	routing {
		get(path = "/session/increment") {
			val session = call.sessions.get() ?: ISession()
			call.sessions.set(value = session.copy(count = session.count + 1))
			call.respondText(text = "Counter is ${session.count}. Refresh to increment.")
		}
	}

	routing {
		webSocket(path = "/ws") { // websocketSession
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
}
