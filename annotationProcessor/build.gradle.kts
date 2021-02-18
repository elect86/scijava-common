//import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
}

dependencies {
    implementation(project(":annotation"))
}

//repositories {
//    mavenCentral()
//}
//
//tasks.withType<KotlinCompile> {
//    kotlinOptions.jvmTarget = "1.8"
//}