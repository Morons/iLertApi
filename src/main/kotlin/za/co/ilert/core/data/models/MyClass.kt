package za.co.ilert.core.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant
import java.time.ZoneOffset

data class MyClass(
	val items: List<Item>,
	val timestamp: Long = Instant.now().atOffset(ZoneOffset.UTC).toEpochSecond(),
	@BsonId
	val blockTestId: String = ObjectId().toString()
)

data class Item(
	val name: String,
	val price: Double,
	val qty: Double
)