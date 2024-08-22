plugins {
    kotlin("jvm") version "2.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("com.squareup.okhttp3:okhttp:4.12.0")
}

testing {

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}