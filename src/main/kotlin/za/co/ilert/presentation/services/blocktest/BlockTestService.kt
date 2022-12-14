package za.co.ilert.presentation.services.blocktest

import za.co.ilert.core.data.mapper.toBlockTest
import za.co.ilert.core.data.models.BlockTest
import za.co.ilert.core.data.repository.blocktest.BlockTestRepository
import za.co.ilert.core.data.requests.BlockTestRequest
import za.co.ilert.core.data.requests.GenericPageRequest
import za.co.ilert.core.data.requests.NewBlockTestRequest
import za.co.ilert.presentation.validation.ValidationEvent


/**
 * suspend fun getBlockTest(blockTestId: String): BlockTest?
 * suspend fun getCuts(blockTestId: String): List<PrimalCutResponse>
 * suspend fun insertBlockTest(blockTestRequest: BlockTestRequest, primalCutsRequest: List<PrimalCutRequest>): Boolean
 * suspend fun deleteBlockTest(deleteBlockTestRequest: DeleteBlockTestRequest): Boolean
 * suspend fun getBlockTestsPaged(genericPageRequest: GenericPageRequest):List<BlockTestListResponse>
 **/

class BlockTestService(
	private val blockTestRepository: BlockTestRepository
) {

	suspend fun getBlockTest(blockTestId: String): BlockTest? {
		return blockTestRepository.getBlockTestById(blockTestId = blockTestId)
	}

	suspend fun getBlockTests(): List<BlockTest> {
		return blockTestRepository.getBlockTestList()
	}

	suspend fun getBlockTestsPaged(genericPageRequest: GenericPageRequest): List<BlockTest> {
		return blockTestRepository.getBlockTestListPaged(genericPageRequest = genericPageRequest)
	}

	suspend fun createBlockTest(newBlockTestRequest: NewBlockTestRequest): BlockTest? {
		val wasAcknowledged = blockTestRepository.insertBlockTest(blockTest = newBlockTestRequest.toBlockTest())
		return if (wasAcknowledged) blockTestRepository.getBlockTestById(newBlockTestRequest.blockTestId) else null
	}

	suspend fun upsertBlockTest(blockTest: BlockTest): Boolean =
		blockTestRepository.upsertBlockTest(blockTest = blockTest)

	fun validateInsertBlockTestRequest(request: BlockTestRequest): ValidationEvent {
		return if (
			request.carcassKgCostIncl <= 0.0 ||
			request.carcassWeight <= 0.0 ||
			request.cuts.isEmpty()
		) {
			ValidationEvent.ErrorFieldEmpty
		} else ValidationEvent.Success
	}

	suspend fun deleteBlockTest(blockTest: BlockTest): Boolean {
		return blockTestRepository.deleteBlockTest(blockTest.blockTestId).wasAcknowledged()
	}
}