package za.co.ilert.presentation.routes.organization

import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId
import org.litote.kmongo.json
import za.co.ilert.core.data.models.UserSecurity
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.FIELDS_BLANK
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.ORGANIZATION_NOT_FOUND
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.UNKNOWN_ERROR_TRY_AGAIN
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.USER_ALREADY_EXIST
import za.co.ilert.core.data.requests.CreateOrganizationRequest
import za.co.ilert.core.data.requests.OrganizationIdRequest
import za.co.ilert.core.data.requests.OrganizationRequest
import za.co.ilert.core.data.requests.UserRequest
import za.co.ilert.core.data.responses.BasicApiResponse
import za.co.ilert.core.utils.Constants.FILE_SOURCE
import za.co.ilert.core.utils.Constants.ORGANIZATION
import za.co.ilert.core.utils.Constants.ORGANIZATION_GET
import za.co.ilert.core.utils.getByteArray
import za.co.ilert.presentation.services.organization.OrganizationService
import za.co.ilert.presentation.services.user.UserService
import za.co.ilert.presentation.validation.ValidationEvent

fun Route.createOrganization(organizationService: OrganizationService, userService: UserService) {
	post(ORGANIZATION) {
		val request = kotlin.runCatching { call.receiveNullable<OrganizationRequest>() }.getOrNull() ?: kotlin.run {
			call.respond(
				status = BadRequest,
				message = BasicApiResponse<Unit>(
					successful = false,
					message = UNKNOWN_ERROR_TRY_AGAIN
				)
			)
			return@post
		}

		val organizationId = ObjectId().toString()
		val ownerId: String = ObjectId().toString()

		val userRequest = with(request.owner) {
			UserRequest(
				userId = ownerId,
				userEmail = userEmail,
				mobileNumber = mobileNumber.orEmpty(),
				userName = userName,
				password = password,
				avatarAsString = avatarAsString
					?: getByteArray(filePathName = "$FILE_SOURCE/ic_avatar_default.png"),
				security = security ?: UserSecurity(active = true, roles = "BLOCK MAN"),
				organizationId = organizationId
			)
		}
		if (userService.doesUserWithEmailExist(email = userRequest.userEmail)) {
			call.respond(
				status = OK,
				message = BasicApiResponse<Unit>(successful = false, message = USER_ALREADY_EXIST)
			)
			return@post
		}
		when (userService.validateCreateAccountRequest(userRequest = userRequest)) {
			ValidationEvent.ErrorFieldEmpty -> {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = FIELDS_BLANK)
				)
				return@post
			}
			else -> {
				userService.createUser(userRequest = userRequest)
				call.respond(
					status = OK,
					message = BasicApiResponse<Unit>(successful = true, message = "$OK")
				)
			}
		}
		val createOrganizationRequest = with(request) {
			CreateOrganizationRequest(
				organizationId = organizationId,
				organizationName = organizationName,
				organizationPhone = organizationPhone,
				organizationEmail = organizationEmail,
				organizationOwnerId = ownerId,
				industry = industry,
				sector = sector,
				organizationMobile = organizationMobile,
				organizationPreferredIM = organizationPreferredIM,
				payment = payment,
				organizationPopi = organizationPopi,
				coRegistrationNumber = coRegistrationNumber,
				numberVAT = numberVAT,
				valueVATPercent = valueVATPercent,
				address = address,
				parameters = parameters
			)
		}
		organizationService.createOrganization(createOrganizationRequest = createOrganizationRequest)
		call.respond(
			status = OK,
			message = BasicApiResponse<Unit>(successful = true, message = "$OK")
		)
		return@post
	}
}

fun Route.getOrganization(organizationService: OrganizationService) {
	authenticate {
		get(ORGANIZATION) {
			val request =
				kotlin.runCatching { call.receiveNullable<OrganizationIdRequest>() }.getOrNull() ?: kotlin.run {
					println("BadRequest call.receiveNullable **********")
					call.respond(
						status = BadRequest,
						message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
					)
					return@get
				}
			val organization = organizationService.getOrganization(request.organizationId)
			println("organization = ${organization?.json} **********")
			if (organization == null) {
				println("BadRequest organization == null **********")
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = ORGANIZATION_NOT_FOUND)
				)
				return@get
			}
			call.respond(status = OK, message = BasicApiResponse(successful = true, data = organization))
		}
	}
}

fun Route.getOrganizationUsePost(organizationService: OrganizationService) {
	authenticate {
		post(ORGANIZATION_GET) {
			val request =
				kotlin.runCatching { call.receiveNullable<OrganizationIdRequest>() }.getOrNull() ?: kotlin.run {
					println("BadRequest call.receiveNullable **********")
					call.respond(
						status = BadRequest,
						message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
					)
					return@post
				}
			val organization = organizationService.getOrganization(request.organizationId)
			println("organization = ${organization?.json} **********")
			if (organization == null) {
				println("BadRequest organization == null **********")
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = ORGANIZATION_NOT_FOUND)
				)
				return@post
			}
			call.respond(status = OK, message = BasicApiResponse(successful = true, data = organization))
		}
	}
}