package za.co.ilert.core.di

import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import za.co.ilert.core.data.repository.chat.ChatRepository
import za.co.ilert.core.data.repository.chat.ChatRepositoryImpl
import za.co.ilert.core.data.repository.user.UserRepository
import za.co.ilert.core.data.repository.user.UserRepositoryImpl
import za.co.ilert.core.util.Constants.DATABASE_NAME
import za.co.ilert.presentation.services.UserService
import za.co.ilert.presentation.services.chat.ChatController
import za.co.ilert.presentation.services.chat.ChatService

val mainModule = module {

	single {
		val client = KMongo.createClient().coroutine
		client.getDatabase(DATABASE_NAME)
	}

//	single<ChatRepository>{ ChatRepositoryImpl() }
	singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
	singleOf(::ChatRepositoryImpl) { bind<ChatRepository>() }

	singleOf(::UserService)
	singleOf(::ChatService)
	singleOf(::ChatController)

	singleOf(::Json)
}