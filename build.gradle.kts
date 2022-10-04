val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val koinVersion: String by project
val kmongoVersion: String by project
val kotlinCssVersion: String by project
val jqueryVersion: String by project

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
	implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-sessions-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-host-common-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-cors-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-default-headers-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-http-redirect-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
	implementation("io.ktor:ktor-serialization-gson-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-websockets-jvm:$ktorVersion")
	implementation("io.ktor:ktor-server-tomcat-jvm:$ktorVersion")

	implementation("ch.qos.logback:logback-classic:$logbackVersion")

	implementation("org.webjars:jquery:$jqueryVersion")

	// KMongo
	implementation("org.litote.kmongo:kmongo:$kmongoVersion")
	implementation("org.litote.kmongo:kmongo-coroutine:$kmongoVersion")

	// Koin core features
	implementation("io.insert-koin:koin-core:$koinVersion")
	implementation("io.insert-koin:koin-ktor:$koinVersion")
	implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

	// Test dependencies
	testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}