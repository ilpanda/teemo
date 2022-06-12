package com.ilpanda.verison;

public class DefaultDependencyManager implements IDependencyManager {

    @Override
    public String gradle_tool_plugin() {
        return Constants.ClassPath.GRADLE_TOOL_PLUGIN;
    }

    @Override
    public String kotlin_gradle_plugin() {
        return Constants.ClassPath.KOTLIN_GRADLE_PLUGIN;
    }

    @Override
    public int min_sdk_version() {
        return Constants.Version.MIN_SDK_VERSION;
    }

    @Override
    public int target_sdk_version() {
        return Constants.Version.TARGET_SDK_VERSION;
    }

    @Override
    public int compile_sdk_version() {
        return Constants.Version.COMPILE_SDK_VERSION;
    }

    @Override
    public String lifecycle_version() {
        return Constants.Version.LIFECYCLE_VERSION;
    }

    @Override
    public String kotlin_version() {
        return Constants.Version.KOTLIN_VERSION;
    }

    @Override
    public String kotlin_coroutines_version() {
        return Constants.Version.COROUTINES_VERSION;
    }

    @Override
    public String androidx_recyclerview() {
        return Constants.AndroidX.RECYCLREVIEW;
    }

    @Override
    public String androidx_constraintlayout() {
        return Constants.AndroidX.CONSTRAINTLAYOUT;
    }

    @Override
    public String androidx_multidex() {
        return Constants.AndroidX.MULTIDEX;
    }

    @Override
    public String androidx_exifinterface() {
        return Constants.AndroidX.EXIFINTERFACE;
    }

    @Override
    public String androidx_lifecycle_extensions() {
        return Constants.AndroidX.LIFECYCLE_EXTENSIONS;
    }

    @Override
    public String androidx_lifecycle_compiler() {
        return Constants.AndroidX.LIFECYCLE_COMPILER;
    }

    @Override
    public String androidx_lifecycle_common() {
        return Constants.AndroidX.LIFECYCLE_COMMON;
    }

    @Override
    public String androidx_lifecycle_viewmodel_ktx() {
        return Constants.AndroidX.LIFECYCLE_VIEWMODEL_KTX;
    }

    @Override
    public String androidx_lifecycle_livedata_ktx() {
        return Constants.AndroidX.LIFECYCLE_LIVEDATA_KTX;
    }

    @Override
    public String androidx_lifecycle_runtime_ktx() {
        return Constants.AndroidX.LIFECYCLE_RUNTIME_KTX;
    }

    @Override
    public String androidx_fragment_ktx() {
        return Constants.AndroidX.FRAGMENT_KTX;
    }

    @Override
    public String androidx_cardview() {
        return Constants.AndroidX.CARDVIEW;
    }

    @Override
    public String androidx_appcompat() {
        return Constants.AndroidX.APPCOMPAT;
    }

    @Override
    public String androidx_core() {
        return Constants.AndroidX.CORE;
    }

    @Override
    public String androidx_core_ktx() {
        return Constants.AndroidX.CORE_KTX;
    }

    @Override
    public String androidx_startup_runtime() {
        return Constants.AndroidX.STARTUP_RUNTIME;
    }

    @Override
    public String androidx_annotation() {
        return Constants.AndroidX.ANNOTATION;
    }

    @Override
    public String androidx_viewpager2() {
        return Constants.AndroidX.VIEWPAGER2;
    }

    @Override
    public String androidx_asynclayoutinflater() {
        return Constants.AndroidX.ASYNCLAYOUTINFLATER;
    }

    @Override
    public String butterknife() {
        return Constants.Butterknife.BUTTERKNIFE;
    }

    @Override
    public String butterknife_compiler() {
        return Constants.Butterknife.BUTTERKNIFE_COMPILER;
    }

    @Override
    public String glide() {
        return Constants.Glide.GLIDE;
    }

    @Override
    public String glide_compiler() {
        return Constants.Glide.GLIDE_COMPILER;
    }

    @Override
    public String glide_transformations() {
        return Constants.Glide.GLIDE_TRANSFORMATIONS;
    }

    @Override
    public String okhttp() {
        return Constants.Common.OKHTTP;
    }

    @Override
    public String okio() {
        return Constants.Common.OKIO;
    }

    @Override
    public String kotlin_stdlib() {
        return Constants.Kotlin.STDLIB;
    }

    @Override
    public String kotlin_reflect() {
        return Constants.Kotlin.REFLECT;
    }

    @Override
    public String kotlin_coroutines_android() {
        return Constants.Kotlin.COROUTINES_ANDROID;
    }

    @Override
    public String gson() {
        return Constants.Common.GSON;
    }

    @Override
    public String eventbus() {
        return Constants.Common.EVENTBUS;
    }

    @Override
    public String mars_xlog() {
        return Constants.Common.MARS_XLOG;
    }

    @Override
    public String retrofit() {
        return Constants.Common.RETROFIT;
    }

    @Override
    public String retrofit_converter_gson() {
        return Constants.Common.RETROFIT_CONVERT_GSON;
    }

    @Override
    public String material() {
        return Constants.Common.MATERIAL;
    }

    @Override
    public String base_recyclerview_adapter_helper() {
        return Constants.Common.BASE_RECYCLERVIEW_ADAPTER_HELPER;
    }

    @Override
    public String leakcanary_android() {
        return Constants.Common.LEAKCANARY_ANDROID;
    }

    @Override
    public String test_junit() {
        return Constants.Test.TEST_JUNIT;
    }

    @Override
    public String test_androidx_junit() {
        return Constants.Test.TEST_ANDROIDX_JUNIT;
    }

    @Override
    public String test_androidx_espresso_core() {
        return Constants.Test.TEST_ESPRESSO_CORE;
    }

    @Override
    public String teemo_android_common() {
        return Constants.Teemo.TEEMO_ANDROID_COMMON;
    }

    @Override
    public String teemo_android_epic() {
        return Constants.Teemo.TEEMO_ANDROID_EPIC;
    }

    @Override
    public String teemo_android_epic_no_op() {
        return Constants.Teemo.TEEMO_ANDROID_EPIC_NO_OP;
    }

    @Override
    public String teemo_android_epic_demo() {
        return Constants.Teemo.TEEMO_ANDROID_EPIC_DEMO;
    }

    @Override
    public String teemo_java_base() {
        return Constants.Teemo.TEEMO_JAVA_BASE;
    }

    @Override
    public String teemo_android_free_reflection() {
        return Constants.Teemo.TEEMO_FREE_REFLECTION;
    }

    @Override
    public String teemo_android_debug() {
        return Constants.Teemo.TEEMO_ANDROID_DEBUG;
    }

    @Override
    public String teemo_android_toast() {
        return Constants.Teemo.TEEMO_ANDROID_TOAST;
    }

    @Override
    public String exposed_xposedapi() {
        return Constants.Common.EXPOSED_XPOSEDAPI;
    }


}
