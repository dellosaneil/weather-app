plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.thelazybattley.weather"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.thelazybattley.weather"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":feature:archive"))
    implementation(project(":feature:forecast"))
    implementation(project(":feature:current"))
    implementation(project(":feature:maps"))
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.hilt.android)
    implementation(libs.bundles.compose)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.timber)
    implementation(project(":feature:main"))
    kapt(libs.hiltCompiler)
    implementation(libs.ui.text.google.fonts)
    implementation(libs.navigation.compose)

    implementation(libs.glance.appwidget)
    implementation(libs.androidx.glance.material3)
    implementation(libs.gson)
    implementation(libs.androidx.work.runtime.ktx)

}
