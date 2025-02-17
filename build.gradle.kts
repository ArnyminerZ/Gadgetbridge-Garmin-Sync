plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.kotlinx.serialization)
}

group = "com.arnyminerz.ggs"
version = "1.0.0-alpha01"

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
    implementation(libs.serialization.xml)
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<org.gradle.jvm.tasks.Jar> {
    exclude("META-INF/BC2048KE.RSA", "META-INF/BC2048KE.SF", "META-INF/BC2048KE.DSA")
    exclude("META-INF/BC2048KE.RSA", "META-INF/BC2048KE.SF", "META-INF/BC2048KE.DSA")
}
