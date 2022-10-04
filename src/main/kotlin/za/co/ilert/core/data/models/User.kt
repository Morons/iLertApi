package za.co.ilert.core.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant
import java.time.ZoneOffset

data class User(
	val email: String,
	val userName: String,
	val password: String,
	val mobileNumber: String,
	val avatarAsString: String,
	val security: UserSecurity? = null,
	val organizationId: String,
	val timestamp: Long	= Instant.now().atOffset(ZoneOffset.UTC).toEpochSecond(),
	@BsonId
	val userId: String = ObjectId().toString()
)