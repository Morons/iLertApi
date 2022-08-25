package za.co.ilert.core.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class UserSearchResponse(
	val userId: String,
	val userName: String,
	val avatarAsString: String
)