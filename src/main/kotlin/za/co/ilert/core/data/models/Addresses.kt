package za.co.ilert.core.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant
import java.time.ZoneOffset

data class Addresses(
	val label: String,
	val number: String,
	val street: String,
	val neighborhood: String,
	val locality: String,
	val region: String,
	val country: String,
	val code: String,
	val googlePlaceId: String,
	val addressTimestamp: Long = Instant.now().atOffset(ZoneOffset.UTC).toEpochSecond(),
	@BsonId
	val addressId: String = ObjectId().toString()
)