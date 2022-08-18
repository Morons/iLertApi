package za.co.ilert.presentation.routes

import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import za.co.ilert.core.data.repository.util.ApiResponseMessages
import za.co.ilert.core.data.repository.util.ApiResponseMessages.USER_NOT_FOUND
import za.co.ilert.core.data.requests.GetUserRequest
import za.co.ilert.core.data.requests.UserRequest
import za.co.ilert.core.data.requests.UserSearchRequest
import za.co.ilert.core.data.responses.BasicApiResponse
import za.co.ilert.core.data.responses.UserSearchResponse
import za.co.ilert.core.data.util.userId
import za.co.ilert.core.util.Constants.USER
import za.co.ilert.core.util.Constants.USER_SEARCH
import za.co.ilert.presentation.services.UserService


fun Route.searchUser(userService: UserService) {
	authenticate {
		get(USER_SEARCH) {
			val request = call.receiveOrNull<UserSearchRequest>() ?: kotlin.run {
				call.respond(
					status = BadRequest, message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
				)
				return@get
			}
			if (request.userSearch.isBlank()) {
				call.respond(status = OK, message = listOf<UserSearchResponse>())
				return@get
			}
			val searchResults = userService.searchForUsers(
				request.userSearch,
				call.userId,
				page = request.page,
				pageSize = request.pageSize
			)
			call.respond(status = OK, message = searchResults)
		}
	}
}

fun Route.getUser(userService: UserService) {
	authenticate {
		// TODO: 2022/07/07 Check if this is called at all
		post(USER) {
			val request = call.receiveOrNull<GetUserRequest>() ?: kotlin.run {
				call.respond(
					status = BadRequest, message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
				)
				return@post
			}
			val loginValue = with(request) { email.ifBlank { userName } }
			val user = userService.getUser(loginValue)
			if (user == null) {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = USER_NOT_FOUND)
				)
				return@post
			} else {
				call.respond(status = OK, message = user)
			}
			call.respond(status = OK, message = user)
		}
	}
}

fun Route.updateUserProfile(userService: UserService) {
	authenticate {
		put(USER) {
			val request = call.receiveOrNull<UserRequest>() ?: kotlin.run {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(
						successful = false,
						message = ApiResponseMessages.UNKNOWN_ERROR_TRY_AGAIN
					)
				)
				return@put
			}
			if (userService.updateUser(userRequest = request.copy(userId = call.userId))
			) {
				call.respond(status = OK, message = BasicApiResponse<Unit>(successful = true))
			} else {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false)
				)
			}
		}
	}
}