@file:Suppress("UnstableApiUsage")

import Libs.cobaltCore
import Libs.kotlinxCollectionsImmutable
import Libs.kotlinxCoroutines
import TestLibs.kotlinTestAnnotationsCommon
import TestLibs.kotlinTestCommon
import TestLibs.kotlinxCoroutinesTest
import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
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
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlinTestCommon)
                implementation(kotlinTestAnnotationsCommon)
            }
        }
        val jvmMain by getting {
            dependencies {
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting {
            dependencies {
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-js"))
            }
        }
    }
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets {
        configureEach {
            includeNonPublic.set(false)
            skipDeprecated.set(false)
            skipEmptyPackages.set(true)
            includes.from("module.md", "packages.md")

            samples.from("src/commonMain/kotlin/org/hexworks/amethyst/samples")

            sourceLink {
                localDirectory.set(file("src/commonMain/kotlin"))
                remoteUrl.set(URL("https://github.com/Hexworks/amethyst/tree/master/amethyst.core/src/commonMain/kotlin"))
                remoteLineSuffix.set("#L")
            }
            sourceLink {
                localDirectory.set(file("src/jvmMain/kotlin"))
                remoteUrl.set(URL("https://github.com/Hexworks/amethyst/tree/master/amethyst.core/src/jvmMain/kotlin"))
                remoteLineSuffix.set("#L")
            }
            jdkVersion.set(8)
            noStdlibLink.set(false)
            noJdkLink.set(false)
            noAndroidSdkLink.set(false)
        }
    }
}

publishing {
    publishWith(
            project = project,
            module = "amethyst.core",
            desc = "Core component of Amethyst."
    )
}

signing {
    isRequired = false
    sign(publishing.publications)
}
