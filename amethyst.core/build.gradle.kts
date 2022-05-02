@file:Suppress("UnstableApiUsage")

import Libraries.kotlinReflect
import Libraries.kotlinTestAnnotationsCommon
import Libraries.kotlinTestCommon
import Libraries.kotlinTestJs
import Libraries.kotlinTestJunit
import Libraries.kotlinxCollectionsImmutable
import Libraries.kotlinxCoroutines
import Libraries.cobaltCore

import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
}


kotlin {

    jvm {
        withJava()
        compilations.all {
            kotlinOptions {
                apiVersion = "1.5"
                languageVersion = "1.5"
            }
        }
    }

    js(BOTH) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        nodejs()
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                api(kotlinxCoroutines)
                api(kotlinReflect)
                api(kotlinxCollectionsImmutable)

                api(cobaltCore)
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.1.1")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlinTestCommon)
                implementation(kotlinTestAnnotationsCommon)
            }
        }
        val jvmMain by getting {}
        val jvmTest by getting {
            dependencies {
                implementation(kotlinTestJunit)
            }
        }
        val jsMain by getting {}
        val jsTest by getting {
            dependencies {
                implementation(kotlinTestJs)
            }
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

tasks {
    withType<DokkaTask>().configureEach {
        dokkaSourceSets {
            configureEach {
                includes.from("module.md", "packages.md")
                samples.from("src/commonMain/kotlin/org/hexworks/amethyst/samples")

                sourceLink {
                    localDirectory.set(file("src/commonMain/kotlin"))
                    remoteUrl.set(URL("https://github.com/Hexworks/amethyst/tree/master/amethyst.core/src/commonMain/kotlin"))
                }

                sourceLink {
                    localDirectory.set(file("src/jvmMain/kotlin"))
                    remoteUrl.set(URL("https://github.com/Hexworks/amethyst/tree/master/amethyst.core/src/jvmMain/kotlin"))
                }
                jdkVersion.set(8)
            }
        }
    }
}
