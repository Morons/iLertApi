package za.co.ilert.core.data.repository.address

import za.co.ilert.core.data.models.Addresses

interface AddressRepository {

	suspend fun createAddress(addresses: Addresses): Boolean
}