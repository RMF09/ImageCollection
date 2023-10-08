import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    const val androidCoreKtx = "androidx.core:core-ktx:${Versions.androidCoreKtx}"

    const val composeMaterial = "androidx.compose.material3:material3:${Versions.composeMaterial3}"
    const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val composeActivity = "androidx.activity:activity-compose:${Versions.composeActivity}"
    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"


    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltAgp = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    const val paging = "androidx.paging:paging-compose:${Versions.paging}"

    const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
    const val viewModelSavedState =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"

    const val liveDataRuntime = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
    const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"

    const val lifeCycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"

    const val composeDestinationCore =
        "io.github.raamcosta.compose-destinations:animations-core:${Versions.composeDestination}"
    const val composeDestinationKsp =
        "io.github.raamcosta.compose-destinations:ksp:${Versions.composeDestination}"


}

fun DependencyHandler.room() {
    implementation(Dependencies.roomRuntime)
    implementation(Dependencies.roomCompiler)
    ksp(Dependencies.roomKtx)
}

fun DependencyHandler.retrofit() {
    implementation(Dependencies.retrofit)
    implementation(Dependencies.gson)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttpLoggingInterceptor)
}

fun DependencyHandler.compose() {
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeRuntime)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiTooling)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.composeActivity)
    implementation(platform(Dependencies.composeBom))
    debugImplementation(Dependencies.composeUiToolingPreview)
    androidTestImplementation(platform(Dependencies.composeBom))
}

fun DependencyHandler.composeDestination() {
    implementation(Dependencies.composeDestinationCore)
    ksp(Dependencies.composeDestinationKsp)
}

fun DependencyHandler.viewModel() {
    implementation(Dependencies.viewModelCompose)
    implementation(Dependencies.viewModelKtx)
    implementation(Dependencies.viewModelSavedState)
}

fun DependencyHandler.lifeCycle() {
    implementation(Dependencies.lifeCycleRuntimeKtx)
    implementation(Dependencies.lifecycleCommon)
}

fun DependencyHandler.liveData() {
    implementation(Dependencies.liveDataRuntime)
    implementation(Dependencies.liveDataKtx)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.hiltAndroid)
    implementation(Dependencies.hiltNavigationCompose)
    ksp(Dependencies.hiltCompiler)
}
