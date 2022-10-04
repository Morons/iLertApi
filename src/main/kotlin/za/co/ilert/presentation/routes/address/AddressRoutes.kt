package za.co.ilert.presentation.routes.address

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import za.co.ilert.core.data.repository.utils.ApiResponseMessages
import za.co.ilert.core.data.requests.AddressRequest
import za.co.ilert.core.data.responses.BasicApiResponse
import za.co.ilert.core.utils.Constants.ADDRESS
import za.co.ilert.presentation.services.address.AddressService


fun Route.createAddress(addressService: AddressService) {
	post(ADDRESS) {
		val request = kotlin.runCatching { call.receiveNullable<AddressRequest>() }.getOrNull() ?: kotlin.run {
			call.respond(
				status = HttpStatusCode.BadRequest,
				message = BasicApiResponse<Unit>(
					successful = false,
					message = ApiResponseMessages.UNKNOWN_ERROR_TRY_AGAIN
				)
			)
			return@post
		}
		addressService.createAddress(addressRequest = request)
		call.respond(
			status = HttpStatusCode.OK,
			message = BasicApiResponse<Unit>(successful = true, message = "${HttpStatusCode.OK}")
		)
		return@post
	}
}