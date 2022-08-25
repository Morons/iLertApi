package za.co.ilert.core.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
	val email: String,
	val userName: String,
	val password: String,
	val avatarAsString: String,
	val security: UserSecurity? = null,
	val timestamp: Long = System.currentTimeMillis(),
	@BsonId
	val userId: String = ObjectId().toString()
)