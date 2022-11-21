package za.co.ilert.core.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class CutType(
	val cutName: String,
	val displayName: String,
	val organizationId: String,
	val isMutable: Boolean,
	val carcassTypeId: String,
	@BsonId
	val cutTypeId: String = ObjectId().toString()
)
