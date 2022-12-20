package za.co.ilert.core.data.requests

import za.co.ilert.core.data.models.Cut

data class UpsertCutRequest(
	val cut: Cut,
	val amend: Boolean
)