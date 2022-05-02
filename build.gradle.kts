@file:Suppress("UnstableApiUsage")

plugins {
    id("org.jetbrains.dokka") version "1.4.10.2"
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://dl.bintray.com/kotlin/kotlinx")
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(projectDir.resolve("docs").resolve(project.version.toString()))
}

tasks {

    val docsDir = projectDir.resolve("docs")

    val renameModulesToIndex by registering {
        doLast {
            logger.lifecycle("---=== Renaming -modules.html to index.html ===---")
            docsDir.listFiles()
                ?.filter { it.isDirectory }
                ?.forEach { dir ->
                    dir.listFiles()
                        ?.find { file -> file.name == "-modules.html" }
                        ?.renameTo(File(dir, "index.html"))
                }
        }
    }

    val generateDocsIndexTask by registering {
        doLast {
            logger.lifecycle("---=== Generating index.html for docs ===---")

            val docsSubDirs = docsDir.listFiles()
                ?.filter { it.isDirectory }
                ?.joinToString("\n") { dir -> "<li><a href=\"${dir.name}\">${dir.name}</a></li>" }

            val html = """|<!doctype html><head><title>Choose a Version</title></head><body>
                |<h1>Pick a Version</h1><ul>
                |$docsSubDirs
                |</ul></body></html>""".trimMargin()

            docsDir.resolve("index.html").writeText(html)
        }
    }

    dokkaHtmlMultiModule {
        outputDirectory.set(docsDir.resolve(project.version.toString()))
        finalizedBy(renameModulesToIndex, generateDocsIndexTask)
    }
}
