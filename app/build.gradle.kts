
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.appbiblioteca"
    compileSdk = 35


    defaultConfig {
        applicationId = "com.example.appbiblioteca"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    buildFeatures{

        viewBinding = true

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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.baselibrary)
    implementation(libs.androidx.coordinatorlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    implementation (libs.androidx.material.icons.extended)
    implementation (libs.logging.interceptor)
    implementation (libs.androidx.navigation.compose)
    implementation (libs.jwtdecode)



    //3d Recyclerview

    //Glide
    implementation (libs.glide.v4130)
    annotationProcessor (libs.compiler)

    // Lifecycle
    implementation (libs.androidx.lifecycle.extensions)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.kotlin.reflect)

    implementation (libs.androidx.cardview)
    implementation (libs.github.carouselrecyclerview)

    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)
    implementation (libs.gson)
    implementation(libs.kotlinx.serialization.json)

    implementation (libs.okhttp)


    implementation (libs.jetbrains.kotlin.stdlib)

    implementation (libs.androidx.recyclerview)

    implementation (libs.circleimageview)
    implementation (libs.input)





}