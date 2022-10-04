package za.co.ilert.presentation.routes.organization

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import za.co.ilert.core.data.repository.utils.ApiResponseMessages
import za.co.ilert.core.data.requests.OrganizationRequest
import za.co.ilert.core.data.responses.BasicApiResponse
import za.co.ilert.core.utils.Constants
import za.co.ilert.presentation.services.organization.OrganizationService


fun Route.createOrganization(organizationService: OrganizationService) {
	post(Constants.ORGANIZATION) {
		val request = kotlin.runCatching { call.receiveNullable<OrganizationRequest>() }.getOrNull() ?: kotlin.run {
			call.respond(
				status = HttpStatusCode.BadRequest,
				message = BasicApiResponse<Unit>(
					successful = false,
					message = ApiResponseMessages.UNKNOWN_ERROR_TRY_AGAIN
				)
			)
			return@post
		}
		organizationService.createOrganization(organizationRequest = request)
		call.respond(
			status = HttpStatusCode.OK,
			message = BasicApiResponse<Unit>(successful = true, message = "${HttpStatusCode.OK}")
		)
		return@post
	}
}