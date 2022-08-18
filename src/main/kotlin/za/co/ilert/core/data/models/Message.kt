package za.co.ilert.core.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Message(
	val fromId: String,
	val toId: String,
	val text: String,
	val chatId: String?,
	val timestamp: Long,
	@BsonId
	val messageId: String = ObjectId().toString()
)
