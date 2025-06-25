plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.nldining.app"
    compileSdk = 35
    buildFeatures {
        viewBinding = true
    }
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
            isMinifyEnabled = true
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
        //noinspection UseTomlInstead
        implementation(platform("com.google.firebase:firebase-bom:33.15.0"))
        //noinspection UseTomlInstead
        implementation("com.google.firebase:firebase-auth")
        //noinspection UseTomlInstead
        implementation("com.google.firebase:firebase-analytics")
        //noinspection UseTomlInstead
        implementation ("androidx.fragment:fragment-ktx:1.8.8")
        //noinspection UseTomlInstead
        implementation ("com.google.android.gms:play-services-location:21.3.0")
        //noinspection UseTomlInstead
        implementation ("com.squareup.retrofit2:retrofit:3.0.0")
        //noinspection UseTomlInstead
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
        //noinspection UseTomlInstead
        implementation ("com.google.maps.android:maps-compose:4.3.3")
        //noinspection UseTomlInstead
        implementation ("com.google.android.gms:play-services-maps:19.2.0")
        //noinspection UseTomlInstead
        implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")
        //noinspection UseTomlInstead
        implementation("com.squareup.okhttp3:okhttp:4.12.0")
        implementation(libs.rootbeer.lib)
        testImplementation(libs.junit)
        testImplementation(libs.mockk)
        testImplementation(libs.kotlinx.coroutines.test)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
    }
}