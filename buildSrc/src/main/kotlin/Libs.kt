import Versions.cobaltVersion
import Versions.kotlinVersion
import Versions.kotlinxCollectionsImmutableVersion
import Versions.kotlinxCoroutinesVersion
import Versions.logbackVersion
import Versions.slf4jVersion

object Libs {

    const val kotlinStdLibCommon = "org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion"
    const val kotlinStdLibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val kotlinStdLibJs = "org.jetbrains.kotlin:kotlin-stdlib-js"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"

    const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion"
    const val kotlinxCoroutinesCommon = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$kotlinxCoroutinesVersion"
    const val kotlinxCoroutinesJs = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$kotlinxCoroutinesVersion"
    const val kotlinxCollectionsImmutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable:$kotlinxCollectionsImmutableVersion"

    const val cobaltCore = "org.hexworks.cobalt:cobalt.core:$cobaltVersion"

    const val slf4jApi = "org.slf4j:slf4j-api:$slf4jVersion"
    const val logbackClassic = "ch.qos.logback:logback-classic:$logbackVersion"
}
