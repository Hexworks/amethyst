plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
}

kotlin {

    jvm {
        jvmTarget(JavaVersion.VERSION_1_8)
        withJava()
    }

//    js {
//        browser {
//            testTask {
//                useKarma {
//                    useChrome()
//                }
//            }
//        }
//    }

    dependencies {

        with(Libs) {
            commonMainApi(kotlinStdLibCommon)
            commonMainApi(kotlinReflect)

            commonMainApi(kotlinxCoroutinesCommon)
            commonMainApi(kotlinxCollectionsImmutable)

            commonMainApi(cobaltCore)

            jvmMainApi(kotlinStdLibJdk8)
            jvmMainApi(kotlinxCoroutines)
            jvmMainApi(slf4jApi)
            jvmMainApi(logbackClassic)

//            jsMainApi(kotlinStdLibJs)
//            jsMainApi(kotlinxCoroutinesJs)
        }

        with(TestLibs) {
            commonTestApi(kotlinTestCommon)
            commonTestApi(kotlinTestAnnotationsCommon)
            commonTestApi(kotlinxCoroutinesTest)

            jvmTestApi(kotlinTestJunit)

//            jsTestApi(kotlinTestJs)
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