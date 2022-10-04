package za.co.ilert.core.data.models

data class Addresses(
	val label: String,
	val number: String,
	val street: String,
	val neighborhood: String,
	val locality: String,
	val region: String,
	val country: String,
	val code: String,
	val googlePlaceId: String,
)