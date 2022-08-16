package za.co.ilert.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.defaultheaders.*

fun Application.defaultHeaders() {
	install(DefaultHeaders) {
		header("X-Engine", "iLert") // will send this header with each response
	}
}