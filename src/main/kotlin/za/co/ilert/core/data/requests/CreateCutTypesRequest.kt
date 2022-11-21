package za.co.ilert.core.data.requests

import za.co.ilert.core.data.models.CutType

data class CreateCutTypesRequest(
	val cutTypes: List<CutType>
)