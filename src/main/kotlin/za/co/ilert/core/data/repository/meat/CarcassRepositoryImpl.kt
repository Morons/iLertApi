package za.co.ilert.core.data.repository.meat

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.upsert
import za.co.ilert.core.data.models.CarcassType

class CarcassRepositoryImpl(db: CoroutineDatabase) : CarcassRepository {

	private val carcassDb = db.getCollection<CarcassType>()

	override suspend fun createCarcassType(carcassType: CarcassType): Boolean {
		return carcassDb.insertOne(carcassType).wasAcknowledged()
	}

	override suspend fun createCarcassTypes(carcassTypes: List<CarcassType>): Boolean {
		return carcassDb.insertMany(carcassTypes).wasAcknowledged()
	}

	override suspend fun upsertCarcassType(carcassType: CarcassType): Boolean {
		return carcassDb.updateOneById(
			id = CarcassType::carcassTypeId eq carcassType.carcassTypeId,
			update = carcassType,
			options = upsert()
		).wasAcknowledged()
	}

	override suspend fun getCarcassTypeById(carcassTypeId: String): CarcassType? {
		return carcassDb.findOneById(carcassTypeId)
	}

	override suspend fun getCarcassTypeList(): List<CarcassType> {
		return carcassDb.find().ascendingSort(CarcassType::displayName).toList()
	}
}