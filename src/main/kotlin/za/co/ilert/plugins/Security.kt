package za.co.ilert.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.litote.kmongo.json
import za.co.ilert.core.util.Constants.USER_ID
import za.co.ilert.core.data.responses.BasicApiResponse

fun Application.configureSecurity() {

	authentication {
		jwt {
			val environment = this@configureSecurity.environment
			val jwtAudience = environment.config.property("jwt.audience").getString()
			realm = environment.config.property("jwt.realm").getString()
			verifier(
				JWT
					.require(Algorithm.HMAC256("secret"))
					.withAudience(jwtAudience)
					.withIssuer(environment.config.property("jwt.domain").getString())
					.build()
			)
			challenge { defaultScheme, realm ->
				call.respondText(
					text = BasicApiResponse<Unit>(
						successful = false,
						message = "$defaultScheme failed for realm $realm! Unauthorized"
					).json,
					contentType = ContentType.Application.Json,
					status = HttpStatusCode.Unauthorized
				)
			}
			validate { credential ->
				if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
			}
		}
	}
}

val JWTPrincipal.userId: String?
	get() = getClaim(name = USER_ID, clazz = String::class)