package za.co.ilert.core.data.repository.address

import org.litote.kmongo.coroutine.CoroutineDatabase
import za.co.ilert.core.data.models.Address

class AddressRepositoryImpl(
	db: CoroutineDatabase
) : AddressRepository {

	private val addressDb = db.getCollection<Address>()

	override suspend fun createAddress(address: Address): Boolean {
		return addressDb.insertOne(address).wasAcknowledged()
	}
}