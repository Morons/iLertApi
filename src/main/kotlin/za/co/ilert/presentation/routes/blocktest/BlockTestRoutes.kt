package za.co.ilert.presentation.routes.blocktest

import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId
import org.litote.kmongo.json
import za.co.ilert.core.data.models.BlockTest
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.BLOCK_TESTS_NOT_FOUND
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.FIELDS_BLANK
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.UNKNOWN_ERROR_TRY_AGAIN
import za.co.ilert.core.data.requests.BlockTestRequest
import za.co.ilert.core.data.requests.GenericPageRequest
import za.co.ilert.core.data.requests.GetBlockTestRequest
import za.co.ilert.core.data.responses.BasicApiResponse
import za.co.ilert.core.data.responses.BlockTestResponse
import za.co.ilert.core.utils.Constants.BLOCK_TEST
import za.co.ilert.core.utils.Constants.BLOCK_TESTS
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

				val blockTestResponse = with(blockTest) {
					BlockTestResponse(
						blockTestId = blockTestId,
						userId = userId,
						organizationId = organizationId,
						carcassTypeId = carcassTypeId,
						carcassKgCostIncl = carcassKgCostIncl,
						carcassWeight = carcassWeight,
						carcassHangingWeight = carcassHangingWeight,
						cutTrimWeight = cutTrimWeight,
						weightLossParameter = weightLossParameter,
						cuttingLossParameter = cuttingLossParameter,
						wasteParameter = wasteParameter,
						percentGpRequired = percentGpRequired,
						cuts = cuts,
						timestamp = timestamp
					)
				}
				call.respond(status = OK, message = BasicApiResponse(successful = true, data = blockTestResponse))
			} else {
				call.respond(status = BadRequest, message = BasicApiResponse<Unit>(successful = false))
				return@get
			}
		}
	}
}

fun Route.getBlockTests(blockTestService: BlockTestService) {
	authenticate {
		get(BLOCK_TESTS) {
			val blockTests: List<BlockTest> = blockTestService.getBlockTests()
				.also { println("getBlockTests BlockTestList = ${it.json} **********") }
			if (blockTests.isNotEmpty()) {
				call.respond(status = OK, message = BasicApiResponse(successful = true, data = blockTests))
				return@get
			} else {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = BLOCK_TESTS_NOT_FOUND)
				)
			}
		}
	}
}

fun Route.getBlockTestsPaged(blockTestService: BlockTestService) {
	authenticate {
		post(BLOCK_TESTS) {
			val request = kotlin.runCatching { call.receiveNullable<GenericPageRequest>() }.getOrNull() ?: kotlin.run {
				call.respond(
					status = BadRequest, message = BasicApiResponse<Unit>(
						successful = false, message = UNKNOWN_ERROR_TRY_AGAIN
					)
				)
				return@post
			}
			val carcassTypes = blockTestService.getBlockTestsPaged(request)
			if (carcassTypes.isNotEmpty()) {
				call.respond(status = OK, message = BasicApiResponse(successful = true, data = carcassTypes))
				return@post
			} else {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(
						successful = false,
						message = BLOCK_TESTS_NOT_FOUND
					)
				)
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
					val blockTestId = ObjectId().toString()
					val timestamp = Instant.now().atOffset(ZoneOffset.UTC).toEpochSecond()
					val cutsToSave = request.cuts.map { it.copy(blockTestId = blockTestId) }
					val blockTestToSave =
						request.copy(blockTestId = blockTestId, cuts = cutsToSave, timestamp = timestamp)
					val wasAcknowledged = blockTestService.insertBlockTest(blockTestRequest = blockTestToSave)
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