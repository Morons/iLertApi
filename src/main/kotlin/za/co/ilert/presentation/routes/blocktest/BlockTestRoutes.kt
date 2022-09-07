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
			if (blockTest != null) {
				val blockTestResponse = BlockTestResponse(
					carcassType = blockTest.carcassType,
					carcassKgCostIncl = blockTest.carcassKgCostIncl,
					carcassWeight = blockTest.carcassWeight,
					carcassHangingWeight = blockTest.carcassHangingWeight,
					cutTrimWeight = blockTest.cutTrimWeight,
					carcassKgWeightLoss = blockTest.carcassKgWeightLoss,
					weightLossParameter = blockTest.weightLossParameter,
					cuttingLossParameter = blockTest.cuttingLossParameter,
					waistParameter = blockTest.wasteParameter,
					percentDifferenceParameter = blockTest.percentDifferenceParameter,
					percentGpRequired = blockTest.percentGpRequired,
					acceptablePriceVariance = blockTest.acceptablePriceVariance,
					trimmingWaste = blockTest.trimmingWaste,
					measuredWeightAfterCuts = blockTest.measuredWeightAfterCuts,
					primalCuts = blockTest.primalCuts,
					timestamp = blockTest.timestamp,
					blockTestId = blockTest.blockTestId,
					carcassCostIncl = blockTest.carcassKgCostIncl * blockTest.carcassWeight,
					carcassEffectivePrice =
					if (blockTest.carcassKgWeightLoss > 0.0) {
						(blockTest.carcassKgCostIncl * blockTest.carcassWeight) / blockTest.carcassKgWeightLoss
					} else 0.0,
					percentCarcassWeightLoss =
					if (blockTest.carcassWeight > 0.0) {
						(blockTest.carcassWeight - blockTest.carcassKgWeightLoss) / blockTest.carcassWeight
					} else 0.0,
					adjustedKgCostKgIncl =
					if (blockTest.carcassHangingWeight > 0.0) {
						(blockTest.carcassKgCostIncl * blockTest.carcassWeight) / blockTest.carcassHangingWeight
					} else 0.0,
					cuttingLoss =
					if (blockTest.measuredWeightAfterCuts > 0.0) {
						blockTest.carcassHangingWeight - blockTest.measuredWeightAfterCuts
					} else 0.0,
					percentWeightLoss =
					if ((blockTest.trimmingWaste > 0.0)) {
						blockTest.measuredWeightAfterCuts / blockTest.trimmingWaste
					} else 0.0,
					percentCuttingLoss =
					if (blockTest.carcassHangingWeight > 0.0) {
						blockTest.carcassHangingWeight - blockTest.measuredWeightAfterCuts / blockTest.carcassHangingWeight
					} else 0.0
				)
				call.respond(status = OK, message = BasicApiResponse(successful = true, data = blockTestResponse))
			} else {
				call.respond(status = BadRequest, message = BasicApiResponse<Unit>(successful = false))
				return@get
			}
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
					val blockTest = request.copy(timestamp = Instant.now().atOffset(ZoneOffset.UTC).toEpochSecond())
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

//deleteBlockTest

//getBlockTestsPaged