package za.co.ilert.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import za.co.ilert.presentation.routes.*
import za.co.ilert.presentation.services.UserService
import za.co.ilert.presentation.services.chat.ChatController
import za.co.ilert.presentation.services.chat.ChatService

fun Application.configureRouting() {

	val userService: UserService by inject()
	val chatService: ChatService by inject()
	val chatController: ChatController by inject()


	val jwtIssuer = environment.config.property("jwt.domain").getString()
	val jwtAudience = environment.config.property("jwt.audience").getString()
	val jwtSecret = environment.config.property("jwt.secret").getString()

	routing {

		// Auth Routes
		authenticate()
		createUser(userService = userService)
		loginUser(
			userService = userService,
			jwtIssuer = jwtIssuer,
			jwtAudience = jwtAudience,
			jwtSecret = jwtSecret,
		)

		// User Routes
		searchUser(userService = userService)
		getUser(userService = userService)
		updateUserProfile(userService = userService)

		// Session Routes
		sessionIncrement()

		// Websocket Routes
		chatWebSocket(chatController = chatController)
		testRoute()

		// Chat Routes
		getMessagesForChat(chatService = chatService)
		getChatsForSelfPaged(chatService = chatService)
		doesChatByUsersExist(chatController = chatController)
		getChatByUsers(chatController = chatController)

		// Static plugin. Try to access `/static/index.html`
		static(remotePath = "/static") {
			resources(resourcePackage = "static")
		}
	}
}
