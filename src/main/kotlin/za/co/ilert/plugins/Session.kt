package za.co.ilert.plugins

import io.ktor.server.application.*
import io.ktor.server.sessions.*
import za.co.ilert.core.util.Constants.ILERT_SESSION
import za.co.ilert.core.data.session.ISession

fun Application.configureSession(){

	install(Sessions) {
		cookie<ISession>(name = ILERT_SESSION) {
			cookie.extensions["SameSite"] = "lax"
		}
	}
}