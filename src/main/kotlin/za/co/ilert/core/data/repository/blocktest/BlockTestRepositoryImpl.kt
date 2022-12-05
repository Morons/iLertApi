package za.co.ilert.core.data.repository.blocktest

import org.litote.kmongo.*
import org.litote.kmongo.MongoOperator.sum
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.aggregate
import za.co.ilert.core.data.models.BlockTest
import za.co.ilert.core.data.models.Cut
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

		return with(blockTest) {
			BlockTest(
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
				accumulatedFairValueMarketRelated = accumulatedFairValueMarketRelated,
				cuts = cuts,
				notBalancingReason = notBalancingReason,
				locked = locked,
				timestamp = timestamp
			)
		}
	}

	// TODO: All of these need to be integrated with either userId filters or organizationId filters
	override suspend fun getBlockTestList(): List<BlockTest> {
		return blockTestDb.find().descendingSort(BlockTest::timestamp).toList()
	}

	// TODO: All of these need to be integrated with either userId filters or organizationId filters
	override suspend fun getBlockTestListPaged(genericPageRequest: GenericPageRequest): List<BlockTest> {
		return blockTestDb.find()
			.skip(genericPageRequest.page * genericPageRequest.pageSize)
			.limit(genericPageRequest.pageSize)
			.descendingSort(BlockTest::timestamp)
			.toList()
	}

	override suspend fun sumPrimalsWeight(blockTestId: String): Double {

		return blockTestDb.aggregate<ResultResponse>(
			match(BlockTest::blockTestId eq blockTestId),
			project(
				ResultResponse::sumWeight to sum.from(
					(BlockTest::cuts / Cut::actualCutWeight).projection
				)
			)
		).first()?.sumWeight ?: 0.0
	}

	/**
	 * @param blockTest the blockTestId must be generated before calling this
	 **/
	override suspend fun insertBlockTest(blockTest: BlockTest): Boolean =
		blockTestDb.insertOne(blockTest).wasAcknowledged()

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
					carcassTypeId = blockTest.carcassTypeId,
					timestamp = blockTest.timestamp,
					blockTestId = blockTest.blockTestId
				)
			}
	}
}