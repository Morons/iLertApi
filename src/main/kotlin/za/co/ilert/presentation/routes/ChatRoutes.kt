package za.co.ilert.presentation.routes

import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.java.KoinJavaComponent.inject
import za.co.ilert.core.data.repository.util.ApiResponseMessages.UNKNOWN_ERROR_TRY_AGAIN
import za.co.ilert.core.data.requests.GenericIdPageRequest
import za.co.ilert.core.data.requests.GenericPageRequest
import za.co.ilert.core.data.requests.GetChatByUsersRequest
import za.co.ilert.core.data.responses.BasicApiResponse
import za.co.ilert.core.data.responses.ChatResponse
import za.co.ilert.core.data.util.WebSocketObject.MESSAGE
import za.co.ilert.core.data.util.userId
import za.co.ilert.core.data.websocket.WsClientMessage
import za.co.ilert.core.util.Constants.CHATS
import za.co.ilert.core.util.Constants.EXIST
import za.co.ilert.core.util.Constants.MESSAGES
import za.co.ilert.core.util.Constants.USER_CHAT
import za.co.ilert.core.util.Constants.WEB_SOCKET
import za.co.ilert.core.util.Constants.WEB_SOCKET_TEST
import za.co.ilert.core.util.handleWebSocket
import za.co.ilert.presentation.services.chat.ChatController
import za.co.ilert.presentation.services.chat.ChatService


fun Route.getMessagesForChat(chatService: ChatService) {
	authenticate {
		post(MESSAGES) {
			val request = call.receiveOrNull<GenericIdPageRequest>() ?: kotlin.run {
				call.respond(
					status = BadRequest, message = BasicApiResponse<Unit>(
						successful = false, message = UNKNOWN_ERROR_TRY_AGAIN
					)
				)
				return@post
			}
			if (!chatService.doesChatBelongToUser(chatId = request.genericId, userId = call.userId)) {
				call.respond(Forbidden)
				return@post
			}
			val messageList = chatService.getMessagesForChat(
				chatId = request.genericId, page = request.page, pageSize = request.pageSize
			)
			call.respond(status = OK, message = messageList)
		}
	}
}

fun Route.getChatsForSelfPaged(chatService: ChatService) {
	authenticate {
		post(CHATS) {
			val request = call.receiveOrNull<GenericPageRequest>() ?: kotlin.run {
				call.respond(
					status = BadRequest, message = BasicApiResponse<Unit>(
						successful = false, message = UNKNOWN_ERROR_TRY_AGAIN
					)
				)
				return@post
			}
			val chatDtoList: List<ChatResponse> = chatService.getChatsForSelfPaged(
				ownUserId = call.userId, page = request.page, pageSize = request.pageSize
			)
			println("chatDtoList = $chatDtoList")
			call.respond(status = OK, message = chatDtoList)
		}
	}
}

fun Route.doesChatByUsersExist(chatController: ChatController) {
	authenticate {
		post(EXIST) {
			val request = call.receiveOrNull<GetChatByUsersRequest>() ?: kotlin.run {
				call.respond(
					status = BadRequest, message = BasicApiResponse<Unit>(
						successful = false, message = UNKNOWN_ERROR_TRY_AGAIN
					)
				)
				return@post
			}
			val chat = chatController.doesChatByUsersExist(fromId = request.fromId, request.toId)
			call.respond(status = OK, message = chat)
		}
	}
}

fun Route.getChatByUsers(chatController: ChatController) {
	authenticate {
		post(USER_CHAT) {
			val request = call.receiveOrNull<GetChatByUsersRequest>() ?: kotlin.run {
				call.respond(
					status = BadRequest, message = BasicApiResponse<Unit>(
						successful = false, message = UNKNOWN_ERROR_TRY_AGAIN
					)
				)
				return@post
			}
			val chat = chatController.getChatByUsers(fromId = request.fromId, request.toId)
			call.respond(status = OK, message = chat)
		}
	}
}