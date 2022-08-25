package za.co.ilert.presentation.routes.chat

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import za.co.ilert.core.data.session.ISession
import za.co.ilert.core.utils.Constants.SESSION_INCREMENT


fun Route.sessionIncrement() {
	get(path = SESSION_INCREMENT) {
		val session = call.sessions.get() ?: ISession()
		call.sessions.set(value = session.copy(count = session.count + 1))
		call.respondText(text = "Counter is ${session.count}. Refresh to increment.")
	}
}