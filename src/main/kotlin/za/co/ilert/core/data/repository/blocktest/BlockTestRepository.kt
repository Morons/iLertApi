package za.co.ilert.core.data.repository.blocktest

import za.co.ilert.core.data.requests.BlockTestRequest
import za.co.ilert.core.data.requests.DeleteBlockTestRequest
import za.co.ilert.core.data.requests.GenericPageRequest
import za.co.ilert.core.data.requests.PrimalCutRequest
import za.co.ilert.core.data.responses.BlockTestListRequest
import za.co.ilert.core.data.responses.BlockTestResponse
import za.co.ilert.core.data.responses.PrimalCutResponse

interface BlockTestRepository {

	suspend fun getBlockTest(blockTestId: String): BlockTestResponse?

	suspend fun getPrimalCuts(blockTestId: String): List<PrimalCutResponse>

	suspend fun insertBlockTest(blockTestRequest: BlockTestRequest, primalCutsRequest: List<PrimalCutRequest>): Boolean

	suspend fun deleteBlockTest(deleteBlockTestRequest: DeleteBlockTestRequest): Boolean

	suspend fun getBlockTestsPaged(genericPageRequest: GenericPageRequest):List<BlockTestListRequest>

}