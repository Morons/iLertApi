package za.co.ilert.core.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


data class UserSecurity(
	val userId: String,
	val active: Boolean,
	var roles: String?,  // Valid Roles = "ADMIN, MANAGER, BLOCK MAN"
	@BsonId
	val securityId: String = ObjectId().toString()
) {
//	
//	val roleList: List<String>
//		get() = if (this.roles?.isNotEmpty()!!) {
//			listOf(*this.roles?.split(",".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()!!)
//		} else ArrayList()
}
