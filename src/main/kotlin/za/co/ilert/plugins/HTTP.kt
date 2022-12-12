package za.co.ilert.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureHTTP() {
	install(CORS) {
		allowMethod(HttpMethod.Options)
		allowMethod(HttpMethod.Put)
		allowMethod(HttpMethod.Delete)
		allowMethod(HttpMethod.Patch)
		allowHeader(HttpHeaders.Authorization)
//		allowHeader("MyCustomHeader")
		allowCredentials = true
		allowHost("173.255.192.16", listOf("https://", "http://"))
		allowHost("2600:3c00::f03c:91ff:fe93:9f35", listOf("https://", "http://"))
		allowHost("192.168.70.198", listOf("https://", "http://"))
		allowHost("10.0.2.2", listOf("https://", "http://"))
//		anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
	}
}
