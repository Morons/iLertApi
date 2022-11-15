package za.co.ilert.core.data.responses


data class UserSearchResponse(
	val userId: String,
	val userName: String,
	val userEmail: String,
	val avatarAsString: String
)