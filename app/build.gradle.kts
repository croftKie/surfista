

plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	id("com.google.devtools.ksp") version "1.9.0-1.0.13"
}


android {
	namespace = "com.croftk.surfista"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.croftk.surfista"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
		buildConfigField("String", "GEO_KEY", "\"66ee978bf18e1248071945bjq69c4d\"")
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
		viewBinding = true
		buildConfig = true
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

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	implementation("androidx.navigation:navigation-compose:2.7.6")
	implementation(libs.androidx.navigation.runtime.ktx)

	val room_version = "2.6.1"

	implementation("androidx.room:room-runtime:$room_version")
	annotationProcessor("androidx.room:room-compiler:$room_version")
	ksp("androidx.room:room-compiler:$room_version")
	// optional - Kotlin Extensions and Coroutines support for Room
	implementation("androidx.room:room-ktx:$room_version")

	// Retrofit
	implementation ("com.squareup.retrofit2:retrofit:2.9.0")
	implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
	implementation("com.squareup.retrofit2:converter-jackson:2.9.0")

	// Coroutines
	implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
	implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")

	// Coroutine Lifecycle Scopes
	implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
	implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")

	// OSMDroid
	implementation ("org.osmdroid:osmdroid-android:6.1.20")

	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)
}