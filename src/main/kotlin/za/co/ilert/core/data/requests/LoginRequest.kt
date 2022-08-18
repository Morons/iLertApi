package za.co.ilert.core.data.requests

data class LoginRequest(
	val email: String? = null,
	val userName: String? = null,
	val password: String
)
