plugins {
    id("java")
    // Java support
    // Gradle Changelog Plugin
    id("org.jetbrains.changelog") version "2.2.0"
    // Gradle Qodana Plugin
    id("org.jetbrains.qodana") version "0.1.13"
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
    id("org.jetbrains.intellij") version "1.17.2"
}

fun properties(key: String) = project.findProperty(key).toString()

group = "ru.azharkova"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    //version.set("2023.2.5")
    type.set("AI") // Target IDE Platform
    version.set("2023.1.1.28")

    plugins.set(properties("platformPlugins").split(',').map(String::trim).filter(String::isNotEmpty))

}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("231")
        untilBuild.set("242.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
