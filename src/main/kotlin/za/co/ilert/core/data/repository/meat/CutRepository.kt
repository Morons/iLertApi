package za.co.ilert.core.data.repository.meat

import za.co.ilert.core.data.models.CutType

interface CutRepository {

	suspend fun createCutType(cutType: CutType): Boolean
	suspend fun createCutTypes(cutTypes: List<CutType>): Boolean
	suspend fun upsertCutType(cutType: CutType): Boolean
	suspend fun getCutTypeById(cutTypeId: String) : CutType?
	suspend fun getCutTypeList(): List<CutType>
	suspend fun getCutTypeListByOrganizationId(organizationId: String): List<CutType>?

}