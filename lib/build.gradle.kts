plugins {
    kotlin("jvm") version "1.9.25"
    `java-library`
}

group = "rohde.jonathan.threadpoolexample"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.springframework:spring-webmvc:6.2.8")
    api("io.github.microutils:kotlin-logging-jvm:3.0.5")
    api("com.sun.xml.bind:jaxb-impl:4.0.5")
    testImplementation(kotlin("test"))
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
