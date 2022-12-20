package za.co.ilert.core.data.requests

data class CutTypeRequest(
	val cutName: String,
	val organizationId: String,
	val isMutable: Boolean,
	val carcassTypeId: String,
	val cutTypeId: String
)
