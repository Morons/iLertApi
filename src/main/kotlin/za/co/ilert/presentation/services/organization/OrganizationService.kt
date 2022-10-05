package za.co.ilert.presentation.services.organization

import org.bson.types.ObjectId
import za.co.ilert.core.data.models.Organization
import za.co.ilert.core.data.repository.organization.OrganizationRepository
import za.co.ilert.core.data.requests.OrganizationRequest

class OrganizationService(
	private val organizationRepository: OrganizationRepository
) {

	suspend fun createOrganization(organizationRequest: OrganizationRequest): Boolean {
		var organizationId = organizationRequest.organizationId
		return organizationRepository.createOrganization(
			with(organizationRequest){
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
					VATNumber = VATNumber,
					VATValuePercent = VATValuePercent,
					organizationAddress = organizationAddress,
					parameters = parameters
				)
			}
		)
	}
}