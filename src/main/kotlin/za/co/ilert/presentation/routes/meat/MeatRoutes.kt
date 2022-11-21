package za.co.ilert.presentation.routes.meat

import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import za.co.ilert.core.data.mapper.toCarcassTypeResponse
import za.co.ilert.core.data.mapper.toCutTypeResponse
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.CARCASS_TYPE_NOT_FOUND
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.CUT_TYPE_NOT_FOUND
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.UNKNOWN_ERROR_TRY_AGAIN
import za.co.ilert.core.data.requests.*
import za.co.ilert.core.data.responses.BasicApiResponse
import za.co.ilert.core.utils.Constants.CARCASS
import za.co.ilert.core.utils.Constants.CARCASSES_CREATE
import za.co.ilert.core.utils.Constants.CARCASS_CREATE
import za.co.ilert.core.utils.Constants.CUTS
import za.co.ilert.core.utils.Constants.CUTS_CREATE
import za.co.ilert.core.utils.Constants.CUT_CREATE
import za.co.ilert.core.utils.Constants.PRIVATE_CUTS
import za.co.ilert.presentation.services.meat.MeatService

//createCarcassType(carcassTypeRequest: CarcassTypeRequest): Boolean
fun Route.createCarcassType(meatService: MeatService) {
	authenticate {
		post(CARCASS_CREATE) {
			val carcassTypeRequest =
				kotlin.runCatching { call.receiveNullable<CarcassTypeRequest>() }.getOrNull() ?: kotlin.run {
					call.respond(
						status = BadRequest,
						message = BasicApiResponse<Unit>(
							successful = false,
							message = UNKNOWN_ERROR_TRY_AGAIN
						)
					)
					return@post
				}
			meatService.createCarcassType(carcassTypeRequest = carcassTypeRequest)
			call.respond(
				status = OK,
				message = BasicApiResponse<Unit>(successful = true, message = "$OK")
			)
			return@post
		}
	}
}

fun Route.createCarcassTypes(meatService: MeatService) {
	authenticate {
		post(CARCASSES_CREATE) {
			val createCarcassTypesRequest =
				kotlin.runCatching { call.receiveNullable<CreateCarcassTypesRequest>() }.getOrNull() ?: kotlin.run {
					call.respond(
						status = BadRequest,
						message = BasicApiResponse<Unit>(
							successful = false,
							message = UNKNOWN_ERROR_TRY_AGAIN
						)
					)
					return@post
				}
			meatService.createCarcassTypes(createCarcassTypesRequest = createCarcassTypesRequest)
			call.respond(
				status = OK,
				message = BasicApiResponse<Unit>(successful = true, message = "$OK")
			)
			return@post
		}
	}
}

//getCarcassTypeById(carcassTypeId: String): CarcassType?
fun Route.getCarcassTypeById(meatService: MeatService) {
	authenticate {
		post(CARCASS) {
			val carcassTypeIdRequest =
				kotlin.runCatching { call.receiveNullable<CarcassTypeIdRequest>() }.getOrNull() ?: kotlin.run {
					call.respond(
						status = BadRequest,
						message = BasicApiResponse<Unit>(
							successful = false,
							message = UNKNOWN_ERROR_TRY_AGAIN
						)
					)
					return@post
				}
			val carcassTypeResponse = meatService.getCarcassTypeById(carcassTypeIdRequest.carcassTypeId)
			if (carcassTypeResponse == null) {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = CARCASS_TYPE_NOT_FOUND)
				)
				return@post
			} else {
				call.respond(status = OK, message = BasicApiResponse(successful = true, data = carcassTypeResponse))
			}
		}
	}
}

//getCarcassTypeList(): List<CarcassType>
fun Route.getCarcassTypeList(meatService: MeatService) {
	authenticate {
		get(CARCASS) {
			call.respond(status = OK, message = meatService.getCarcassTypeList().map { it.toCarcassTypeResponse() })
		}
	}
}

//createCutType(cutTypeRequest: CutTypeRequest): Boolean
fun Route.createCutType(meatService: MeatService) {
	authenticate {
		post(CUT_CREATE) {
			val cutTypeRequest =
				kotlin.runCatching { call.receiveNullable<CutTypeRequest>() }.getOrNull() ?: kotlin.run {
					call.respond(
						status = BadRequest,
						message = BasicApiResponse<Unit>(successful = false, message = UNKNOWN_ERROR_TRY_AGAIN)
					)
					return@post
				}
			if (meatService.createCutType(cutTypeRequest = cutTypeRequest)) {
				call.respond(
					status = OK,
					message = BasicApiResponse<Unit>(successful = true, message = "$OK")
				)
				return@post
			} else {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = UNKNOWN_ERROR_TRY_AGAIN)
				)
			}
		}
	}
}

fun Route.createCutTypes(meatService: MeatService) {
	authenticate {
		post(CUTS_CREATE) {
			val createCutTypesRequest =
				kotlin.runCatching { call.receiveNullable<CreateCutTypesRequest>() }.getOrNull() ?: kotlin.run {
					call.respond(
						status = BadRequest,
						message = BasicApiResponse<Unit>(successful = false, message = UNKNOWN_ERROR_TRY_AGAIN)
					)
					return@post
				}
			if (meatService.createCutTypes(createCutTypesRequest = createCutTypesRequest)) {
				call.respond(
					status = OK,
					message = BasicApiResponse<Unit>(successful = true, message = "$OK")
				)
				return@post
			} else {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = UNKNOWN_ERROR_TRY_AGAIN)
				)
			}
		}
	}
}

//getCutTypeById(cutTypeId: String): CutType?
fun Route.getCutTypeById(meatService: MeatService) {
	authenticate {
		post(CUTS) {
			val cutTypeIdRequest =
				kotlin.runCatching { call.receiveNullable<CutTypeIdRequest>() }.getOrNull() ?: kotlin.run {
					call.respond(
						status = BadRequest,
						message = BasicApiResponse<Unit>(
							successful = false,
							message = UNKNOWN_ERROR_TRY_AGAIN
						)
					)
					return@post
				}
			val cutTypeResponse = meatService.getCutTypeById(cutTypeIdRequest.cutTypeId)
			if (cutTypeResponse == null) {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = CUT_TYPE_NOT_FOUND)
				)
				return@post
			} else {
				call.respond(status = OK, message = BasicApiResponse(successful = true, data = cutTypeResponse))
			}
		}
	}
}

//getCutTypeList(): List<CutType>
fun Route.getCutTypeList(meatService: MeatService) {
	authenticate {
		get(CUTS) {
			call.respond(status = OK, message = meatService.getCutTypeList().map { it.toCutTypeResponse() })
		}
	}
}

//getCutTypeListByOrganizationId(organizationId: String): List<CutType>
fun Route.getCutTypeListByOrganizationId(meatService: MeatService) {
	authenticate {
		post(PRIVATE_CUTS) {
			val organizationIdRequest =
				kotlin.runCatching { call.receiveNullable<OrganizationIdRequest>() }.getOrNull() ?: kotlin.run {
					call.respond(
						status = BadRequest,
						message = BasicApiResponse<Unit>(
							successful = false,
							message = UNKNOWN_ERROR_TRY_AGAIN
						)
					)
					return@post
				}
			val cutTypeResponse = meatService.getCutTypeListByOrganizationId(organizationIdRequest.organizationId)
			if (cutTypeResponse == null) {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = CUT_TYPE_NOT_FOUND)
				)
				return@post
			} else {
				call.respond(status = OK, message = BasicApiResponse(successful = true, data = cutTypeResponse))
			}
		}
	}
}