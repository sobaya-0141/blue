
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    id("dev.icerock.mobile.multiplatform-network-generator")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktorfit.lib)
            implementation(libs.koin.core)
            implementation(libs.koin.annotations)
            implementation(libs.moko.network)
            implementation(libs.serialization)
        }
    }
}

android {
    namespace = "sobaya.app.blue.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.ktorfit.ksp)
    add("kspAndroid", libs.ktorfit.ksp)
    add("kspIosArm64", libs.ktorfit.ksp)
    add("kspIosX64", libs.ktorfit.ksp)
}

mokoNetwork {
    spec("anime") {
        packageName = "sobaya.app.blue.repository"

        val apiSourceDir = "${layout.buildDirectory.asFile.get()}/generated/moko-network/$name/src/main/kotlin/${packageName!!.replace(".", "/")}/apis"

        inputSpec = file("src/commonMain/api-docs.json")

        configureTask {
            validateSpec = false
            doLast {
                delete(file(apiSourceDir))
            }
        }
    }
}
