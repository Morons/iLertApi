package za.co.ilert.core.data.util

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import za.co.ilert.core.utils.Constants.USER_ID

//suspend fun PipelineContext<Unit, ApplicationCall>.ifEmailBelongsToLoggedInUser(
//	userId: String, validateEmail: suspend (email: String, userId: String) -> Boolean, onSuccess: suspend () -> Unit
//) {
//	val isEmailByUser = validateEmail(call.principal<JWTPrincipal>()?.userId ?: "", userId)
//	if (isEmailByUser) onSuccess() else call.respond(
//		status = Unauthorized,
//		message = BasicApiResponse<Unit>(successful = false, message = "$Unauthorized $POSSIBLE_HACKER")
//	)
//}

val JWTPrincipal.userId: String?
	get() = getClaim(name = USER_ID, clazz = String::class)

val ApplicationCall.userId: String
	get() = principal<JWTPrincipal>()?.userId.toString()
