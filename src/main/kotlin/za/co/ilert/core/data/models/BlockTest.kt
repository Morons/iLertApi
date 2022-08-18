package za.co.ilert.core.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class BlockTest(
	val primalId: String,
	val carcassKgPrice: Long,
	val carcassWeight: Long,
	val carcassLoss: Long, // Weight after Cutting & W.Loss
	val carcassKgWeightLoss: Long,
	val timestamp: Long,
	@BsonId
	val testId: String = ObjectId().toString()
) {
	val carcassCost: Long
		get() = carcassKgPrice * carcassWeight

	val carcassEffectivePrice: Long
		get() = carcassCost / carcassKgWeightLoss // (carcassKgPrice * carcassWeight) / carcassWeightLoss

	val carcassPercentLoss: Long
		get() = (carcassWeight - carcassKgWeightLoss) / carcassWeight
}
