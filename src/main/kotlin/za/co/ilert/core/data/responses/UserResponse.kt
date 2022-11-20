package za.co.ilert.core.data.responses

import za.co.ilert.core.data.models.UserSecurity

data class UserResponse(
	val userId: String,
	val email: String,
	val mobileNumber: String,
	val userName: String,
	val password: String,
	val avatarAsString: String,
	val security: UserSecurity?,
	val organizationId: String,
	val isOwnProfile: Boolean
)