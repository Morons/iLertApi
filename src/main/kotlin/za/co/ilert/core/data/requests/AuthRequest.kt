package za.co.ilert.core.data.requests


data class AuthRequest(
	val email: String,
	val userName: String,
	val password: String
)
