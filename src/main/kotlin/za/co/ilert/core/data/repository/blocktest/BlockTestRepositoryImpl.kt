package za.co.ilert.core.data.repository.blocktest

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import za.co.ilert.core.data.models.BlockTest
import za.co.ilert.core.data.models.PrimalCut
import za.co.ilert.core.data.requests.*
import za.co.ilert.core.data.responses.BlockTestListRequest
import za.co.ilert.core.data.responses.BlockTestResponse
import za.co.ilert.core.data.responses.PrimalCutResponse

class BlockTestRepositoryImpl(
	db: CoroutineDatabase
) : BlockTestRepository {

	private val blockTestDb = db.getCollection<BlockTest>()
	private val primalCutDb = db.getCollection<PrimalCut>()

	override suspend fun getBlockTest(blockTestId: String): BlockTestResponse? {
		val blockTest: BlockTest = blockTestDb.findOne(filter = BlockTest::blockTestId eq blockTestId) ?: return null
		val primalCuts = primalCutDb.find(filter = PrimalCut::blockTestId eq blockTestId)
			.toList()
			.map { primalCut ->
				PrimalCutResponse(
					primalCut.blockTestId,
					primalCut.primalCutType,
					primalCut.actualCutWeight,
					primalCut.marketSellPrice,
					primalCut.timestamp,
					primalCut.primalCutId
				)
			}
		return BlockTestResponse(
			carcassType = blockTest.carcassType,
			carcassKgCostIncl = blockTest.carcassKgCostIncl,
			carcassWeight = blockTest.carcassWeight,
			carcassHangingWeight = blockTest.carcassHangingWeight,
			carcassLoss = blockTest.cutTrimWeight,
			carcassKgWeightLoss = blockTest.carcassKgWeightLoss,
			weightLossParameter = blockTest.weightLossParameter,
			cuttingLossParameter = blockTest.cuttingLossParameter,
			waistParameter = blockTest.wasteParameter,
			percentDifferenceParameter = blockTest.percentDifferenceParameter,
			percentGpRequired = blockTest.percentGpRequired,
			acceptablePriceVariance = blockTest.acceptablePriceVariance,
			trimmingWaste = blockTest.trimmingWaste,
			measuredWeightAfterCuts = blockTest.measuredWeightAfterCuts,
			primalCuts = primalCuts,
			timestamp = blockTest.timestamp,
			blockTestId = blockTest.blockTestId
		)
	}

	override suspend fun getPrimalCuts(blockTestId: String): List<PrimalCutResponse> {
		return primalCutDb.find(filter = PrimalCut::blockTestId eq blockTestId)
			.toList()
			.map { primalCut ->
				PrimalCutResponse(
					primalCut.blockTestId,
					primalCut.primalCutType,
					primalCut.actualCutWeight,
					primalCut.marketSellPrice,
					primalCut.timestamp,
					primalCut.primalCutId
				)
			}
	}

	/**
	 * @param blockTestRequest the blockTestId must be generated before calling this
	 **/
	override suspend fun insertBlockTest(
		blockTestRequest: BlockTestRequest
	): Boolean {
		return blockTestDb.insertOne(blockTestRequest.toBlockTest()).wasAcknowledged()
	}

	/**
	 * @param primalCutsRequest the primalCutId must be generated before calling this
	 * Use the same generated blockTestId in the [primalCutsRequest] and [blockTestRequest]
	 **/
	override suspend fun insertPrimalCuts(
		primalCutsRequest: PrimalCutsRequest
	) {
		if (primalCutsRequest.primalCutsRequest.isNotEmpty()) {
			primalCutsRequest.primalCutsRequest.forEachIndexed { _, primalCutRequest ->
				val primalCut = primalCutRequest.toPrimalCut()
				primalCutDb.insertOne(primalCut)
			}
		}
	}

	override suspend fun deleteBlockTest(deleteBlockTestRequest: DeleteBlockTestRequest): Boolean {

		return if (
			primalCutDb.deleteMany(filter = PrimalCut::blockTestId eq deleteBlockTestRequest.blockTestId)
				.wasAcknowledged()
		) {
			blockTestDb.deleteOneById(deleteBlockTestRequest.blockTestId).wasAcknowledged()
		} else false
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