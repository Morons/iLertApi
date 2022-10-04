package za.co.ilert.core.data.models

data class OrganizationParameters(
	val salesTaxPercent: Double,
	val weightLossParameter: Double,
	val cuttingLossParameter: Double,
	val wasteParameter: Double,
	val percentDifferenceParameter: Double,
	val percentGpRequired: Double
)