# Kotlin / Gradle DSL integration to manage dependency version variables

My question is about the best way to manage dependency version variables in IntelliJ Idea / Android Studio using Gradle
Kotlin DSL

The secondary purpose is to have the IDE perform version upgrades upon clicking the suggestion to change to newer
version automatically instead of manually editing the versioning file entry.

### the simple way

### definition

**build.gradle.kts**

```kotlin

plugins {
	application
	kotlin("jvm") version "1.7.10"
}
dependencies {
	implementation("io.ktor:ktor-server-core-jvm:2.1.1")
}

```

### Comments

This is working but "clumsy" and does not comply to single source of truth (SSOT)
I have seen several ways that attempt having a SSOT for the dependency versions

## by Project

### Definition

**gradle.properties**

```kotlin

ktor_version = 2.1.0
kotlin_version = 1.7.10

```

**build.gradle.kts**

```kotlin

val ktor_version: String by project // NOTE the complaint Property name 'ktor_version' should not contain underscores 
val kotlin_version: String by project

plugins {
	application
	kotlin("jvm") version "1.7.10" // NOTE you cannot replace this with the variable!
	// 'val kotlin_version: String' can't be called in this context by implicit receiver. Use the explicit one if necessary
}
dependencies {
	implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
}

```

### Comments

This does not update the suggested version numbers via "Project Structure/Suggestions" in AS/IJ
my inline NOTES indicate several issues with this method!

## by buildSrc java Module

### Definition

````kotlin

object Ktor {
	private const val ktorVersion = "2.0.0"
	const val core = "io.ktor:ktor-client-core:${ktorVersion}"
	const val android = "io.ktor:ktor-client-android:${ktorVersion}"
}

````

### implementation in build.gradle.kts

```kotlin

implementation(Ktor.android)

```

### Comments

This does not update the suggested version numbers via "Project Structure/Suggestions" in AS/IJ, is seemed to
be totally de-coupled, and it is a nightmare to manually find and update the versions manually to the latest!

**However:** doing it by buildSrc java Module makes it much easier to re-use in other projects

## by Gradle Version Catalogs

### Definition

**libs.versions.toml** // in the gradle directory next to wrapper

```toml

[versions]
plugin-kotlin = "1.7.10"
ktor = "2.1.0"

[libraries]
plugin-kotlin = { module = "org.jetbrains.kotlin:kotlin-gradle-plugin", version.ref = "plugin-kotlin" }
ktor-client-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor" }

[bundles]
plugins = ["plugin-android", "plugin-kotlin", "plugin-kotlin-serialization", "plugin-sqldelight"]

```

### implementation in build.gradle.kts(project)

````kotlin

buildscript {
	repositories {
		gradlePluginPortal()
		google()
		mavenCentral()
	}
	dependencies {
		classpath(libs.bundles.plugins)
	}
}

````

### implementation in build.gradle.kts(shared)

````kotlin

sourceSets {
	val commonMain by getting {
		dependencies {
			implementation(libs.ktor.client.core)
		}
	}
}

````

### Comments

This is by far the favorite of mine, the reason is clear, it allows SSOT, and it allows grouping/bundling to make
"sets" that is more understandable later on

**However!**
This does not update the suggested version numbers via "Project Structure/Suggestions" in AS/IJ
it is a nightmare to manually find and update the versions manually to the latest!

# Main question

1. Is there a better way of managing dependency versions?
2. Is there a way that works inside the IDE AS/IJ suggestions to update the versions correctly at the definition
   location?