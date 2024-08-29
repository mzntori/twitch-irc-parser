plugins {
    kotlin("jvm") version "2.0.0"
    id("org.jetbrains.dokka") version "1.9.20"
    `java-library`
    `maven-publish`
    signing
}

group = "org.example"
version = "1.0"

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.github.mzntori"
            artifactId = "twitch-irc-parse"
            version = "1.0.0"

            from(components["java"])

            pom {
                name = "Twitch IRC Parser"
                description = "Parser for Twitch IRC Messages"
                url = "https://github.com/mzntori/twitch-irc-parser"

                licenses {
                    license {
                        name = "The MIT License"
                        url = "https://opensource.org/license/mit"
                    }
                }
            }
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("com.squareup.okhttp3:okhttp:4.12.0")
}

kotlin {
    jvmToolchain(17)
}

java {
    withSourcesJar()
}

signing {
    sign(publishing.publications["maven"])
}

tasks {
    dokkaHtml
    jar {
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to project.name,
                    "Implementation-Version" to project.version
                )
            )
        }
    }
    test {
        useJUnitPlatform()
    }
}