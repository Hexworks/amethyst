import Libs.cobaltCore
import Libs.kotlinxCollectionsImmutable
import Libs.kotlinxCoroutines

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")")
}

kotlin {

    jvm {
        withJava()
        jvmTarget(JavaVersion.VERSION_1_8)
    }

    js {
        browser {
            testTask { enabled = false }
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(kotlin("reflect"))

                api(kotlinxCoroutines)
                api(kotlinxCollectionsImmutable)

                api(cobaltCore)
            }
        }
    }

    dependencies {

        with(Libs) {
            jvmMainApi(kotlinxCoroutines)
            jvmMainApi(slf4jApi)
            jvmMainApi(logbackClassic)

            jsMainApi(kotlinStdLibJs)
            jsMainApi(kotlinxCoroutinesJs)
        }

        with(TestLibs) {
            commonTestApi(kotlinTestCommon)
            commonTestApi(kotlinTestAnnotationsCommon)
            commonTestApi(kotlinxCoroutinesTest)

            jvmTestApi(kotlinTestJunit)

            jsTestApi(kotlinTestJs)
        }
    }
}

publishing {
    publishWith(
            project = project,
            module = "amethyst.core",
            desc = "Core package of Amethyst."
    )
}

signing {
    isRequired = false
    sign(publishing.publications)
}