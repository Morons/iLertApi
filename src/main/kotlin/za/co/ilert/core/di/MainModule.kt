package za.co.ilert.core.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.coroutine.coroutine
import za.co.ilert.core.data.repository.user.UserRepository
import za.co.ilert.core.data.repository.user.UserRepositoryImpl
import za.co.ilert.core.util.Constants.DATABASE_NAME
import za.co.ilert.presentation.services.UserService

val mainModule = module {

	single {
		val client = KMongo.createClient().coroutine
		client.getDatabase(DATABASE_NAME)
	}

//	single<UserRepository>{ UserRepositoryImpl() }
	singleOf(::UserRepositoryImpl) {bind<UserRepository>() }

	singleOf(::UserService)
}