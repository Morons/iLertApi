package za.co.ilert.core.data.requests

import za.co.ilert.core.data.models.Addresses
import za.co.ilert.core.data.models.OrganizationParameters

data class OrganizationRequest(
	val organizationId: String,
	val organizationName: String,
	val organizationPhone: String,
	val organizationEmail: String,
	val organizationOwnerId: String,
	val industry: String, // Consumer Goods
	val sector: String, // Retail
	val organizationMobile: String,
	val organizationPreferredIM: String = "WhatsApp",
	val payment: String, // EFT/ VISA/ Crypto etc (tread carefully)
	val organizationPopi: String = "false",
	val coRegistrationNumber: String,
	val VATNumber: String,
	val VATValuePercent: Double,
	val organizationAddress: Addresses,
	val parameters: OrganizationParameters
)
