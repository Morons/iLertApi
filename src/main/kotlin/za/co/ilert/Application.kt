package za.co.ilert

import io.ktor.server.application.*
import za.co.ilert.plugins.*

fun main(args: Array<String>): Unit =
	io.ktor.server.tomcat.EngineMain.main(args)

// application.conf references the main function. This annotation prevents the IDE from marking it as unused.
@Suppress("unused")
fun Application.module() {
	configureKoin()
	configureSecurity()
	configureSession()
	configureHTTP()
	httpsRedirect()
	defaultHeaders()
	configureMonitoring()
	configureSerialization()
	configureSockets()
	configureRouting()
}
