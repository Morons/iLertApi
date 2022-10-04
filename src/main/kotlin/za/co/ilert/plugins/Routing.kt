package za.co.ilert.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import za.co.ilert.presentation.routes.address.createAddress
import za.co.ilert.presentation.routes.auth.authenticate
import za.co.ilert.presentation.routes.auth.createUser
import za.co.ilert.presentation.routes.auth.loginUser
import za.co.ilert.presentation.routes.blocktest.getBlockTest
import za.co.ilert.presentation.routes.blocktest.insertBlockTest
import za.co.ilert.presentation.routes.chat.*
import za.co.ilert.presentation.routes.organization.createOrganization
import za.co.ilert.presentation.routes.user.getUser
import za.co.ilert.presentation.routes.user.getUserByPost
import za.co.ilert.presentation.routes.user.searchUser
import za.co.ilert.presentation.routes.user.updateUserProfile
import za.co.ilert.presentation.services.address.AddressService
import za.co.ilert.presentation.services.blocktest.BlockTestService
import za.co.ilert.presentation.services.chat.ChatController
import za.co.ilert.presentation.services.chat.ChatService
import za.co.ilert.presentation.services.organization.OrganizationService
import za.co.ilert.presentation.services.user.UserService

fun Application.configureRouting() {

	val userService: UserService by inject()
	val organizationService: OrganizationService by inject()
	val addressService: AddressService by inject()
	val blockTestService: BlockTestService by inject()
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

		// Organization Routes
		createOrganization(organizationService = organizationService)

		// Address Routes
		createAddress(addressService = addressService)

		// User Routes
		searchUser(userService = userService)
		getUser(userService = userService)
		getUserByPost(userService = userService)
		updateUserProfile(userService = userService)

		// BlockTest Routes
		getBlockTest(blockTestService = blockTestService)
		insertBlockTest(blockTestService = blockTestService)
//		insertPrimalCuts(blockTestService = blockTestService)

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