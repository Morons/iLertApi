package za.co.ilert.plugins

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import za.co.ilert.core.di.mainModule

fun Application.configureKoin() {
	// Install Ktor features
	install(Koin) {
		modules(mainModule)
	}
}