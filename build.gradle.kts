plugins {
    kotlin("jvm") version "2.0.0"
    `java-library`
    id("maven-publish")
    id("signing")
}

group = "org.example"
version = "1.0"

repositories {
    mavenCentral()
}

publishing {
    publications.create<MavenPublication>("lib") {
        
    }
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("com.squareup.okhttp3:okhttp:4.12.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
