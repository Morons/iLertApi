package za.co.ilert.presentation.services.address

import za.co.ilert.core.data.models.Address
import za.co.ilert.core.data.repository.address.AddressRepository
import za.co.ilert.core.data.requests.AddressRequest

class AddressService(
	private val addressRepository: AddressRepository
) {

	suspend fun createAddress(addressRequest: AddressRequest): Boolean {
		return addressRepository.createAddress(
			with(addressRequest) {
				Address(
					label = label,
					premiseNumber = premiseNumber,
					premise = premise,
					streetNumber = streetNumber,
					street = street,
					neighborhood = neighborhood,
					locality = locality,
					region = region,
					country = country,
					code = code
				)
			}
		)
	}
}