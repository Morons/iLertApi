package za.co.ilert.core.data.repository.chat

import org.bson.BsonInvalidOperationException
import org.litote.kmongo.and
import org.litote.kmongo.contains
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue
import za.co.ilert.core.data.models.Chat
import za.co.ilert.core.data.models.Message
import za.co.ilert.core.data.models.User
import za.co.ilert.core.data.responses.ChatResponse
import za.co.ilert.core.utils.Constants.FILE_SOURCE
import za.co.ilert.core.utils.SystemUtils

class ChatRepositoryImpl(db: CoroutineDatabase) : ChatRepository {

	private val chatDb = db.getCollection<Chat>()
	private val userDb = db.getCollection<User>()
	private val messageDb = db.getCollection<Message>()

	override suspend fun getMessagesForChat(chatId: String, page: Int, pageSize: Int): List<Message> {
		return messageDb.find(filter = Message::chatId eq chatId).skip(page * pageSize).limit(pageSize)
			.ascendingSort(Message::timestamp).toList()
	}

	override suspend fun getChatsForSelfPaged(ownUserId: String, page: Int, pageSize: Int): List<ChatResponse> {
		return chatDb.find(filter = Chat::chatUserIds.contains(ownUserId))
			.skip(skip = page * pageSize).limit(limit = pageSize).descendingSort(Chat::timestamp).toList()
			.map { chat ->
				val remoteUserId = chat.chatUserIds.find { it != ownUserId } ?: return emptyList()
				val user = userDb.findOneById(id = remoteUserId) ?: return emptyList()
				val lastMessage = messageDb.findOneById(id = chat.lastMessageId) ?: return emptyList()
				ChatResponse(
					chatId = chat.chatId,
					remoteUserId = user.userId,
					remoteUserName = user.userName,
					remoteAvatarAsString = user.avatarAsString
						?: SystemUtils.getByteArray(filePathName = "$FILE_SOURCE/ic_avatar_default.png"),
					lastMessage = lastMessage.text,
					lastMessageTimestamp = lastMessage.timestamp
				)
			}
	}

	override suspend fun doesChatBelongToUser(chatId: String, userId: String): Boolean {
		return chatDb.findOneById(chatId)?.chatUserIds?.any { it == userId } == true
	}

	override suspend fun insertMessage(message: Message) {
		messageDb.insertOne(document = message)
	}

	override suspend fun insertChat(fromId: String, toId: String, messageId: String) {
		val chat = Chat(
			chatUserIds = listOf(fromId, toId),
			lastMessageId = messageId,
			timestamp = System.currentTimeMillis()
		)
		try {
			chatDb.insertOne(document = chat)
		} catch (e: BsonInvalidOperationException) {
			e.stackTrace
		}
	}

	override suspend fun getChatByUsers(fromId: String, toId: String): List<Chat> {
		return chatDb.find(
			filter = and(Chat::chatUserIds contains fromId, Chat::chatUserIds contains toId)
		).toList()
	}

	override suspend fun doesChatByUsersExist(fromId: String, toId: String): Boolean {
		return getChatByUsers(fromId = fromId, toId = toId).isNotEmpty()
	}

	override suspend fun updateLastMessageIdForChat(chatId: String, lastMessageId: String) {
		messageDb.updateOneById(lastMessageId, setValue(Message::chatId, chatId))
	}
}