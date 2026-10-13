plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "cd.unigesta.mobile"
    compileSdk = 35
    defaultConfig {
        applicationId = "cd.unigesta.mobile"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}
