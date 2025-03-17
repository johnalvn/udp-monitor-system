plugins {
    kotlin("jvm") version "2.1.10"
}

group = "co.jedal.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("com.rabbitmq:amqp-client:5.20.0")
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.8.9")


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}