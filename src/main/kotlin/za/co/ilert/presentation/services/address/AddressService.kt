package za.co.ilert.presentation.services.address

import org.bson.types.ObjectId
import za.co.ilert.core.data.models.Addresses
import za.co.ilert.core.data.repository.address.AddressRepository
import za.co.ilert.core.data.requests.AddressRequest

class AddressService(
	private val addressRepository: AddressRepository
) {

	suspend fun createAddress(addressRequest: AddressRequest): Boolean {
		return addressRepository.createAddress(
			with(addressRequest) {
				val addressId: String = ObjectId().toString()
				Addresses(
					label = label,
					number = number,
					street = street,
					neighborhood = neighborhood,
					locality = locality,
					region = region,
					country = country,
					code = code,
					googlePlaceId = googlePlaceId
				)
			}
		)
	}
}