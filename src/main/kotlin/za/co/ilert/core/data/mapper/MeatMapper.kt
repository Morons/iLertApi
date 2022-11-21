package za.co.ilert.core.data.mapper

import za.co.ilert.core.data.models.CarcassType
import za.co.ilert.core.data.models.CutType
import za.co.ilert.core.data.responses.CarcassTypeResponse
import za.co.ilert.core.data.responses.CutTypeResponse

fun CarcassType.toCarcassTypeResponse(): CarcassTypeResponse {
	return CarcassTypeResponse(
		displayName = displayName,
		carcassTypeId = carcassTypeId
	)
}

fun CutType.toCutTypeResponse(): CutTypeResponse {
	return CutTypeResponse(
		cutName = cutName,
		displayName = displayName,
		organizationId = organizationId,
		isMutable = isMutable,
		carcassTypeId = carcassTypeId,
		cutTypeId = cutTypeId
	)
}