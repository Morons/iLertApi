package za.co.ilert.core.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import za.co.ilert.core.utils.Constants
import za.co.ilert.core.utils.getByteArray
import java.time.Instant
import java.time.ZoneOffset

data class User(
	val userEmail: String,
	val userName: String,
	val password: String,
	val mobileNumber: String? = "",
	val avatarAsString: String? = getByteArray(filePathName = "${Constants.FILE_SOURCE}/ic_avatar_default.png"),
	val security: UserSecurity? = UserSecurity(active = true, roles = "BLOCK MAN"),
	val organizationId: String,
	val timestamp: Long	= Instant.now().atOffset(ZoneOffset.UTC).toEpochSecond(),
	@BsonId
	val userId: String = ObjectId().toString()
)