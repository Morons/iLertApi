package za.co.ilert.core.data.requests

import za.co.ilert.core.util.Constants.DEFAULT_PAGE_SIZE


data class UserSearchRequest(
	val userSearch: String,
	val page: Int = 0,
	val pageSize: Int = DEFAULT_PAGE_SIZE
)