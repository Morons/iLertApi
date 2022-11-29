package za.co.ilert.core.data.repository.user

import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import za.co.ilert.core.data.models.User
import za.co.ilert.core.data.requests.UpdateUserRequest
import za.co.ilert.core.data.responses.UserResponse
import za.co.ilert.core.utils.Constants
import za.co.ilert.core.utils.getByteArray

class UserRepositoryImpl(
	db: CoroutineDatabase
) : UserRepository {

	private val usersDb = db.getCollection<User>()

	override suspend fun createUser(user: User): Boolean {
		return usersDb.insertOne(user).wasAcknowledged()
	}

	override suspend fun getUser(userId: String): User? {
		return usersDb.findOne(User::userId eq userId)
	}

	override suspend fun upsertUser(user: User): Boolean {
		return usersDb.updateOneById(id = User::userId eq user.userId, update = user, options = upsert())
			.wasAcknowledged()
	}

	override suspend fun updateUser(isOwnProfile: Boolean, updateUserRequest: UpdateUserRequest): Boolean {
		val userToSave = if (!updateUserRequest.userId.isNullOrBlank()) {
			val user = getUserById(updateUserRequest.userId) ?: return false
			UserResponse(
				userId = updateUserRequest.userId,
				email = updateUserRequest.userEmail,
				mobileNumber = updateUserRequest.mobileNumber ?: "",
				userName = updateUserRequest.userName,
				password = user.password,
				avatarAsString = updateUserRequest.avatarAsString
					?: getByteArray(filePathName = "${Constants.FILE_SOURCE}/ic_avatar_default.png"),
				security = updateUserRequest.security,
				organizationId = updateUserRequest.organizationId,
				isOwnProfile = isOwnProfile
			)
		} else {
			return false
		}
		return usersDb.updateOneById(
			id = updateUserRequest.userId,
			update = userToSave,
			updateOnlyNotNullProperties = true
		).wasAcknowledged()
	}

	override suspend fun getUserById(userId: String): User? {
		return usersDb.findOneById(userId)
	}

	override suspend fun getUserListById(userList: List<String>): List<User> {
		return usersDb.find(filter = User::userId `in` userList).toList()
	}

	override suspend fun getUserByEmail(email: String): User? {
		return usersDb.findOne(filter = User::userEmail eq email)
	}

	override suspend fun getUserByUsername(userName: String): User? {
		return usersDb.findOne(filter = User::userName eq userName)
	}

	override suspend fun doesLoginValueMatchForUser(loginValue: String, enteredPassword: String): Boolean {
		val user = getUserByEmail(loginValue) ?: getUserByUsername(loginValue)
		return if (user != null) user.password == enteredPassword else false
	}

	override suspend fun doesEmailBelongToUserId(email: String, userId: String): Boolean {
		return usersDb.findOneById(userId)?.userEmail == email
	}

	override suspend fun searchForUsers(userQuery: String, page: Int, pageSize: Int): List<User> {
		return usersDb.find(
			filter = or(
				User::userName regex Regex(pattern = ".*$userQuery.*", option = RegexOption.IGNORE_CASE),
				User::userEmail eq userQuery
			)
		).skip(page * pageSize).limit(pageSize).descendingSort(User::userName).toList()
	}
}