plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Kotlin 2.0 이상에서 Compose 사용할 때 필수
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
}

android {
    namespace = "com.example.test01"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.test01"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true
    }

    // 🔹 자바와 코틀린 모두 JVM 17로 맞추기
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    // 🔹 Kotlin Toolchain (JVM 17 명시)
    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        compose = true
    }

    // Compose Compiler는 plugin이 관리하므로 composeOptions 불필요
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2024.10.00"))

    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation("androidx.navigation:navigation-compose:2.8.3")
    implementation("androidx.compose.material:material-icons-extended")
}
