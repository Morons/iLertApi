package za.co.ilert.core.util

object Constants {

	const val DATABASE_NAME = "ilert"
	const val USER_ID = "userId"
	const val ILERT_SESSION = "ILERT_SESSION"
	const val DEFAULT_PAGE_SIZE = 15

	const val FILE_SOURCE = "build/resources/main/static/images"      // Seeder pictures

	// API Path
	private const val PATH = "/v1"

	// User Paths
	const val USER = "$PATH/user"
	const val USER_CREATE = "$PATH/user/create"
	const val USER_LOGIN = "$PATH/user/login"
	const val USER_AUTHENTICATE = "$PATH/user/authenticate"
	const val USER_SEARCH = "$PATH/user/search"

	// Session Paths
	const val SESSION_INCREMENT = "/session/increment"

	// Websocket Paths
	const val WEB_SOCKET = "$PATH/webSocket"
	const val WEB_SOCKET_TEST = "$PATH/webSocket/test"

	// Chat Paths
	const val MESSAGES ="$PATH/chat/messages"
	const val CHATS = "$PATH/chat/chats"
	const val EXIST = "$PATH/chat/exist"
	const val USER_CHAT = "$PATH/userChat"
}