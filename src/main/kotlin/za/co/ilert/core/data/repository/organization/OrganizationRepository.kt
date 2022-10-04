package za.co.ilert.core.data.repository.organization

import za.co.ilert.core.data.models.Organization

interface OrganizationRepository {

	suspend fun createOrganization(organization: Organization): Boolean
}