import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val arrowVersion = "0.10.1-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.3.50"
}


repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://dl.bintray.com/arrow-kt/arrow-kt/")
    maven(url = "https://oss.jfrog.org/artifactory/oss-snapshot-local/") // for SNAPSHOT builds
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("io.arrow-kt:arrow-syntax:$arrowVersion")
    implementation("com.beust:klaxon:5.0.1")
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    testImplementation("ch.qos.logback:logback-classic:1.2.3")
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    // Customise the “compileKotlin” task.
    kotlinOptions.jvmTarget = "11"
}
val compileTestKotlin by tasks.getting(KotlinCompile::class) {
    // Customise the “compileTestKotlin” task.
    kotlinOptions.jvmTarget = "11"
}

tasks {
    named<Test>("test") {
        useJUnitPlatform()
    }
}
