From a previously running flawless app
After automatic migration several things was changed
# Gradle
IJ and your samples uses gradle.properties to define version and thn in the gradle you have the syntax of using 
variables.
### Before Migration
```kotlin
implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
```
### After migration
Instead of changing the variable in gradle.properties, this change is inconsistent and makes a mess for the programmer!!
```kotlin
implementation("io.ktor:ktor-server-core-jvm:2.2.1")
```
# Code
### Plugin Failures
Theses were mostly Import changes not happened! **e.g.** in `...plugins\Serialization.kt`
### supposed to be after migration
```kotlin
import io.ktor.serialization.gson.*
```
### was not migrated!
```kotlin
import io.ktor.serialization.kotlinx.json.gson.*
```
```
e: [...snipped...]\plugins\Serialization.kt: (3, 30): Unresolved reference: kotlinx 
e: [...snipped...]\plugins\Serialization.kt: (9, 3): Unresolved reference: gson 
e: [...snipped...]\plugins\Serialization.kt: (10, 4): Unresolved reference: serializeSpecialFloatingPointValues 
e: [...snipped...]\plugins\Sockets.kt: (3, 30): Unresolved reference: kotlinx 
e: [...snipped...]\plugins\Sockets.kt: (14, 22): Unresolved reference: GsonWebsocketContentConverter 
```
### below seem to be IntelliJ
IJ is not re-analyzing or something the code after the migration, therefore IJ show some errors, by simply 
reloading the project the fake errors was removed and the file stayed unchanged!
**maybe** force IJ to re-analyze after the whole migration went through!
```
e: [...snipped...] \presentation\routes\chat\WebSocketRoutes. kt: (28, 11): Unresolved reference: Frame
e: [...snipped...] \presentation\routes\chat\WebSocketRoutes. kt: (29, 31): Unresolved reference. None of the following candidates is applicable because of receiver type mismatch:
    public fun File.readText(charset: Charset = ...): String defined in kotlin.io
    public fun Reader.readText(): String defined in kotlin.io
    public inline fun URL.readText(charset: Charset = ...): String defined in kotlin.io
e: [...snipped...] \presentation\routes\chat\WebSocketRoutes. kt: (78, 8): Unresolved reference: Frame
e: [...snipped...] \presentation\routes\chat\WebSocketRoutes. kt: (79, 23): Unresolved reference. None of the following candidates is applicable because of receiver type mismatch:
    public fun File.readText(charset: Charset = ...): String defined in kotlin.io
    public fun Reader.readText(): String defined in kotlin.io
    public inline fun URL.readText(charset: Charset = ...): String defined in kotlin.io
e: [...snipped...] \presentation\routes\chat\WebSocketRoutes. kt: (80, 20): Unresolved reference: Frame
e: [...snipped...] \presentation\routes\chat\WebSocketRoutes. kt: (82, 7): Unresolved reference: close
e: [...snipped...] \presentation\routes\chat\WebSocketRoutes. kt: (82, 13): Unresolved reference: CloseReason
e: [...snipped...] \presentation\routes\chat\WebSocketRoutes. kt: (82, 25): Unresolved reference: CloseReason
e: [...snipped...] \presentation\services\chat\ChatController. kt: (17, 54): Unresolved reference: WebSocketSession
e: [...snipped...] \presentation\services\chat\ChatController. kt: (19, 40): Unresolved reference: WebSocketSession
e: [...snipped...] \presentation\services\chat\ChatController. kt: (38, 32): Unresolved reference: Frame
e: [...snipped...] \presentation\services\chat\ChatController. kt: (39, 35): Unresolved reference: Frame
```