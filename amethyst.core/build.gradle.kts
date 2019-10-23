plugins {
    kotlinMpp
}

kotlin {
    jvm {
        jvmTarget(JavaVersion.VERSION_1_8)
        withJava()
    }

    dependencies {
        with (Libs) {
            commonMainApi(kotlinStdLibCommon)
            commonMainApi(cobaltEvents)
            commonMainApi(cobaltDatabinding)
            commonMainApi(cobaltDatatypes)
            commonMainApi(cobaltLogging)
        }

        with (LibsTest) {
            commonTestImplementation(kotlinTestCommon)
            commonTestImplementation(kotlinTestAnnotationsCommon)
        }

        with (Libs) {
            jvmMainApi(kotlinStdLibJdk8)
            jvmMainApi(kotlinReflect)
        }

        with (LibsTest) {
            jvmTestApi(kotlinTest)
            jvmTestApi(kotlinTestJUnit)
            jvmTestApi(junit)
            jvmTestApi(assertJCore)
        }
    }
}