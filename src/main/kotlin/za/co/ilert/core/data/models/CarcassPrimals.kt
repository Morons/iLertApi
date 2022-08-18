package za.co.ilert.core.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class CarcassPrimals(
	val carcassType: Int, // Front Quarter, Hind Quarter, Pork, Lamb
	val carcassMeatType: Int, // Beef, Pork, Lamb
	val description: String,
	@BsonId
	val primalId: String = ObjectId().toString()
)
