package za.co.ilert.core.data.responses

import za.co.ilert.core.data.models.Addresses
import za.co.ilert.core.data.models.OrganizationParameters

data class OrganizationResponse(
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
	val organizationAddress: Addresses,
	val parameters: OrganizationParameters
)
