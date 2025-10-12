import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    kotlin("jvm")
    id("com.gradleup.shadow") version "9.2.2"
    id("de.eldoria.plugin-yml.paper") version "0.8.0"
}

group = "net.cafestube"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    paperLibrary(kotlin("stdlib"))
    implementation(project(":"))
}

paper {
    name = "Wiretapper"

    main = "net.cafestube.wiretapper.plugin.WireTapperPlugin"
    loader = "net.cafestube.wiretapper.plugin.WireTapperPluginLoader"

    generateLibrariesJson = true
    foliaSupported = true
    apiVersion = "1.21.10"

    authors = listOf("CafeStube")
}

tasks {
    generatePaperPluginDescription {
        addMavenCentralProxy("google", "https://maven-central.storage-download.googleapis.com/maven2")
    }
    build {
        dependsOn(shadowJar)
    }
    test {
        useJUnitPlatform()
    }
}
