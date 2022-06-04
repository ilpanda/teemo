package com.ilpanda.verison;

public interface IDependencyManager {

    /**
     * Gradle 插件
     */
    String gradle_tool_plugin();

    /**
     * Kotlin 插件
     */
    String kotlin_gradle_plugin();

    /**
     * Android minSdkVersion
     */
    int min_sdk_version();

    /**
     * Android targetSdkVersion
     */
    int target_sdk_version();

    /**
     * Android compileSdkVersion
     */
    int compile_sdk_version();

    /**
     * AndroidX lifecycle 版本
     */
    String lifecycle_version();

    /**
     * kotlin 版本
     */
    String kotlin_version();

    /**
     * kotlin 协程版本
     */
    String kotlin_coroutines_version();

    /**
     * androidx.recyclerview:recyclerview
     */
    String androidx_recyclerview();

    /**
     * androidx.constraintlayout:constraintlayout
     */
    String androidx_constraintlayout();

    /**
     * aandroidx.multidex:multidex
     */
    String androidx_multidex();

    /**
     * androidx.exifinterface:exifinterface
     */
    String androidx_exifinterface();

    /**
     * androidx.lifecycle:lifecycle-extensions
     */
    String androidx_lifecycle_extensions();

    /**
     * androidx.lifecycle:lifecycle-compiler
     */
    String androidx_lifecycle_compiler();

    /**
     * androidx.lifecycle:lifecycle-common
     */
    String androidx_lifecycle_common();

    /**
     * androidx.lifecycle:lifecycle-viewmodel-ktx
     */
    String androidx_lifecycle_viewmodel_ktx();

    /**
     * androidx.lifecycle:lifecycle-livedata-ktx
     */
    String androidx_lifecycle_livedata_ktx();

    /**
     * androidx.lifecycle:lifecycle-runtime-ktx
     */
    String androidx_lifecycle_runtime_ktx();

    /**
     * androidx.fragment:fragment-ktx
     */
    String androidx_fragment_ktx();

    /**
     * androidx.cardview:cardview
     */
    String androidx_cardview();

    /**
     * androidx.appcompat:appcompat
     */
    String androidx_appcompat();

    /**
     * androidx.core:core
     */
    String androidx_core();

    /**
     * androidx.core:core-ktx
     */
    String androidx_core_ktx();

    /**
     * androidx.startup:startup-runtime
     */
    String androidx_startup_runtime();

    /**
     * androidx.annotation:annotation
     */
    String androidx_annotation();

    /**
     * androidx.viewpager2:viewpager2
     */
    String androidx_viewpager2();

    /**
     * androidx.asynclayoutinflater:asynclayoutinflater
     */
    String androidx_asynclayoutinflater();

    /**
     * com.jakewharton:butterknife
     */
    String butterknife();

    /**
     * com.jakewharton:butterknife-compiler
     */
    String butterknife_compiler();

    /**
     * com.github.bumptech.glide:glide
     */
    String glide();

    /**
     * com.github.bumptech.glide:compiler
     */
    String glide_compiler();

    /**
     * jp.wasabeef:glide-transformations
     */
    String glide_transformations();

    /**
     * com.squareup.okhttp3:okhttp
     */
    String okhttp();

    /**
     * com.squareup.okio:okio
     */
    String okio();

    /**
     * org.jetbrains.kotlin:kotlin-stdlib
     */
    String kotlin_stdlib();

    /**
     * org.jetbrains.kotlin:kotlin-reflect
     */
    String kotlin_reflect();

    /**
     * org.jetbrains.kotlinx:kotlinx-coroutines-android
     */
    String kotlin_coroutines_android();

    /**
     * com.google.code.gson:gson
     */
    String gson();

    /**
     * org.greenrobot:eventbus
     */
    String eventbus();

    /**
     * com.tencent.mars:mars-xlog
     */
    String mars_xlog();

    /**
     * com.squareup.retrofit2:retrofit
     */
    String retrofit();

    /**
     * com.squareup.retrofit2:converter-gson
     */
    String retrofit_converter_gson();

    /**
     * com.google.android.material:material
     */
    String material();

    /**
     * com.github.CymChad:BaseRecyclerViewAdapterHelper
     */
    String base_recyclerview_adapter_helper();


    /**
     * com.squareup.leakcanary:leakcanary-android
     */
    String leakcanary_android();

    /**
     * junit:junit
     */
    String test_junit();

    /**
     * androidx.test.ext:junit
     */
    String test_androidx_junit();

    /**
     * androidx.test.espresso:espresso-core
     */
    String test_androidx_espresso_core();

    /**
     * io.github.ilpanda.teemo:teemo-android-common
     */
    String teemo_android_common();

    /**
     * io.github.ilpanda.teemo:teemo-android-epic
     */
    String teemo_android_epic();

    /**
     * io.github.ilpanda.teemo:teemo-android-epic-no-op
     */
    String teemo_android_epic_no_op();

    /**
     * io.github.ilpanda.teemo:teemo-android-epic-demo
     */
    String teemo_android_epic_demo();

    /**
     * io.github.ilpanda.teemo:teemo-java-base
     */
    String teemo_java_base();

    /**
     * io.github.ilpanda.teemo:teemo-android-free-reflection
     */
    String teemo_android_free_reflection();

    /**
     * io.github.ilpanda.teemo:teemo-android-debug
     */
    String teemo_android_debug();

    /**
     * io.github.ilpanda.teemo:teemo-android-toast
     */
    String teemo_android_toast();

    /**
     * me.weishu.exposed:exposed-xposedapi
     */
    String exposed_xposedapi();


}
