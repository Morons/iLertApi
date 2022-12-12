package za.co.ilert.core.data.models

import org.bson.codecs.pojo.annotations.BsonId


data class BlockTest(
	@BsonId
	val blockTestId: String,
	val userId: String,
	val organizationId: String,
	val carcassTypeId: String, // Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
	val carcassKgCostIncl: Double,
	val carcassWeight: Double,
	val carcassHangingWeight: Double,
	val cutTrimWeight: Double = 0.0, // Weight after Cutting & W.Loss
	val weightLossParameter: Double,
	val cuttingLossParameter: Double,
	val wasteParameter: Double,
	val percentGpRequired: Double,
	val cuts: List<Cut>,
	val notBalancingReason: String,
	val locked: Boolean,
	val timestamp: Long
)