import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    sourceSets {
        
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(projects.repository)
        }
    }
}

android {
    namespace = "sobaya.app.blue"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")

    applicationVariants.forEach { variant ->
        variant.sourceSets.forEach { sourceSet ->
            sourceSet.kotlinDirectories += file("build/generated/ksp/${variant.name}/kotlin")
        }
    }

    defaultConfig {
        applicationId = "sobaya.app.blue"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        implementation(projects.repository)
        implementation(libs.koin.android)
        implementation(libs.koin.core)
        implementation(libs.koin.annotations)
        implementation(libs.koin.compose)
        ksp(libs.koin.ksp)
    }
}
