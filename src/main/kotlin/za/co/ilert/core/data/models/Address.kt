package za.co.ilert.core.data.models

data class Address(
	val label: String,
	val premiseNumber: String,
	val premise: String,
	val streetNumber: String,
	val street: String,
	val neighborhood: String,
	val locality: String,
	val region: String,
	val country: String,
	val code: String
)