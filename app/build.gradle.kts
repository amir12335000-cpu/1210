plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val configuredServerUrl = (project.findProperty("serverBaseUrl") as String?) ?: System.getenv("SERVER_BASE_URL") ?: ""
val escapedServerUrl = configuredServerUrl.replace("\\", "\\\\").replace("\"", "\\\"")

android {
    namespace = "ir.phonelink"
    compileSdk = 35

    buildFeatures { buildConfig = true }

    defaultConfig {
        applicationId = "ir.phonelink"
        buildConfigField("String", "SERVER_BASE_URL", "\"$escapedServerUrl\"")
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    signingConfigs {
        create("release") {
            // These values come from environment variables (GitHub Secrets) or local properties
            val keystorePath = System.getenv("KEYSTORE_PATH") ?: "../alzahra-release.keystore"
            val storePassword = System.getenv("KEYSTORE_PASSWORD") ?: "AlzahraUni2026!"
            val keyAlias = System.getenv("KEY_ALIAS") ?: "alzahra"
            val keyPassword = System.getenv("KEY_PASSWORD") ?: "AlzahraUni2026!"

            storeFile = file(keystorePath)
            storePassword = storePassword
            this.keyAlias = keyAlias
            this.keyPassword = keyPassword
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            // debug still works for development
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("androidx.core:core:1.15.0")
}
