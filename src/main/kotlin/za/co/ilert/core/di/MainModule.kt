package za.co.ilert.core.di

import org.koin.dsl.module
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.coroutine.coroutine
import za.co.ilert.core.util.Constants.DATABASE_NAME

val mainModule = module {

	single {
		val client = KMongo.createClient().coroutine
		client.getDatabase(DATABASE_NAME)
	}


//	singleOf(::ChatRepositoryImpl) { bind<ChatRepository>() }

//	singleOf(::UserService)
}