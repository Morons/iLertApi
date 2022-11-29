package za.co.ilert.core.data.responses

import za.co.ilert.core.data.models.User


data class AuthResponse(
	val user: User,
	val token: String
)
