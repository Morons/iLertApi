package za.co.ilert.core.data.models


data class UserSecurity(
	val active: Boolean,
	var roles: String?,  // Valid Roles = "ADMIN, MANAGER, BLOCK MAN"
) {

//	val roleList: List<String>
//		get() = if (this.roles?.isNotEmpty()!!) {
//			listOf(*this.roles?.split(",".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()!!)
//		} else ArrayList()
}
