package za.co.ilert.core.data.repository.meat

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.upsert
import za.co.ilert.core.data.models.CutType

class CutRepositoryImpl(db: CoroutineDatabase) : CutRepository {

	private val cutDb = db.getCollection<CutType>()

	override suspend fun createCutType(cutType: CutType): Boolean {
		return cutDb.insertOne(cutType).wasAcknowledged()
	}

	override suspend fun createCutTypes(cutTypes: List<CutType>): Boolean {
		return cutDb.insertMany(cutTypes).wasAcknowledged()
	}

	override suspend fun upsertCutType(cutType: CutType): Boolean {
		return cutDb.updateOneById(
			id = CutType::cutTypeId eq cutType.cutTypeId,
			update = cutType,
			options = upsert()
		).wasAcknowledged()
	}

	override suspend fun getCutTypeById(cutTypeId: String): CutType? {
		return cutDb.findOneById(cutTypeId)
	}

	override suspend fun getCutTypes(): List<CutType> = cutDb.find().descendingSort(CutType::cutName).toList()

	override suspend fun getCutTypeListByOrganizationId(organizationId: String): List<CutType> {
		return cutDb
			.find(filter = CutType::organizationId eq organizationId)
			.descendingSort(CutType::cutName)
			.toList()
	}

	override suspend fun getCutTypesByCarcassTypeId(carcassTypeId: String): List<CutType> {
		return cutDb
			.find(filter = CutType::carcassTypeId eq carcassTypeId)
			.descendingSort(CutType::cutName)
			.toList()
	}
}