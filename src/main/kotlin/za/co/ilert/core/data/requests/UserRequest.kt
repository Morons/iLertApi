package za.co.ilert.core.data.requests

import za.co.ilert.core.data.models.UserSecurity

data class UserRequest(
	val userId: String,
	val email: String,
	val mobileNumber: String,
	val userName: String,
	val password: String,
	val avatarAsString: String,
	val security: UserSecurity
)
