val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project
val kmongo_version: String by project
val kotlin_css_version: String by project
val jquery_version: String by project

plugins {
	application
	kotlin("jvm") version "1.7.20"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
	id("war")
}

group = "za.co.ilert"
//version = "0.0.1"
application {
	mainClass.set("io.ktor.server.tomcat.EngineMain")

	val isDevelopment: Boolean = project.ext.has("development")
	applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

tasks.war { archiveFileName.set("ilert.war") }

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
	kotlinOptions {
		jvmTarget = "11"
		freeCompilerArgs = listOf(
//			"-Xuse-k2",
//			"-Xjdk-release-11",
			"-Xcontext-receivers",
			"-Xbackend-threads=4"
		)
	}
}


repositories {
	mavenCentral()
}

dependencies {
	implementation("ch.qos.logback:logback-classic:$logback_version")

	implementation("io.ktor:ktor-serialization-gson-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-cors-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-default-headers-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-http-redirect-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-sessions-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-tomcat-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-webjars-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-websockets-jvm:$ktor_version")

	implementation("org.webjars:jquery:$jquery_version")

	// KMongo
	implementation("org.litote.kmongo:kmongo:$kmongo_version")
	implementation("org.litote.kmongo:kmongo-coroutine:$kmongo_version")

	// Koin core features
	implementation("io.insert-koin:koin-core:$koin_version")
	implementation("io.insert-koin:koin-ktor:$koin_version")
	implementation("io.insert-koin:koin-logger-slf4j:$koin_version")

	// Test dependencies

	// Gson
	testImplementation("com.google.code.gson:gson:2.10")
	// Koin
	testImplementation("io.insert-koin:koin-test:$koin_version")
	// Ktor Test
	testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
	// Kotlin Test
	testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
	// Truth
	testImplementation("com.google.truth:truth:1.1.3")

}