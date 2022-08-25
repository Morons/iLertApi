package za.co.ilert.core.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
	val email: String,
	val userName: String,
	val password: String
)
