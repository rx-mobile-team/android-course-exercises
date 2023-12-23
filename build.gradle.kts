import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "com.rxmobileteam"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains:annotations:24.0.1")
    implementation("io.reactivex.rxjava3:rxjava:3.1.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_13.toString()
}

java {
    sourceCompatibility = JavaVersion.VERSION_13
    targetCompatibility = JavaVersion.VERSION_13
}

application {
    mainClass.set("com.rxmobileteam.lecture1.Exercise1Main")
}
