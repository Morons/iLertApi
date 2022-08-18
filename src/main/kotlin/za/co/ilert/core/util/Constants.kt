package za.co.ilert.core.util

object Constants {

	const val DATABASE_NAME = "ilert"
	const val USER_ID = "userId"
	const val ILERT_SESSION = "ILERT_SESSION"
	const val DEFAULT_PAGE_SIZE = 15

	const val FILE_SOURCE = "build/resources/main/static/images"      // Seeder pictures

	private const val PATH = "/"

	const val USER_CREATE = "$PATH/user/create"
	const val USER_LOGIN = "$PATH/user/login"
	const val USER_AUTHENTICATE = "$PATH/user/authenticate"
	const val USER_SEARCH = "$PATH/user/search"

	const val SESSION_INCREMENT = "/session/increment"

	const val WEBSOCKET = "/socket"
}