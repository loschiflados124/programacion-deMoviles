plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.utesa.toastadivinanumero"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.utesa.toastadivinanumero"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // enableEdgeToEdge() y "by viewModels()" (crea el ViewModel)
    implementation(libs.androidx.activity.ktx)

    // ViewModel: guarda el número generado para que no cambie al rotar la pantalla
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
}
