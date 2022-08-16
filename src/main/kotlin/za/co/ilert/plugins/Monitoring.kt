package za.co.ilert.plugins

import io.ktor.server.plugins.callloging.*
import org.slf4j.event.*
import io.ktor.server.request.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureMonitoring() {
	install(CallLogging) {
		level = Level.TRACE
		filter { call -> call.request.path().startsWith("/") }
	}

}
