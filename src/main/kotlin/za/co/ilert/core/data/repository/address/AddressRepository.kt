package za.co.ilert.core.data.repository.address

import za.co.ilert.core.data.models.Address

interface AddressRepository {

	suspend fun createAddress(address: Address): Boolean
}