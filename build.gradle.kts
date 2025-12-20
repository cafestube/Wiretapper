plugins {
    kotlin("jvm") version "2.2.0"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17"
    id("maven-publish")
}

group = "net.cafestube"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(kotlin("stdlib"))
    paperweight.paperDevBundle("1.21.10-R0.1-SNAPSHOT")

    testImplementation(kotlin("test"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "$group"
            artifactId = "Wiretapper"
            version = "${project.version}"

            artifact(tasks["jar"])
            artifact(tasks["sourcesJar"])
        }
        repositories {
            maven {
                name = "cafestubeRepository"
                credentials(PasswordCredentials::class)
                url = uri("https://repo.cafestube.net/repository/maven-public-snapshots/")
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}