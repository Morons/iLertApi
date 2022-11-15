package za.co.ilert.presentation.services.organization

import org.bson.types.ObjectId
import za.co.ilert.core.data.models.Organization
import za.co.ilert.core.data.repository.organization.OrganizationRepository
import za.co.ilert.core.data.requests.CreateOrganizationRequest

class OrganizationService(
	private val organizationRepository: OrganizationRepository
) {

	suspend fun createOrganization(createOrganizationRequest: CreateOrganizationRequest): Boolean {
		var organizationId = createOrganizationRequest.organizationId
		return organizationRepository.createOrganization(
			with(createOrganizationRequest){
				if(organizationId.isEmpty()) organizationId = ObjectId().toString()
				Organization(
					organizationId = organizationId,
					organizationName = organizationName,
					organizationPhone = organizationPhone,
					organizationEmail = organizationEmail,
					organizationOwnerId = organizationOwnerId,
					industry = industry,
					sector = sector,
					organizationMobile = organizationMobile,
					organizationPreferredIM = organizationPreferredIM,
					payment = payment,
					organizationPopi = organizationPopi,
					coRegistrationNumber = coRegistrationNumber,
					numberVAT = numberVAT,
					valueVATPercent = valueVATPercent,
					address = address,
					parameters = parameters
				)
			}
		)
	}

	suspend fun getOrganization(organizationId: String): Organization? {
		return organizationRepository.getOrganization(organizationId)
	}
}