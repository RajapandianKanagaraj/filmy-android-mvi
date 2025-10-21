import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.android.filmy"
    compileSdk = 36
    buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "com.android.filmy"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        val tmdbApiKey: String = getLocalProperty("TMDB_API_KEY")
        val baseUrl = getLocalProperty("BASE_URL")
        val imageBaseUrl = getLocalProperty("BASE_IMAGE_URL")
        val amplitudeApiKey = getLocalProperty("AMPLITUDE_API_KEY")
        val segmentWriteKey = getLocalProperty("SEGMENT_WRITE_KEY")

        buildConfigField("String", "TMDB_API_KEY", tmdbApiKey)
        buildConfigField("String", name = "BASE_URL", value = baseUrl)
        buildConfigField("String", name = "BASE_IMAGE_URL", value = imageBaseUrl)
        buildConfigField("String", name = "AMPLITUDE_API_KEY", value = amplitudeApiKey)
        buildConfigField("String", name = "SEGMENT_WRITE_KEY", value = segmentWriteKey)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

private fun getLocalProperty(key: String): String {
    return gradleLocalProperties(
        rootDir,
        providers = providers
    ).getProperty(key)
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.material.icons.extended)

    // Dagger - Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.compose.navigation)

    // Retrofit - OkHttp - Moshi
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.okhttp)
    implementation(libs.moshi)
    ksp(libs.moshi.codegen)

    //Coil
    implementation(libs.coil)
    implementation(libs.coil.network)

    //Navigation
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Amplitude Analytics + Session Replay
    implementation(libs.amplitude)
    implementation(libs.amplitude.session.replay)

    //Segment Analytics
    implementation("com.segment.analytics.kotlin:android:1.21.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
