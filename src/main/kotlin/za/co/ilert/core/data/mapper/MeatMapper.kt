package za.co.ilert.core.data.mapper

import za.co.ilert.core.data.models.BlockTest
import za.co.ilert.core.data.models.CarcassType
import za.co.ilert.core.data.models.Cut
import za.co.ilert.core.data.models.CutType
import za.co.ilert.core.data.requests.BlockTestRequest
import za.co.ilert.core.data.requests.NewBlockTestRequest
import za.co.ilert.core.data.requests.UpsertBlockTestCutRequest
import za.co.ilert.core.data.responses.CarcassTypeResponse
import za.co.ilert.core.data.responses.CutTypeResponse

fun CarcassType.toCarcassTypeResponse(): CarcassTypeResponse {
	return CarcassTypeResponse(
		carcassTypeId = carcassTypeId,
		displayName = displayName
	)
}

fun CutType.toCutTypeResponse(): CutTypeResponse {
	return CutTypeResponse(
		cutTypeId = cutTypeId,
		cutName = cutName,
		displayName = displayName,
		organizationId = organizationId,
		isMutable = isMutable,
		carcassTypeId = carcassTypeId
	)
}

fun BlockTestRequest.toBlockTest(): BlockTest {
	return BlockTest(
		blockTestId = blockTestId,
		userId = userId,
		organizationId = organizationId,
		carcassTypeId = carcassTypeId,
		carcassKgCostIncl = carcassKgCostIncl,
		carcassWeight = carcassWeight,
		carcassHangingWeight = carcassHangingWeight,
		cutTrimWeight = cutTrimWeight,
		weightLossParameter = weightLossParameter,
		cuttingLossParameter = cuttingLossParameter,
		wasteParameter = wasteParameter,
		percentGpRequired = percentGpRequired,
		cuts = cuts,
		notBalancingReason = notBalancingReason,
		locked = locked,
		timestamp = timestamp
	)
}

fun NewBlockTestRequest.toBlockTest(): BlockTest {
	return BlockTest(
		blockTestId = blockTestId,
		userId = userId,
		organizationId = organizationId,
		carcassTypeId = carcassTypeId,
		carcassKgCostIncl = carcassKgCostIncl,
		carcassWeight = carcassWeight,
		carcassHangingWeight = carcassHangingWeight,
		cutTrimWeight = cutTrimWeight,
		weightLossParameter = weightLossParameter,
		cuttingLossParameter = cuttingLossParameter,
		wasteParameter = wasteParameter,
		percentGpRequired = percentGpRequired,
		cuts = emptyList(),
		notBalancingReason = notBalancingReason,
		locked = locked,
		timestamp = timestamp
	)
}

fun UpsertBlockTestCutRequest.toCut(): Cut {
	return Cut(
		cutId = cutId,
		cutTypeId = cutTypeId,
		blockTestId = blockTestId,
		actualCutWeight = actualCutWeight,
		marketSellPrice = marketSellPrice,
		marketValue = marketValue,
		salesPrice = salesPrice,
		actualValue = actualValue,
		cutName = cutName,
		displayName = displayName
	)
}