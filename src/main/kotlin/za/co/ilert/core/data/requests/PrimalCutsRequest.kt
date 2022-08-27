package za.co.ilert.core.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class PrimalCutsRequest(
	val primalCutsRequest: List<PrimalCutRequest>
)
