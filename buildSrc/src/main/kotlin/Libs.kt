object Versions {
    const val kotlinVersion = "1.3.50"

    const val cobaltVersion= "2019.1.0-PREVIEW"

    const val junitVersion = "4.12"
    const val assertjVersion = "3.6.2"
}

object Libs {
    const val kotlinStdLibCommon = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlinVersion}"
    const val kotlinStdLibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlinVersion}"

    const val cobaltEvents = "org.hexworks.cobalt:cobalt.events:${Versions.cobaltVersion}"
    const val cobaltDatabinding = "org.hexworks.cobalt:cobalt.databinding:${Versions.cobaltVersion}"
    const val cobaltLogging = "org.hexworks.cobalt:cobalt.logging:${Versions.cobaltVersion}"
    const val cobaltDatatypes = "org.hexworks.cobalt:cobalt.datatypes:${Versions.cobaltVersion}"
}

object LibsTest {
    const val kotlinTestCommon = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlinVersion}"
    const val kotlinTestAnnotationsCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlinVersion}"

    const val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlinVersion}"
    const val kotlinTestJUnit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlinVersion}"

    const val junit = "junit:junit:${Versions.junitVersion}"
    const val assertJCore = "org.assertj:assertj-core:${Versions.assertjVersion}"
}