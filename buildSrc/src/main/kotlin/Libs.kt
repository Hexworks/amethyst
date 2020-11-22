import Versions.cobaltVersion
import Versions.kotlinxCollectionsImmutableVersion
import Versions.kotlinxCoroutinesVersion
import Versions.logbackVersion
import Versions.slf4jVersion

object Libs {

    const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion"
    const val kotlinxCollectionsImmutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable:$kotlinxCollectionsImmutableVersion"

    const val cobaltCore = "org.hexworks.cobalt:cobalt.core:$cobaltVersion"

    const val slf4jApi = "org.slf4j:slf4j-api:$slf4jVersion"
    const val logbackClassic = "ch.qos.logback:logback-classic:$logbackVersion"
}
