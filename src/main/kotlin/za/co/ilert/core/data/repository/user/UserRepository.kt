package za.co.ilert.core.data.repository.user

import za.co.ilert.core.data.models.User
import za.co.ilert.core.data.requests.UpdateUserRequest

interface UserRepository {

	suspend fun createUser(user: User): Boolean

	suspend fun getUser(userId: String): User?

	suspend fun upsertUser(user: User): Boolean

	suspend fun updateUser(isOwnProfile: Boolean, updateUserRequest: UpdateUserRequest): Boolean

	suspend fun getUserById(userId: String): User?

	suspend fun getUserListById(userList: List<String>): List<User>

	suspend fun getUserByEmail(email: String): User?

	suspend fun getUserByUsername(userName: String): User?

	suspend fun doesLoginValueMatchForUser(loginValue: String, enteredPassword: String): Boolean

	suspend fun doesEmailBelongToUserId(email: String, userId: String): Boolean

	suspend fun searchForUsers(userQuery: String, page: Int, pageSize: Int): List<User>

}