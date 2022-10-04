package za.co.ilert.core.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant
import java.time.ZoneOffset

data class Message(
	val fromId: String,
	val toId: String,
	val text: String,
	val chatId: String?,
	val timestamp: Long = Instant.now().atOffset(ZoneOffset.UTC).toEpochSecond(),
	@BsonId
	val messageId: String = ObjectId().toString()
)
