plugins {
    kotlin("jvm")
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.symbol.processing.api)
    implementation(libs.kotlinpoet)  // For code generation
    implementation(libs.kotlinpoet.ksp)
    implementation(project(":tracking"))
    implementation(project(":tracking-annotation"))
}