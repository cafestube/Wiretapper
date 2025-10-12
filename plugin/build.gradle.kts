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
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    paperLibrary(kotlin("stdlib"))
    implementation(project(":"))
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
}

paper {
    name = "Wiretapper"

    main = "net.cafestube.wiretapper.plugin.WiretapperPlugin"
    loader = "net.cafestube.wiretapper.plugin.WiretapperPluginLoader"

    generateLibrariesJson = true
    foliaSupported = true
    apiVersion = "1.21.10"

    authors = listOf("CafeStube")
}

tasks {
    generatePaperPluginDescription {
        addMavenCentralProxy("google", "https://maven-central.storage-download.googleapis.com/maven2")
    }
    shadowJar {
        archiveBaseName.set("Wiretapper")
    }
    build {
        dependsOn(shadowJar)
    }
    test {
        useJUnitPlatform()
    }
}
