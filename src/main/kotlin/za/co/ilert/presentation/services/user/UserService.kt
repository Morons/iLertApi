package za.co.ilert.presentation.services.user

import org.bson.types.ObjectId
import za.co.ilert.core.data.models.User
import za.co.ilert.core.data.models.UserSecurity
import za.co.ilert.core.data.repository.user.UserRepository
import za.co.ilert.core.data.requests.UserRequest
import za.co.ilert.core.data.responses.UserResponse
import za.co.ilert.core.data.responses.UserSearchResponse
import za.co.ilert.core.utils.Constants.DEFAULT_PAGE_SIZE
import za.co.ilert.core.utils.Constants.FILE_SOURCE
import za.co.ilert.core.utils.getByteArray
import za.co.ilert.presentation.validation.ValidationEvent

class UserService(
	private val userRepository: UserRepository
) {

	suspend fun doesUserWithEmailExist(email: String): Boolean {
		return userRepository.getUserByEmail(email) != null
	}

	suspend fun getUserByEmail(email: String): User? {
		return userRepository.getUserByEmail(email)
	}


	suspend fun getUser(userId: String): User? {
		return userRepository.getUser(userId)
	}

	private fun isValidPassword(enteredPassword: String, actualPassword: String?): Boolean {
		return enteredPassword == actualPassword
	}

	suspend fun doesLoginValueMatchForUser(
		loginValue: String,
		enteredPassword: String,
		userPassword: String?
	): Boolean {
		return if (isValidPassword(enteredPassword, userPassword)) {
			userRepository.doesLoginValueMatchForUser(loginValue, enteredPassword)
		} else false
	}

	suspend fun createUser(userRequest: UserRequest): Boolean {
		var userId = userRequest.userId
		return userRepository.createUser(
			with(userRequest) {
				if (userId.isEmpty()) userId = ObjectId().toString()
				User(
					email = email,
					userName = userName,
					password = password,
					mobileNumber = mobileNumber,
					avatarAsString = getByteArray(filePathName = "$FILE_SOURCE/ic_avatar_default.png"),
					security = UserSecurity(userId = userId, active = true, roles = "BLOCK MAN"),
					userId = userId,
					organizationId = organizationId
				)
			}
		)
	}

	suspend fun upsertUser(user: User): Boolean {
		return userRepository.upsertUser(
			with(user) {
				User(
					email = email,
					userName = userName,
					password = password,
					mobileNumber = mobileNumber,
					avatarAsString = avatarAsString,
					security = security,
					organizationId = organizationId
				)
			}
		)
	}

	suspend fun updateUser(userRequest: UserRequest): Boolean {
		return userRepository.updateUser(userRequest)
	}

	fun validateCreateAccountRequest(userRequest: UserRequest): ValidationEvent {
		return if (with(userRequest) { email.isBlank() || password.isBlank() || userName.isBlank() }) {
			ValidationEvent.ErrorFieldEmpty
		} else ValidationEvent.Success
	}

	suspend fun searchForUsers(
		userSearch: String, userId: String, page: Int, pageSize: Int = DEFAULT_PAGE_SIZE
	): List<UserSearchResponse> {
		val users = userRepository.searchForUsers(userSearch, page, pageSize)
		return users.map { user ->
			UserSearchResponse(
				userId = user.userId,
				userName = user.userName,
				avatarAsString = user.avatarAsString,
			)
		}.filter { it.userId != userId }
	}

	suspend fun getUserProfile(userId: String): UserResponse? {
		val user = userRepository.getUserById(userId = userId) ?: return null
		return with(user) {
			UserResponse(
				userId = userId,
				email = email,
				mobileNumber = mobileNumber,
				userName = userName,
				password = password,
				avatarAsString = avatarAsString,
				security = security,
				organizationId = organizationId
			)
		}
	}
}