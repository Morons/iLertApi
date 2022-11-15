package za.co.ilert.core.data.responses


data class AuthResponse(
	val userId: String,
	val organizationId: String,
	val token: String
)
