package za.co.ilert.core.data.repository.blocktest

import za.co.ilert.core.data.models.BlockTest
import za.co.ilert.core.data.requests.DeleteBlockTestRequest
import za.co.ilert.core.data.requests.GenericPageRequest
import za.co.ilert.core.data.responses.BlockTestListResponse

interface BlockTestRepository {

	suspend fun getBlockTestById(blockTestId: String): BlockTest?

	suspend fun getBlockTestList(): List<BlockTest>

	suspend fun getBlockTestListPaged(genericPageRequest: GenericPageRequest): List<BlockTest>

	suspend fun sumPrimalsWeight(blockTestId: String): Double

	suspend fun insertBlockTest(blockTest: BlockTest): Boolean

	suspend fun upsertBlockTest(blockTest: BlockTest): Boolean

	suspend fun deleteBlockTest(deleteBlockTestRequest: DeleteBlockTestRequest): Boolean

	suspend fun getBlockTestsPaged(genericPageRequest: GenericPageRequest):List<BlockTestListResponse>

}