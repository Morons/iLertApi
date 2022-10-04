package za.co.ilert.core.data.repository.user

import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import za.co.ilert.core.data.models.User
import za.co.ilert.core.data.requests.UserRequest
import za.co.ilert.core.data.responses.UserResponse

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

	override suspend fun updateUser(userRequest: UserRequest): Boolean {
		val user = getUserById(userRequest.userId) ?: return false
		val userToSave = UserResponse(
			userId = userRequest.userId,
			email = user.email,
			userName = user.userName,
			password = user.password,
			avatarAsString = user.avatarAsString,
			security = user.security
		)
		return usersDb.updateOneById(
			id = userRequest.userId,
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
		return usersDb.findOne(filter = User::email eq email)
	}

	override suspend fun getUserByUsername(userName: String): User? {
		return usersDb.findOne(filter = User::userName eq userName)
	}

	override suspend fun doesLoginValueMatchForUser(loginValue: String, enteredPassword: String): Boolean {
		val user = getUserByEmail(loginValue) ?: getUserByUsername(loginValue)
		return if (user != null) user.password == enteredPassword else false
	}

	override suspend fun doesEmailBelongToUserId(email: String, userId: String): Boolean {
		return usersDb.findOneById(userId)?.email == email
	}

	override suspend fun searchForUsers(userSearch: String, page: Int, pageSize: Int): List<User> {
		return usersDb.find(
			filter = or(
				User::userName regex Regex(pattern = ".*$userSearch.*", option = RegexOption.IGNORE_CASE),
				User::email eq userSearch
			)
		).skip(page * pageSize).limit(pageSize).descendingSort(User::email).toList()
	}
}