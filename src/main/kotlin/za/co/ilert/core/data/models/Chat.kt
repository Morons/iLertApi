package za.co.ilert.core.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Chat(
	val chatUserIds: List<String>,
	val lastMessageId: String,
	val timestamp: Long,
	@BsonId
	val chatId: String = ObjectId().toString()
)