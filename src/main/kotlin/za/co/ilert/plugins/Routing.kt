package za.co.ilert.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.ktor.ext.inject
import za.co.ilert.presentation.routes.*
import za.co.ilert.presentation.services.UserService

fun Application.configureRouting() {

	val userService: UserService by inject()


	val jwtIssuer = environment.config.property("jwt.domain").getString()
	val jwtAudience = environment.config.property("jwt.audience").getString()
	val jwtSecret = environment.config.property("jwt.secret").getString()

	routing {

		// Auth Routes
		authenticate()
		createUser(userService)
		loginUser(
			userService = userService,
			jwtIssuer = jwtIssuer,
			jwtAudience = jwtAudience,
			jwtSecret = jwtSecret,
		)

		// Session Routes
		sessionIncrement()

		// Websocket Session
		websocketSession()

		// Static plugin. Try to access `/static/index.html`
		static(remotePath = "/static") {
			resources(resourcePackage = "static")
		}
	}
}
