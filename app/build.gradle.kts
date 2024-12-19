plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 26
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
    // 基本ライブラリ
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.kotlinx.coroutines.android)

    // UIとテーマ関連
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.ui.test.android)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.appcompat)

    // UIテストとデバッグ用
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.androidx.ui.test.android)

    // ナビゲーション
    implementation(libs.androidx.navigation.compose)
    implementation (libs.androidx.navigation.compose.v260)

    // 外部ライブラリ
    implementation(libs.places)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)

    // アイコン関連
    implementation(libs.androidx.material.icons.extended)

    // テスト関連
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // その他
    implementation(libs.androidx.material) // Material Design用サポート
    implementation(libs.androidx.material3.v110) // 最新のMaterial3バージョン
    implementation(libs.androidx.material.v140) // 最新のMaterialアイコン
    implementation(libs.androidx.material3.v100) // 最新のMaterial3バージョン
    implementation(libs.material3)
    implementation (libs.androidx.ui.v140) // Compose UI

    implementation (libs.androidx.ui.tooling.preview.v140) // プレビュー用ツール
    implementation (libs.androidx.ui.tooling.v140) // ツール用ライブラリ
    implementation (libs.androidx.runtime.livedata)
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio) // HTTP通信用
    implementation(libs.ktor.client.serialization) // JSON対応
    implementation(libs.logging.interceptor) // バージョンを最新に更新することをお勧めします
    implementation(libs.okhttp.v493)
    implementation (libs.ui)
    implementation (libs.ui.tooling.preview)
    implementation (libs.androidx.lifecycle.runtime.ktx.v262)
    implementation (libs.androidx.activity.compose.v180)
    implementation(libs.kotlinx.serialization.json)
    implementation (libs.hilt.android) // Use the latest version

    // Hilt for Jetpack Compose
    implementation (libs.androidx.hilt.navigation.compose)

    // Other essential Compose dependencies
    implementation (libs.androidx.runtime.livedata.v151) //

    // LiveData
    implementation (libs.androidx.lifecycle.livedata.ktx) // or the latest version
    // ViewModel (if not added)
    implementation (libs.androidx.lifecycle.viewmodel.ktx.v261)

    implementation (libs.androidx.ui.v150)
    implementation (libs.androidx.material.v150)
    implementation (libs.androidx.material3.v120)
    implementation (libs.androidx.foundation)

    // Compose runtime
    implementation (libs.androidx.runtime)
    implementation (libs.androidx.runtime.livedata.v140)

    // Kotlin support for Compose
    implementation (libs.androidx.activity.compose.v170)

    implementation (libs.androidx.work.runtime.ktx)
    implementation (libs.threetenabp)
}