plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.kotlinx.serialization)
}

group = "com.arnyminerz.ggs"
version = "1.0.0-ALPHA"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)

    // SQLite Driver for JDBC
    implementation(libs.jdbc.sqlite)

    // Serialization
    implementation(libs.serialization.csv)
    implementation(libs.serialization.json)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
