plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.luckylotto"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.luckylotto"
        minSdk = 34 // 24 - 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.google.googleid)
    //implementation(libs.compose.preview.renderer)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Splash API
    implementation(libs.androidx.core.splashscreen.v110alpha02)

    // Shapes
    implementation(libs.androidx.graphics.shapes)

    // Navigation Jetpack compose
    implementation(libs.androidx.navigation.compose)

    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))

    // When using the BoM, you don't specify versions in Firebase library dependencies

    // Add the dependency for the Firebase SDK for Google Analytics
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-analytics-ktx")
    // TODO: Add the dependencies for any other Firebase products you want to use
    // See https://firebase.google.com/docs/android/setup#available-libraries
    // For example, add the dependencies for Firebase Authentication and Cloud Firestore
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-auth-ktx")
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation(libs.play.services.auth)
    // One Tap Client By Stevdza-san
    implementation(libs.onetapcompose)

    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.animated.navigation.bar)
    // Coil
    implementation(libs.coil.compose)
    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose.v284)

}