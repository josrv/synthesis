import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("edu.stanford.nlp", "stanford-corenlp", "3.9.2")
    implementation("edu.stanford.nlp", "stanford-corenlp", "3.9.2", classifier = "models")
    implementation("cc.mallet", "mallet", "2.0.8")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}