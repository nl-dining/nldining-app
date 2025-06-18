plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.nldining.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nldining.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "1.1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    dependencies {
        implementation(libs.androidx.appcompat)
        implementation(libs.google.material)
        implementation(libs.material3)
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
        implementation("com.google.firebase:firebase-auth")
        implementation("com.google.firebase:firebase-analytics")
        implementation ("androidx.fragment:fragment-ktx:1.5.5") // of de laatste versie
        // Google Maps dependencies
        implementation ("com.google.android.gms:play-services-location:21.0.1")// indien locatiegebruik
// Retrofit core library
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
// Converter voor JSON (Gson)
        implementation ("com.google.maps.android:maps-compose:2.11.4")
        implementation ("com.google.android.gms:play-services-maps:18.2.0")
// Optioneel: OkHttp logging-interceptor voor het debuggen van netwerkverkeer
        implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
    }
}