package za.co.ilert.core.data.responses

import kotlinx.serialization.Serializable
import za.co.ilert.core.data.models.UserSecurity
@Serializable
data class UserResponse(
	val userId: String,
	val email: String,
	val userName: String,
	val password: String,
	val avatarAsString: String,
	val security: UserSecurity?
)