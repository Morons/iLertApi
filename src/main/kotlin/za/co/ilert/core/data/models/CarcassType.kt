package za.co.ilert.core.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class CarcassType(
	val displayName: String,
	@BsonId
	val carcassTypeId: String = ObjectId().toString()
)
