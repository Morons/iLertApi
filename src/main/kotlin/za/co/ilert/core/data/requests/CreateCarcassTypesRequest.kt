package za.co.ilert.core.data.requests

import za.co.ilert.core.data.models.CarcassType


data class CreateCarcassTypesRequest(
	val carcassTypes: List<CarcassType>
)
