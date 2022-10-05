package za.co.ilert.core.data.responses

data class AddressResponse(
	val label: String,
	val number: String,
	val street: String,
	val neighborhood: String,
	val locality: String,
	val region: String,
	val country: String,
	val code: String
)
