package za.co.ilert.core.data.requests

data class AddressRequest(
	val label: String,
	val premise: String,
	val premiseNumber: String,
	val street: String,
	val streetNumber: String,
	val neighborhood: String,
	val locality: String,
	val region: String,
	val country: String,
	val code: String
)
