package za.co.ilert.core.data.responses

data class CutTypeResponse(
	val cutName: String,
	val organizationId: String,
	val isMutable: Boolean,
	val carcassTypeId: String,
	val cutTypeId: String
)
