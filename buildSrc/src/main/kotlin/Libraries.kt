import Versions.cobaltVersion
import Versions.kotlinxCollectionsImmutableVersion
import Versions.kotlinxCoroutinesVersion
import Versions.logbackVersion
import Versions.slf4jVersion

object Libraries {

    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"
    const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion"
    const val kotlinxCollectionsImmutable =
        "org.jetbrains.kotlinx:kotlinx-collections-immutable:$kotlinxCollectionsImmutableVersion"

    const val cobaltCore = "org.hexworks.cobalt:cobalt.core:$cobaltVersion"

    const val slf4jApi = "org.slf4j:slf4j-api:$slf4jVersion"
    const val logbackClassic = "ch.qos.logback:logback-classic:$logbackVersion"

    // TEST
    const val kotlinTestCommon = "org.jetbrains.kotlin:kotlin-test-common"
    const val kotlinTestAnnotationsCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common"
    const val kotlinTestJunit = "org.jetbrains.kotlin:kotlin-test-junit"
    const val kotlinTestJs = "org.jetbrains.kotlin:kotlin-test-js"
}
