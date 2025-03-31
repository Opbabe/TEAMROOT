plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") version "4.4.2" apply false
}

android {
    namespace = "edu.sjsu.sase.android.roots"
    compileSdk = 35

    defaultConfig {
        applicationId = "edu.sjsu.sase.android.roots"
        minSdk = 24
        targetSdk = 35
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

    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.firebase.auth)
    implementation(libs.legacy.support.v4)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.android.gms:play-services-auth:21.3.0")


    //for credential management
    implementation("androidx.credentials:credentials:1.5.0-rc01")
    //optional - needed for credentials support from play services, for devices running
    //Android 13 and below.
    implementation("androidx.credentials:credentials-play-services-auth:1.5.0-rc01")

    // Added dependency for CircleImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation("androidx.core:core-ktx:1.13.1") // Replace with the latest stable version
    implementation("androidx.appcompat:appcompat:1.7.0") // Replace with the latest stable version
    implementation("com.google.android.material:material:1.12.0") // Replace with the latest stable version

}