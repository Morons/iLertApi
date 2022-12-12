package za.co.ilert.di

import com.google.gson.Gson
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import za.co.ilert.core.data.repository.address.AddressRepository
import za.co.ilert.core.data.repository.address.AddressRepositoryImpl
import za.co.ilert.core.data.repository.blocktest.BlockTestRepository
import za.co.ilert.core.data.repository.blocktest.BlockTestRepositoryImpl
import za.co.ilert.core.data.repository.chat.ChatRepository
import za.co.ilert.core.data.repository.chat.ChatRepositoryImpl
import za.co.ilert.core.data.repository.meat.CarcassRepository
import za.co.ilert.core.data.repository.meat.CarcassRepositoryImpl
import za.co.ilert.core.data.repository.meat.CutRepository
import za.co.ilert.core.data.repository.meat.CutRepositoryImpl
import za.co.ilert.core.data.repository.organization.OrganizationRepository
import za.co.ilert.core.data.repository.organization.OrganizationRepositoryImpl
import za.co.ilert.core.data.repository.user.UserRepository
import za.co.ilert.core.data.repository.user.UserRepositoryImpl
import za.co.ilert.core.utils.Constants.DATABASE_NAME
import za.co.ilert.presentation.services.address.AddressService
import za.co.ilert.presentation.services.blocktest.BlockTestService
import za.co.ilert.presentation.services.chat.ChatController
import za.co.ilert.presentation.services.chat.ChatService
import za.co.ilert.presentation.services.meat.MeatService
import za.co.ilert.presentation.services.organization.OrganizationService
import za.co.ilert.presentation.services.user.UserService

val mainModule = module {

	single {
		val client = KMongo.createClient().coroutine
		client.getDatabase(DATABASE_NAME)
	}

//	single<BlockTestRepository>{ BlockTestRepositoryImpl(get()) }
	singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
	singleOf(::OrganizationRepositoryImpl) { bind<OrganizationRepository>() }
	singleOf(::AddressRepositoryImpl) { bind<AddressRepository>() }
	singleOf(::ChatRepositoryImpl) { bind<ChatRepository>() }
	singleOf(::BlockTestRepositoryImpl) { bind<BlockTestRepository>() }
	singleOf(::CarcassRepositoryImpl) { bind<CarcassRepository>() }
	singleOf(::CutRepositoryImpl) { bind<CutRepository>() }

//	single { BlockTestService(get()) }
	singleOf(::UserService)
	singleOf(::OrganizationService)
	singleOf(::AddressService)
	singleOf(::ChatService)
	singleOf(::BlockTestService)
	singleOf(::ChatController)
	singleOf(::MeatService)

//	singleOf(::Json)
	singleOf(::Gson)
}