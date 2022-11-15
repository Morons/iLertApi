package za.co.ilert.core.data.repository.blocktest

import org.litote.kmongo.*
import org.litote.kmongo.MongoOperator.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.aggregate
import za.co.ilert.core.data.models.BlockTest
import za.co.ilert.core.data.models.Cuts
import za.co.ilert.core.data.requests.BlockTestRequest
import za.co.ilert.core.data.requests.DeleteBlockTestRequest
import za.co.ilert.core.data.requests.GenericPageRequest
import za.co.ilert.core.data.responses.BlockTestListResponse
import za.co.ilert.core.data.responses.ResultResponse

class BlockTestRepositoryImpl(
	db: CoroutineDatabase
) : BlockTestRepository {

	private val blockTestDb = db.getCollection<BlockTest>()

	override suspend fun getBlockTest(blockTestId: String): BlockTest? {
		val blockTest: BlockTest = blockTestDb.findOne(filter = BlockTest::blockTestId eq blockTestId) ?: return null
		val sumPrimalsWeight = sumPrimalsWeight(blockTestId)

		return BlockTest(
			userId = blockTest.userId,
			carcassType = blockTest.carcassType,
			carcassKgCostIncl = blockTest.carcassKgCostIncl,
			carcassWeight = blockTest.carcassWeight,
			carcassHangingWeight = blockTest.carcassHangingWeight,
			cutTrimWeight = blockTest.cutTrimWeight,
			weightLossParameter = blockTest.weightLossParameter,
			cuttingLossParameter = blockTest.cuttingLossParameter,
			wasteParameter = blockTest.wasteParameter,
			percentGpRequired = blockTest.percentGpRequired,
			cuts = blockTest.cuts,
			timestamp = blockTest.timestamp,
			blockTestId = blockTest.blockTestId
		)
	}

	override suspend fun sumPrimalsWeight(blockTestId: String): Double {

		return blockTestDb.aggregate<ResultResponse>(
			match(BlockTest::blockTestId eq blockTestId),
			project(ResultResponse::sumWeight to sum.from(
				(BlockTest::cuts / Cuts::actualCutWeight).projection))
		).first()?.sumWeight ?: 0.0
	}

	/**
	 * @param blockTestRequest the blockTestId must be generated before calling this
	 **/
	override suspend fun insertBlockTest(
		blockTestRequest: BlockTestRequest
	): Boolean {
		return blockTestDb.insertOne(blockTestRequest.toBlockTest()).wasAcknowledged()
	}

	override suspend fun deleteBlockTest(deleteBlockTestRequest: DeleteBlockTestRequest): Boolean {
		return blockTestDb.deleteOneById(deleteBlockTestRequest.blockTestId).wasAcknowledged()
	}

	override suspend fun getBlockTestsPaged(genericPageRequest: GenericPageRequest): List<BlockTestListResponse> {
		return blockTestDb.find()
			.skip(genericPageRequest.page * genericPageRequest.pageSize)
			.limit(genericPageRequest.pageSize)
			.descendingSort(BlockTest::timestamp)
			.toList()
			.map { blockTest ->
				BlockTestListResponse(
					carcassType = blockTest.carcassType,
					timestamp = blockTest.timestamp,
					blockTestId = blockTest.blockTestId
				)
			}
	}
}