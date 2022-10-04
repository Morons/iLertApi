package za.co.ilert.core.data.repository.address

import org.litote.kmongo.coroutine.CoroutineDatabase
import za.co.ilert.core.data.models.Addresses

class AddressRepositoryImpl(
	db: CoroutineDatabase
) : AddressRepository {

	private val addressDb = db.getCollection<Addresses>()

	override suspend fun createAddress(addresses: Addresses): Boolean {
		return addressDb.insertOne(addresses).wasAcknowledged()
	}
}