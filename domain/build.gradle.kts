plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("com.google.devtools.ksp")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.paging.common)

    // For Hilt DI in UseCaseModule (optional)
    implementation(libs.hilt.core)
    implementation(libs.hilt.annotations)
    ksp(libs.hilt.compiler)
}