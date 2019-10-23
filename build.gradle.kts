allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        jcenter()
        kotlinx()
        jitpack()
    }
}

subprojects {
    apply<MavenPublishPlugin>()
}