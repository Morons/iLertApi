package za.co.ilert.presentation.services.blocktest

import za.co.ilert.core.data.repository.blocktest.BlockTestRepository
import za.co.ilert.core.data.requests.*
import za.co.ilert.core.data.responses.BlockTestListRequest
import za.co.ilert.core.data.responses.BlockTestResponse
import za.co.ilert.core.data.responses.PrimalCutResponse
import za.co.ilert.presentation.validation.ValidationEvent


/**
 * suspend fun getBlockTest(blockTestId: String): BlockTestResponse?
 * suspend fun getPrimalCuts(blockTestId: String): List<PrimalCutResponse>
 * suspend fun insertBlockTest(blockTestRequest: BlockTestRequest, primalCutsRequest: List<PrimalCutRequest>): Boolean
 * suspend fun deleteBlockTest(deleteBlockTestRequest: DeleteBlockTestRequest): Boolean
 * suspend fun getBlockTestsPaged(genericPageRequest: GenericPageRequest):List<BlockTestListRequest>
 **/

class BlockTestService(
	private val blockTestRepository: BlockTestRepository
) {

	suspend fun getBlockTest(blockTestId: String): BlockTestResponse? {
		return blockTestRepository.getBlockTest(blockTestId = blockTestId)
	}

	suspend fun getPrimalCuts(blockTestId: String): List<PrimalCutResponse> {
		return blockTestRepository.getPrimalCuts(blockTestId = blockTestId)
	}

	suspend fun insertBlockTest(
		blockTestRequest: BlockTestRequest
	): Boolean {
		return blockTestRepository.insertBlockTest(
			blockTestRequest = blockTestRequest
		)
	}

	suspend fun insertPrimalCuts(
		primalCutsRequest: PrimalCutsRequest
	) {
		blockTestRepository.insertPrimalCuts(
			primalCutsRequest = primalCutsRequest
		)
	}

	fun validateInsertBlockTestRequest(request: BlockTestRequest): ValidationEvent {
		return if (
			request.carcassKgCostIncl <= 0.0 ||
			request.carcassWeight <= 0.0
		) {
			ValidationEvent.ErrorFieldEmpty
		} else ValidationEvent.Success
	}

	fun validateInsertPrimalCutsRequest(request: PrimalCutsRequest): ValidationEvent {
		return if (
			request.primalCutsRequest.isEmpty()
		) {
			ValidationEvent.ErrorFieldEmpty
		} else ValidationEvent.Success
	}

	suspend fun deleteBlockTest(deleteBlockTestRequest: DeleteBlockTestRequest): Boolean {
		return blockTestRepository.deleteBlockTest(deleteBlockTestRequest = deleteBlockTestRequest)
	}

	suspend fun getBlockTestsPaged(genericPageRequest: GenericPageRequest): List<BlockTestListRequest> {
		return blockTestRepository.getBlockTestsPaged(genericPageRequest = genericPageRequest)
	}

}