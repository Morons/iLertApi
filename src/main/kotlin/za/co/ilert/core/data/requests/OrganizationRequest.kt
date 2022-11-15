package za.co.ilert.core.data.requests

import za.co.ilert.core.data.models.Address
import za.co.ilert.core.data.models.Parameters
import za.co.ilert.core.data.models.User

data class OrganizationRequest(
	val organizationId: String,
	val organizationName: String,
	val organizationPhone: String,
	val organizationEmail: String,
	val owner: User,
	val industry: String, // Consumer Goods
	val sector: String, // Retail
	val organizationMobile: String,
	val organizationPreferredIM: String = "WhatsApp",
	val payment: String, // EFT/ VISA/ Crypto etc (tread carefully)
	val organizationPopi: String = "false",
	val coRegistrationNumber: String,
	val numberVAT: String,
	val valueVATPercent: Double,
	val address: Address,
	val parameters: Parameters,
	val organizationTimestamp: Long
)
