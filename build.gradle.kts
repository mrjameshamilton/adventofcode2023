plugins {
    kotlin("jvm") version "1.9.21"
}

group = "eu.jameshamilton"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.21")

}

tasks.test {
    useJUnitPlatform()
}