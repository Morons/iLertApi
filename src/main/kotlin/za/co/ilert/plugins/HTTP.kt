package za.co.ilert.plugins

import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.httpsredirect.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureHTTP() {
	install(CORS) {
		allowMethod(HttpMethod.Options)
		allowMethod(HttpMethod.Put)
		allowMethod(HttpMethod.Delete)
		allowMethod(HttpMethod.Patch)
		allowHeader(HttpHeaders.Authorization)
//		allowHeader("MyCustomHeader")
		allowCredentials = true
		allowHost("192.168.70.198", listOf("https://", "http://"))
		allowHost("10.0.2.2", listOf("https://", "http://"))
//		anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
	}
}
