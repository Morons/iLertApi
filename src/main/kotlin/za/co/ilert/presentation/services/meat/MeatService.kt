package za.co.ilert.presentation.services.meat

import org.bson.types.ObjectId
import za.co.ilert.core.data.mapper.toCutTypeResponse
import za.co.ilert.core.data.models.CarcassType
import za.co.ilert.core.data.models.CutType
import za.co.ilert.core.data.repository.meat.CarcassRepository
import za.co.ilert.core.data.repository.meat.CutRepository
import za.co.ilert.core.data.requests.CarcassTypeRequest
import za.co.ilert.core.data.requests.CreateCarcassTypesRequest
import za.co.ilert.core.data.requests.CreateCutTypesRequest
import za.co.ilert.core.data.requests.CutTypeRequest
import za.co.ilert.core.data.responses.CarcassTypeResponse
import za.co.ilert.core.data.responses.CutTypeResponse

class MeatService(
	private val carcassRepository: CarcassRepository,
	private val cutRepository: CutRepository
) {
	// CarcassType
	suspend fun createCarcassType(carcassTypeRequest: CarcassTypeRequest): Boolean {
		var carcassTypeId = carcassTypeRequest.carcassTypeId
		return carcassRepository.createCarcassType(
			with(carcassTypeRequest) {
				if (carcassTypeId.isEmpty()) carcassTypeId = ObjectId().toString()
				CarcassType(
					displayName = displayName,
					carcassTypeId = carcassTypeId
				)
			}
		)
	}

	suspend fun createCarcassTypes(createCarcassTypesRequest: CreateCarcassTypesRequest): Boolean =
		carcassRepository.createCarcassTypes(createCarcassTypesRequest.carcassTypes)

	suspend fun createCutTypes(createCarcassTypesRequest: CreateCarcassTypesRequest): Boolean =
		carcassRepository.createCarcassTypes(createCarcassTypesRequest.carcassTypes)

	suspend fun upsertCarcassType(carcassType: CarcassType): Boolean = carcassRepository.upsertCarcassType(carcassType)

	suspend fun getCarcassTypeById(carcassTypeId: String): CarcassTypeResponse? {
		val carcassType = carcassRepository.getCarcassTypeById(carcassTypeId) ?: return null
		return with(carcassType) {
			CarcassTypeResponse(
				carcassTypeId = carcassTypeId,
				displayName = displayName
			)
		}
	}

	suspend fun getCarcassTypeList(): List<CarcassType> = carcassRepository.getCarcassTypeList()

	// CutType
	suspend fun createCutType(cutTypeRequest: CutTypeRequest): Boolean {
		var cutTypeId = cutTypeRequest.cutTypeId
		return cutRepository.createCutType(
			with(cutTypeRequest) {
				if (cutTypeId.isEmpty()) cutTypeId = ObjectId().toString()
				CutType(
					cutName = cutName,
					displayName = displayName,
					organizationId = organizationId,
					isMutable = isMutable,
					carcassTypeId = carcassTypeId,
					cutTypeId = cutTypeId
				)
			}
		)
	}

	suspend fun createCutTypes(createCutTypesRequest: CreateCutTypesRequest): Boolean =
		cutRepository.createCutTypes(createCutTypesRequest.cutTypes)

	suspend fun upsertCutType(cutType: CutType): Boolean = cutRepository.upsertCutType(cutType)

	suspend fun getCutTypeById(cutTypeId: String): CutTypeResponse? {
		val cutType = cutRepository.getCutTypeById(cutTypeId) ?: return null
		return with(cutType) {
			CutTypeResponse(
				cutName = cutName,
				displayName = displayName,
				organizationId = organizationId,
				isMutable = isMutable,
				carcassTypeId = carcassTypeId,
				cutTypeId = cutTypeId
			)
		}
	}

	suspend fun getCutTypes(): List<CutType> = cutRepository.getCutTypes()

	suspend fun getCutTypeListByOrganizationId(organizationId: String): List<CutType>? =
		cutRepository.getCutTypeListByOrganizationId(organizationId)

	suspend fun getCutTypesByCarcassTypeId(carcassTypeId: String): List<CutTypeResponse> {
		val cutTypes = cutRepository.getCutTypesByCarcassTypeId(carcassTypeId)
		return if (cutTypes.isEmpty())
			emptyList()
		else
			cutTypes.map { it.toCutTypeResponse() }
	}
}