package za.co.ilert.presentation.routes.blocktest

import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.FIELDS_BLANK
import za.co.ilert.core.data.requests.BlockTestRequest
import za.co.ilert.core.data.requests.GetBlockTestRequest
import za.co.ilert.core.data.requests.PrimalCutsRequest
import za.co.ilert.core.data.responses.BasicApiResponse
import za.co.ilert.core.utils.Constants.BLOCK_TEST
import za.co.ilert.core.utils.Constants.BLOCK_TEST_PRIMALS
import za.co.ilert.presentation.services.blocktest.BlockTestService
import za.co.ilert.presentation.validation.ValidationEvent

fun Route.getBlockTest(blockTestService: BlockTestService) {
	authenticate {
		get(BLOCK_TEST) {
			val request = call.receiveOrNull<GetBlockTestRequest>() ?: kotlin.run {
				call.respond(
					status = BadRequest, message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
				)
				return@get
			}
			if (request.blockTestId.isBlank()) {
				call.respond(status = BadRequest, message = BasicApiResponse<Unit>(successful = false))
				return@get
			}
			val blockTest = blockTestService.getBlockTest(request.blockTestId)
			call.respond(status = OK, message = BasicApiResponse(successful = true, data = blockTest))
		}
	}
}

//getPrimalCuts

fun Route.insertBlockTest(blockTestService: BlockTestService) {
	authenticate {
		post(BLOCK_TEST) {
			val request = call.receiveOrNull<BlockTestRequest>() ?: kotlin.run {
				call.respond(
					status = BadRequest, message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
				)
				return@post
			}
			when (blockTestService.validateInsertBlockTestRequest(request = request)) {
				ValidationEvent.ErrorFieldEmpty -> {
					call.respond(
						status = BadRequest,
						message = BasicApiResponse<Unit>(successful = false, message = FIELDS_BLANK)
					)
					return@post
				}

				else -> {
					val wasAcknowledged = blockTestService.insertBlockTest(
						blockTestRequest = request
					)
					if (wasAcknowledged) call.respond(
						status = OK,
						message = BasicApiResponse<Unit>(successful = true, message = "$OK")
					)
					return@post
				}
			}
		}
	}
}

fun Route.insertPrimalCuts(blockTestService: BlockTestService) {
	authenticate {
		post(BLOCK_TEST_PRIMALS) {
			val request = call.receiveOrNull<PrimalCutsRequest>() ?: kotlin.run {
				call.respond(
					status = BadRequest, message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
				)
				return@post
			}
			when (blockTestService.validateInsertPrimalCutsRequest(request = request)) {
				ValidationEvent.ErrorFieldEmpty -> {
					call.respond(
						status = BadRequest,
						message = BasicApiResponse<Unit>(successful = false, message = FIELDS_BLANK)
					)
					return@post
				}

				else -> {
					blockTestService.insertPrimalCuts(
						primalCutsRequest = request
					)
					call.respond(
						status = OK,
						message = BasicApiResponse<Unit>(successful = true, message = "$OK")
					)
					return@post
				}
			}
		}
	}
}

//deleteBlockTest

//getBlockTestsPaged