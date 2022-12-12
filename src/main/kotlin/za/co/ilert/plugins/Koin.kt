package za.co.ilert.plugins

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger
import za.co.ilert.di.mainModule

fun Application.configureKoin() {
	// Install Ktor features
	install(Koin) {
		SLF4JLogger()
		modules(mainModule)
	}
}