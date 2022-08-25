package za.co.ilert.core.data.requests

data class InsertBlockTestRequest(
	val blockTestRequest: BlockTestRequest,
	val primalCutsRequest: List<PrimalCutRequest>
)
