plugins {
    kotlin("jvm") version "2.1.10"
    application
    id("org.gradle.java-library")
}

group = "co.jedal.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.rabbitmq:amqp-client:5.20.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.yaml:snakeyaml:2.0")
    // https://mvnrepository.com/artifact/io.mockk/mockk
    testImplementation("io.mockk:mockk:1.13.10")
    testImplementation("org.mockito:mockito-core:5.14.2")
    testImplementation("org.mockito:mockito-junit-jupiter:5.14.2")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("co.jedal.test.MainKt")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "co.jedal.test.MainKt"
    }
    // Include runtime dependencies in JAR
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}