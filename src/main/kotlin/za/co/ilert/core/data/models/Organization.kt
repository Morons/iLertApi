package za.co.ilert.core.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant
import java.time.ZoneOffset

data class Organization(
	val organizationName: String,
	val organizationPhone: String,
	val organizationEmail: String,
	val organizationOwnerId: String,
	val industry: String,  // Consumer Goods
	val sector: String, // Retail
	val organizationMobile: String,
	val organizationPreferredIM: String = "WhatsApp",
	val payment: String, // EFT/ VISA/ Crypto etc (tread carefully)
	val organizationPopi: String = "false",
	val coRegistrationNumber: String,
	val VATNumber: String,
	val VATValuePercent: Double,
	val organizationAddress: Addresses,
	val parameters: OrganizationParameters,
	val organizationTimestamp: Long = Instant.now().atOffset(ZoneOffset.UTC).toEpochSecond(),
	@BsonId
	val organizationId: String = ObjectId().toString()
)