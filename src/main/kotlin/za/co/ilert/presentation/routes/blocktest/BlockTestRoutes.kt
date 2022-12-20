package za.co.ilert.presentation.routes.blocktest

import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId
import za.co.ilert.core.data.mapper.toBlockTest
import za.co.ilert.core.data.models.BlockTest
import za.co.ilert.core.data.models.Cut
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.BLOCK_TESTS_NOT_FOUND
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.FAILED_CREATE_BLOCK_TESTS
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.FIELDS_BLANK
import za.co.ilert.core.data.repository.utils.ApiResponseMessages.UNKNOWN_ERROR_TRY_AGAIN
import za.co.ilert.core.data.requests.BlockTestByIdRequest
import za.co.ilert.core.data.requests.BlockTestRequest
import za.co.ilert.core.data.requests.GenericPageRequest
import za.co.ilert.core.data.requests.NewBlockTestRequest
import za.co.ilert.core.data.responses.BasicApiResponse
import za.co.ilert.core.data.responses.BlockTestApiResponse
import za.co.ilert.core.utils.Constants.BLOCK_TEST
import za.co.ilert.core.utils.Constants.BLOCK_TESTS
import za.co.ilert.core.utils.Constants.BLOCK_TEST_CREATE
import za.co.ilert.core.utils.Constants.BLOCK_TEST_CUT
import za.co.ilert.presentation.services.blocktest.BlockTestService
import za.co.ilert.presentation.validation.ValidationEvent

fun Route.getBlockTestById(blockTestService: BlockTestService) {
	authenticate {
		post(BLOCK_TEST) {
			val request =
				kotlin.runCatching { call.receiveNullable<BlockTestByIdRequest>() }.getOrNull() ?: kotlin.run {
					call.respond(
						status = BadRequest,
						message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
					)
					return@post
				}
			if (request.blockTestId.isBlank()) {
				call.respond(status = BadRequest, message = BasicApiResponse<Unit>(successful = false))
				return@post
			}
			val blockTest = blockTestService.getBlockTest(request.blockTestId)
			if (blockTest != null) {
				val blockTestApiResponse = with(blockTest) {
					BlockTestApiResponse(
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
						notBalancingReason = notBalancingReason,
						locked = locked,
						timestamp = timestamp
					)
				}
				call.respond(status = OK, message = BasicApiResponse(successful = true, data = blockTestApiResponse))
			} else {
				call.respond(status = BadRequest, message = BasicApiResponse<Unit>(successful = false))
				return@post
			}
		}
	}
}

fun Route.getBlockTests(blockTestService: BlockTestService) {
	authenticate {
		get(BLOCK_TESTS) {
			val blockTests: List<BlockTest> = blockTestService.getBlockTests()
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

fun Route.createBlockTest(blockTestService: BlockTestService) {
	authenticate {
		post(BLOCK_TEST_CREATE) {
			val request = kotlin.runCatching { call.receiveNullable<NewBlockTestRequest>() }.getOrNull() ?: kotlin.run {
				call.respond(
					status = BadRequest, message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
				)
				return@post
			}
			val blockTest = blockTestService.createBlockTest(newBlockTestRequest = request)
			if (blockTest != null) call.respond(
				status = OK,
				message = BasicApiResponse(successful = true, data = blockTest)
			)
			else
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = FAILED_CREATE_BLOCK_TESTS)
				)
			return@post
		}
	}
}

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
					val cutsToSave = request.cuts.map { it.copy(blockTestId = blockTestId) }
					val blockTestToSave =
						request.copy(blockTestId = blockTestId, cuts = cutsToSave)
					val wasAcknowledged = blockTestService.upsertBlockTest(blockTest = blockTestToSave.toBlockTest())
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

fun Route.insertBlockTestCut(blockTestService: BlockTestService) {
	authenticate {
		post(BLOCK_TEST_CUT) {
			val request = kotlin.runCatching { call.receiveNullable<Cut>() }.getOrNull() ?: kotlin.run {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
				)
				return@post
			}
			val blockTestId = request.blockTestId
			val blockTest = blockTestService.getBlockTest(blockTestId)
			val cutId = request.cutId.ifBlank {
				ObjectId().toString()
			}
			if (blockTest != null) {
				val cuts = blockTest.cuts.toMutableList()
				val newCut = request.copy(cutId = cutId)
				cuts.add(newCut)
				val wasAcknowledged = blockTestService.upsertBlockTest(blockTest.copy(cuts = cuts))
					.also { println("********** blockTestService.upsertBlockTest wasAcknowledged = $it **********") }
				if (wasAcknowledged) {
					call.respond(status = OK, message = BasicApiResponse<Unit>(successful = true))
				} else {
					call.respond(status = BadRequest, message = BasicApiResponse<Unit>(successful = false))
					return@post
				}
			}
			return@post
		}
	}
}

fun Route.amendBlockTestCut(blockTestService: BlockTestService) {
	authenticate {
		put(BLOCK_TEST_CUT) {
			val request = kotlin.runCatching { call.receiveNullable<Cut>() }.getOrNull() ?: kotlin.run {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
				)
				return@put
			}
			val blockTestId = request.blockTestId
			val blockTest = blockTestService.getBlockTest(blockTestId)
			val cutId = request.cutId.ifBlank {
				call.respond(
					status = BadRequest,
					message = BasicApiResponse<Unit>(successful = false, message = "$BadRequest")
				)
				return@put
			}
			if (blockTest != null) {
				val cuts = blockTest.cuts.toMutableList()
				cuts.find { it.cutId == cutId }?.let { cut ->
					with(cut) {
						marketValueXVat = request.marketValueXVat
						costPerCutXVat = request.costPerCutXVat
						salesPriceInclVat = request.salesPriceInclVat
						actualValueXVat = request.actualValueXVat
					}
				}
				val wasAcknowledged = blockTestService.upsertBlockTest(blockTest.copy(cuts = cuts))
				if (wasAcknowledged) {
					call.respond(status = OK, message = BasicApiResponse<Unit>(successful = true))
				} else {
					call.respond(status = BadRequest, message = BasicApiResponse<Unit>(successful = false))
					return@put
				}
			}
			return@put
		}
	}
}