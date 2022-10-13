package za.co.ilert.presentation.routes.user

import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import za.co.ilert.core.data.repository.utils.ApiResponseMessages
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.USER_NOT_FOUND
import za.co.ilert.core.data.requests.GetUserRequest
import za.co.ilert.core.data.requests.UserRequest
import za.co.ilert.core.data.requests.UserSearchRequest
import za.co.ilert.core.data.responses.BasicApiResponse
import za.co.ilert.core.data.responses.UserSearchResponse
import za.co.ilert.core.data.util.userId
import za.co.ilert.core.utils.Constants.USER
import za.co.ilert.core.utils.Constants.USER_SEARCH
import za.co.ilert.presentation.services.user.UserService


fun Route.searchUser(userService: UserService) {
	authenticate {
		get(USER_SEARCH) {
			val request = kotlin.runCatching { call.receiveNullable<UserSearchRequest>() }.getOrNull() ?: kotlin.run {
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
		get(USER) {
			val request = kotlin.runCatching { call.receiveNullable<GetUserRequest>() }.getOrNull() ?: kotlin.run {
				call.respond(
					status = BadRequest, message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
				)
				return@get
			}
			val user = userService.getUser(request.userId)
			if (user == null) {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = USER_NOT_FOUND)
				)
				return@get
			}
			call.respond(status = OK, message = BasicApiResponse(successful = true, data = user))
		}
	}
}

fun Route.getUserUsePost(userService: UserService) {
	authenticate {
		post(USER) {
			val request = kotlin.runCatching {
				call.receiveNullable<GetUserRequest>()
			}.getOrNull() ?: kotlin.run {
				call.respond(
					status = BadRequest, message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
				)
				return@post
			}
			val user = userService.getUser(request.userId)
			if (user == null) {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = USER_NOT_FOUND)
				)
				return@post
			}
			call.respond(status = OK, message = BasicApiResponse(successful = true, data = user))
		}
	}
}

fun Route.updateUserProfile(userService: UserService) {
	authenticate {
		put(USER) {
			val request = kotlin.runCatching { call.receiveNullable<UserRequest>() }.getOrNull() ?: kotlin.run {
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