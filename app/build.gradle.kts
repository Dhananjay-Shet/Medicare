plugins {
    alias(
        libs.plugins.android.application
    )
    alias(
        libs.plugins.kotlin.android
    )
    alias(
        libs.plugins.kotlin.compose
    )

    kotlin("plugin.serialization") version "1.9.0"

    id("com.google.gms.google-services")
}

android {
    namespace =
        "com.medicare"
    compileSdk =
        35

    defaultConfig {
        applicationId =
            "com.medicare"
        minSdk =
            26
        targetSdk =
            35
        versionCode =
            1
        versionName =
            "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled =
                false
            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility =
            JavaVersion.VERSION_11
        targetCompatibility =
            JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget =
            "11"
    }
    buildFeatures {
        compose =
            true
    }
}

dependencies {

    implementation(
        libs.androidx.core.ktx
    )
    implementation(
        libs.androidx.lifecycle.runtime.ktx
    )
    implementation(
        libs.androidx.activity.compose
    )
    implementation(
        platform(
            libs.androidx.compose.bom
        )
    )
    implementation(
        libs.androidx.ui
    )
    implementation(
        libs.androidx.ui.graphics
    )
    implementation(
        libs.androidx.ui.tooling.preview
    )
    implementation(
        libs.androidx.material3
    )
    testImplementation(
        libs.junit
    )
    androidTestImplementation(
        libs.androidx.junit
    )
    androidTestImplementation(
        libs.androidx.espresso.core
    )
    androidTestImplementation(
        platform(
            libs.androidx.compose.bom
        )
    )
    androidTestImplementation(
        libs.androidx.ui.test.junit4
    )
    debugImplementation(
        libs.androidx.ui.tooling
    )
    debugImplementation(
        libs.androidx.ui.test.manifest
    )

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.0")

    implementation("androidx.compose.material3:material3:1.3.1")
    //implementation("androidx.compose.material3:material3-window-size-class:1.3.1")
    //implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.4.0-alpha05")

    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.8")

    implementation("androidx.navigation:navigation-compose:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation("io.coil-kt:coil-compose:2.1.0")

    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.firebaseui:firebase-ui-auth:7.2.0")
    implementation("com.google.firebase:firebase-database")

    implementation("androidx.datastore:datastore-preferences:1.1.2")
}