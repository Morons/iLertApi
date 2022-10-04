package za.co.ilert.core.data.repository.organization

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.insertOne
import za.co.ilert.core.data.models.Organization

class OrganizationRepositoryImpl(
	db: CoroutineDatabase
) : OrganizationRepository {

	private val organizationDb = db.getCollection<Organization>()

	override suspend fun createOrganization(organization: Organization): Boolean {
		return organizationDb.insertOne(organization).wasAcknowledged()
	}
}