package za.co.ilert.core.data.repository.blocktest

import org.litote.kmongo.*
import org.litote.kmongo.MongoOperator.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.aggregate
import za.co.ilert.core.data.models.BlockTest
import za.co.ilert.core.data.models.PrimalCut
import za.co.ilert.core.data.requests.BlockTestRequest
import za.co.ilert.core.data.requests.DeleteBlockTestRequest
import za.co.ilert.core.data.requests.GenericPageRequest
import za.co.ilert.core.data.responses.BlockTestListRequest
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
			carcassKgWeightLoss = blockTest.carcassKgWeightLoss,
			weightLossParameter = blockTest.weightLossParameter,
			cuttingLossParameter = blockTest.cuttingLossParameter,
			wasteParameter = blockTest.wasteParameter,
			percentDifferenceParameter = blockTest.percentDifferenceParameter,
			percentGpRequired = blockTest.percentGpRequired,
			acceptablePriceVariance = blockTest.acceptablePriceVariance,
			trimmingWaste = blockTest.trimmingWaste,
			measuredWeightAfterCuts = blockTest.measuredWeightAfterCuts,
			primalCuts = blockTest.primalCuts,
			sumPrimalsWeight = sumPrimalsWeight,
			timestamp = blockTest.timestamp,
			blockTestId = blockTest.blockTestId
		)
	}

	override suspend fun sumPrimalsWeight(blockTestId: String): Double {

		return blockTestDb.aggregate<ResultResponse>(
			match(BlockTest::blockTestId eq blockTestId),
			project(ResultResponse::sumWeight to sum.from(
				(BlockTest::primalCuts / PrimalCut::actualCutWeight).projection))
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

	override suspend fun getBlockTestsPaged(genericPageRequest: GenericPageRequest): List<BlockTestListRequest> {
		return blockTestDb.find()
			.skip(genericPageRequest.page * genericPageRequest.pageSize)
			.limit(genericPageRequest.pageSize)
			.descendingSort(BlockTest::timestamp)
			.toList()
			.map { blockTest ->
				BlockTestListRequest(
					carcassType = blockTest.carcassType,
					timestamp = blockTest.timestamp,
					blockTestId = blockTest.blockTestId
				)
			}
	}
}