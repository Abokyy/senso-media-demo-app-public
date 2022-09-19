plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")


    implementation(project(":domain"))

    implementation("io.ktor:ktor-client-android:2.0.2")
    implementation("io.ktor:ktor-client-serialization:2.0.2")
    implementation("io.ktor:ktor-client-auth:2.0.2")
    implementation("io.ktor:ktor-client-logging:2.0.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.2")
    implementation("io.ktor:ktor-serialization-kotlinx-xml:2.0.2")
    implementation("io.ktor:ktor-client-content-negotiation:2.0.2")
    implementation("org.kodein.di:kodein-di:7.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.startup:startup-runtime:1.1.0")
    implementation("org.apache.commons:commons-email:1.5")
    implementation ("org.simpleframework:simple-xml:2.7.1")

    implementation (platform("com.google.firebase:firebase-bom:30.4.1"))
    implementation ("com.google.firebase:firebase-messaging-ktx")

}