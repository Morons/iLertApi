package za.co.ilert.core.data.repository.blocktest

import za.co.ilert.core.data.models.BlockTest
import za.co.ilert.core.data.requests.BlockTestRequest
import za.co.ilert.core.data.requests.DeleteBlockTestRequest
import za.co.ilert.core.data.requests.GenericPageRequest
import za.co.ilert.core.data.responses.BlockTestListRequest

interface BlockTestRepository {

	suspend fun getBlockTest(blockTestId: String): BlockTest?

	suspend fun sumPrimalsWeight(blockTestId: String): Double

	suspend fun insertBlockTest(blockTestRequest: BlockTestRequest): Boolean

	suspend fun deleteBlockTest(deleteBlockTestRequest: DeleteBlockTestRequest): Boolean

	suspend fun getBlockTestsPaged(genericPageRequest: GenericPageRequest):List<BlockTestListRequest>

}