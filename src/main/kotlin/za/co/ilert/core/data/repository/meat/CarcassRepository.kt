package za.co.ilert.core.data.repository.meat

import za.co.ilert.core.data.models.CarcassType

interface CarcassRepository {

	suspend fun createCarcassType(carcassType: CarcassType): Boolean
	suspend fun createCarcassTypes(carcassTypes: List<CarcassType>): Boolean
	suspend fun upsertCarcassType(carcassType: CarcassType): Boolean
	suspend fun getCarcassTypeById(carcassTypeId: String) : CarcassType?
	suspend fun getCarcassTypeList(): List<CarcassType>
}