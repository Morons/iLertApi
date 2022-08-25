package za.co.ilert.core.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class DeleteBlockTestRequest(val blockTestId: String)
