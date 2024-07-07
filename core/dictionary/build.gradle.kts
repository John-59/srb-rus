plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.trainer.srb.rus.core.dictionary"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    flavorDimensions += "version"
    productFlavors {
        create("free") {
            dimension = "version"
            isDefault = true
        }
        create("admin") {
            dimension = "version"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(project(":core:translation"))
    implementation(project(":core:repository"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.dagger.hilt.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.squareup.moshi.kotlin)
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.converter.moshi)

    kapt(libs.dagger.hilt.android.compliler)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    testImplementation(libs.junit)
}