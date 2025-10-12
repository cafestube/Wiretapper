plugins {
    kotlin("jvm") version "2.1.20"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17"
}

group = "net.cafestube"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.21.10-R0.1-SNAPSHOT")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}