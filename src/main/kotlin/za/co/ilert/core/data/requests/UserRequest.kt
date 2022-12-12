package za.co.ilert.core.data.requests

import za.co.ilert.core.data.models.UserSecurity
import za.co.ilert.core.utils.Constants
import za.co.ilert.core.utils.Constants.FILE_SOURCE
import za.co.ilert.core.utils.getByteArray

data class UserRequest(
	val userId: String?,
	val userEmail: String,
	val mobileNumber: String? = "",
	val userName: String,
	val password: String,
	val avatarAsString: String? = getByteArray(filePathName = "$FILE_SOURCE/ic_avatar_default.png"),
	val security: UserSecurity? = UserSecurity(active = true, roles = "BLOCK MAN"),
	val organizationId: String
)
