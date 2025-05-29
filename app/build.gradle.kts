plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)

  alias(libs.plugins.kotlin.parcelize)
  alias(libs.plugins.apollo.gradle)
  alias(libs.plugins.ksp)
  kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

android {
  namespace = "com.reverb.android.onsite"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.reverb.android.onsite"
    minSdk = 24
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
  buildFeatures {
    compose = true
  }
}

apollo {
  service("graphql") {
    srcDir("src/main/java/com/reverb/android/onsite/data/graphql/")
    packageName.set("com.reverb.android.onsite")
    codegenModels.set("responseBased")
    mapScalar("Timestamp", "java.util.Date", "com.apollographql.apollo.adapter.DateAdapter")
    schemaFiles.from(
      files(
        "src/main/java/com/reverb/android/onsite/data/schemas/supergraph.graphqls",
        "src/main/java/com/reverb/android/onsite/data/schemas/extensions.graphqls",
      )
    )
  }
}

dependencies {
  implementation(platform(libs.koin.bom))
  implementation(libs.bundles.koin)

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

  with(libs) {
    implementation(kotlin.stdlib)
    implementation(androidx.ktx)
    implementation(platform(libs.koin.bom))
    implementation(koin.core)
    implementation(koin.android)
    implementation(koin.annotations)
    ksp(koin.ksp)
    implementation(bundles.ktor.client)
    implementation(bundles.apollo)
    implementation(androidx.paging)
    implementation(logcat)
    implementation(androidx.preferences)
    implementation(joda.time)
    implementation(pretty.time)
    implementation(apollo.engine.ktor)
    implementation(libs.bundles.coil)

    testImplementation(apollo.test)
    testImplementation(androidx.paging.testing)
    testImplementation(mockk)
    testImplementation(hamcrest)
    testImplementation(androidx.junit)
    testImplementation(coroutines.test)
    testImplementation(turbine)
    testImplementation(ktor.client.mock)
    testImplementation(robolectric.core)
  }

}