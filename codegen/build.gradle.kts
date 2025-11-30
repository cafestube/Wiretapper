import org.jetbrains.kotlin.gradle.utils.extendsFrom

plugins {
    id("java")
    id("io.papermc.paperweight.userdev")
}

group = "net.cafestube"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.21.10-R0.1-SNAPSHOT")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("io.github.classgraph:classgraph:4.8.158")


}

configurations.runtimeClasspath.extendsFrom(configurations.mojangMappedServerRuntime)

tasks.test {
    useJUnitPlatform()
}