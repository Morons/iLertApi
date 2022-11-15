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
import za.co.ilert.core.data.responses.BasicApiResponse
import za.co.ilert.core.data.responses.BlockTestResponse
import za.co.ilert.core.utils.Constants.BLOCK_TEST
import za.co.ilert.presentation.services.blocktest.BlockTestService
import za.co.ilert.presentation.validation.ValidationEvent
import java.time.Instant
import java.time.ZoneOffset

fun Route.getBlockTest(blockTestService: BlockTestService) {
	authenticate {
		get(BLOCK_TEST) {
			val request = kotlin.runCatching { call.receiveNullable<GetBlockTestRequest>() }.getOrNull() ?: kotlin.run {
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
			if (blockTest != null) {
				val blockTestResponse = BlockTestResponse(
					carcassType = blockTest.carcassType,
					carcassKgCostIncl = blockTest.carcassKgCostIncl,
					carcassWeight = blockTest.carcassWeight,
					carcassHangingWeight = blockTest.carcassHangingWeight,
					cutTrimWeight = blockTest.cutTrimWeight,
					weightLossParameter = blockTest.weightLossParameter,
					cuttingLossParameter = blockTest.cuttingLossParameter,
					waistParameter = blockTest.wasteParameter,
					percentGpRequired = blockTest.percentGpRequired,
					cuts = blockTest.cuts,
					timestamp = blockTest.timestamp,
					blockTestId = blockTest.blockTestId
				)
				call.respond(status = OK, message = BasicApiResponse(successful = true, data = blockTestResponse))
			} else {
				call.respond(status = BadRequest, message = BasicApiResponse<Unit>(successful = false))
				return@get
			}
		}
	}
}

//getCuts

fun Route.upsertBlockTest(blockTestService: BlockTestService) {
	authenticate {
		put(BLOCK_TEST) {
			val request = kotlin.runCatching { call.receiveNullable<BlockTestRequest>() }.getOrNull() ?: kotlin.run {
				call.respond(
					status = BadRequest, message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
				)
				return@put
			}
			when (blockTestService.validateInsertBlockTestRequest(request = request)) {
				ValidationEvent.ErrorFieldEmpty -> {
					call.respond(
						status = BadRequest,
						message = BasicApiResponse<Unit>(successful = false, message = FIELDS_BLANK)
					)
					return@put
				}

				else -> {
					val blockTest = request.copy(timestamp = Instant.now().atOffset(ZoneOffset.UTC).toEpochSecond())
					val wasAcknowledged = blockTestService.insertBlockTest(
						blockTestRequest = request
					)
					if (wasAcknowledged) call.respond(
						status = OK,
						message = BasicApiResponse<Unit>(successful = true, message = "$OK")
					)
					return@put
				}
			}
		}
	}
}

//deleteBlockTest

//getBlockTestsPaged