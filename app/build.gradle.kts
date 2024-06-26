plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.gms)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.trainer.srb.rus"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.trainer.srb.rus"
        minSdk = 24
        targetSdk = 34
        versionCode = 1429
        versionName = "0.14.29"

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

    flavorDimensions += "version"
    productFlavors {
        create("free") {
            dimension = "version"
            applicationIdSuffix = ".free"
            isDefault = true
            manifestPlaceholders["appLabel"] = "Srb-Rus"
        }
        create("admin") {
            dimension = "version"
            applicationIdSuffix = ".admin"
            manifestPlaceholders["appLabel"] = "Srb-Rus.Admin"
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
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(project(":core:design"))
    implementation(project(":core:dictionary"))
    implementation(project(":core:exercise"))
    implementation(project(":core:translation"))
    implementation(project(":core:repository"))
    implementation(project(":core:utils"))

    implementation(project(":feature:learn"))
    implementation(project(":feature:dictionary"))
    implementation(project(":feature:addword"))
    implementation(project(":feature:editword"))
    implementation(project(":feature:exercise"))
    implementation(project(":feature:about"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.hilt.navigation.compose)

    kapt(libs.dagger.hilt.android.compliler)
    implementation(libs.dagger.hilt.android)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    implementation(libs.protobuf.javalite)

    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.junit)
}

protobuf {
    // Configures the Protobuf compilation and the protoc executable
    protoc {
        // Downloads from the repositories
        artifact = "com.google.protobuf:protoc:3.8.0"
    }
    // Generates the java Protobuf-lite code for the Protobufs in this project
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}