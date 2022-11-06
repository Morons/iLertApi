package za.co.ilert.presentation.routes.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.FIELDS_BLANK
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.LOGIN_AUTHENTICATED
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.PASSWORD_NOT_MATCHING
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.UNKNOWN_ERROR_TRY_AGAIN
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.USER_ALREADY_EXIST
import za.co.ilert.core.data.requests.AuthRequest
import za.co.ilert.core.data.requests.UserRequest
import za.co.ilert.core.data.responses.AuthResponse
import za.co.ilert.core.data.responses.BasicApiResponse
import za.co.ilert.core.utils.Constants.USER_AUTHENTICATE
import za.co.ilert.core.utils.Constants.USER_CREATE
import za.co.ilert.core.utils.Constants.USER_ID
import za.co.ilert.core.utils.Constants.USER_LOGIN
import za.co.ilert.presentation.services.user.UserService
import za.co.ilert.presentation.validation.ValidationEvent
import java.util.*

fun Route.authenticate() {
	authenticate {
		get(USER_AUTHENTICATE) {
			val principal = call.principal<JWTPrincipal>()
			val userId = principal!!.payload.getClaim(USER_ID).asString()
			val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
			if (call.response.status() == Unauthorized) {
				call.respond(
					status = Unauthorized,
					message = BasicApiResponse<Unit>(
						successful = false,
						message = "userId = $userId, key expires at: ${expiresAt}ms"
					)
				)
			} else {
				call.respond(
					status = OK,
					message = BasicApiResponse<Unit>(
						successful = true,
						message = "$LOGIN_AUTHENTICATED userId = $userId, key expires at: ${expiresAt}ms"
					)
				)
			}
		}
	}
}

fun Route.createUser(userService: UserService) {
	post(USER_CREATE) {
		val request = kotlin.runCatching { call.receiveNullable<UserRequest>() }.getOrNull() ?: kotlin.run {
			call.respond(
				status = BadRequest,
				message = BasicApiResponse<Unit>(
					successful = false,
					message = UNKNOWN_ERROR_TRY_AGAIN
				)
			)
			return@post
		}
		if (userService.doesUserWithEmailExist(email = request.userEmail)) {
			call.respond(
				status = OK,
				message = BasicApiResponse<Unit>(successful = false, message = USER_ALREADY_EXIST)
			)
			return@post
		}
		when (userService.validateCreateAccountRequest(userRequest = request)) {
			ValidationEvent.ErrorFieldEmpty -> {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = FIELDS_BLANK)
				)
				return@post
			}

			else -> {
				userService.createUser(userRequest = request)
				call.respond(
					status = OK,
					message = BasicApiResponse<Unit>(successful = true, message = "$OK")
				)
				return@post
			}
		}
	}
}

fun Route.loginUser(
	userService: UserService, jwtIssuer: String, jwtAudience: String, jwtSecret: String
) {
	post(USER_LOGIN) {
		val request = kotlin.runCatching { call.receiveNullable<AuthRequest>() }.getOrNull() ?: kotlin.run {
			call.respond(
				status = BadRequest,
				message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
			)
			return@post
		}
		if (with(request) { email.isBlank() && userName.isBlank() }) {
			call.respond(
				status = BadRequest,
				message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
			)
			return@post
		}
		val loginValue = with(request) { email.ifBlank { userName } }
		val user = userService.getUserByEmail(request.email) ?: kotlin.run {
			call.respond(
				status = BadRequest,
				message = BasicApiResponse<Unit>(
					successful = false,
					message = PASSWORD_NOT_MATCHING
				)
			)
			return@post
		}
		if (userService.doesLoginValueMatchForUser(loginValue, request.password, user.password)) {
			val token = JWT.create().withClaim("userId", user.userId).withIssuer(jwtIssuer)
				.withExpiresAt(Date(System.currentTimeMillis().plus(31557600000L))).withAudience(jwtAudience)
				.sign(Algorithm.HMAC256(jwtSecret))
			call.respond(
				status = OK,
				message = BasicApiResponse(successful = true, data = AuthResponse(token = token, userId = user.userId))
			)
		} else {
			call.respond(
				status = BadRequest,
				message = BasicApiResponse<Unit>(
					successful = false,
					message = PASSWORD_NOT_MATCHING
				)
			)
		}
	}
}