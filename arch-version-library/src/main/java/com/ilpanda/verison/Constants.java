package com.ilpanda.verison;


public class Constants {

    public static class ClassPath {
        public static String GRADLE_TOOL_PLUGIN = "com.android.tools.build:gradle:4.1.3";
        public static String KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:" + Version.KOTLIN_VERSION;
    }

    public static class Version {
        public static int MIN_SDK_VERSION = 21;
        public static int TARGET_SDK_VERSION = 28;
        public static String BUILD_TOOLS_VERSION = "29.0.3";
        public static int COMPILE_SDK_VERSION = 31;
        public static String KOTLIN_VERSION = "1.5.32";
        public static String LIFECYCLE_VERSION = "2.2.0";
        public static String COROUTINES_VERSION = "1.3.9";
        public static String EPIC_VERSION = "1.3.0";
    }

    public static class AndroidX {
        public static String RECYCLREVIEW = "androidx.recyclerview:recyclerview:1.1.0";
        public static String CONSTRAINTLAYOUT = "androidx.constraintlayout:constraintlayout:2.0.4";
        public static String MULTIDEX = "androidx.multidex:multidex:2.0.1";
        public static String EXIFINTERFACE = "androidx.exifinterface:exifinterface:1.3.1";

        public static String LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:2.2.0";
        public static String LIFECYCLE_COMPILER = "androidx.lifecycle:lifecycle-compiler:" + Version.LIFECYCLE_VERSION;
        public static String LIFECYCLE_COMMON = "androidx.lifecycle:lifecycle-common:" + Version.LIFECYCLE_VERSION;
        public static String LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:" + Version.LIFECYCLE_VERSION;
        public static String LIFECYCLE_LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:" + Version.LIFECYCLE_VERSION;
        public static String LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:" + Version.LIFECYCLE_VERSION;

        public static String FRAGMENT_KTX = "androidx.fragment:fragment-ktx:1.3.6";
        public static String CARDVIEW = "androidx.cardview:cardview:1.0.0";
        public static String APPCOMPAT = "androidx.appcompat:appcompat:1.2.0";
        public static String CORE = "androidx.core:core:1.6.0";
        public static String STARTUP_RUNTIME = "androidx.startup:startup-runtime:1.0.0";
        public static String ANNOTATION = "androidx.annotation:annotation:1.2.0";
        public static String VIEWPAGER2 = "androidx.viewpager2:viewpager2:1.0.0";
        public static String CORE_KTX = "androidx.core:core-ktx:1.6.0";
        public static String ASYNCLAYOUTINFLATER = "androidx.asynclayoutinflater:asynclayoutinflater:1.0.0";

    }

    public static class Kotlin {
        public static String STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:" + Version.KOTLIN_VERSION;
        public static String REFLECT = "org.jetbrains.kotlin:kotlin-reflect:" + Version.KOTLIN_VERSION;
        public static String COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:" + Version.COROUTINES_VERSION;
    }

    public static class Butterknife {
        public static String BUTTERKNIFE = "com.jakewharton:butterknife:10.2.3";
        public static String BUTTERKNIFE_COMPILER = "com.jakewharton:butterknife-compiler:10.2.3";
    }

    public static class Glide {
        public static String GLIDE = "com.github.bumptech.glide:glide:4.11.0";
        public static String GLIDE_COMPILER = "com.github.bumptech.glide:compiler:4.11.0";
        public static String GLIDE_TRANSFORMATIONS = "jp.wasabeef:glide-transformations:3.3.0";
    }

    public static class Common {
        public static String GSON = "com.google.code.gson:gson:2.8.7";
        public static String MATERIAL = "com.google.android.material:material:1.4.0";
        public static String BASE_RECYCLERVIEW_ADAPTER_HELPER = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4";
        public static String LEAKCANARY_ANDROID = "com.squareup.leakcanary:leakcanary-android:2.7";
        public static String EXPOSED_XPOSEDAPI = "me.weishu.exposed:exposed-xposedapi:0.4.5";
        public static String EVENTBUS = "org.greenrobot:eventbus:3.2.0";
        public static String MARS_XLOG = "com.tencent.mars:mars-xlog:1.2.5";

        public static String OKHTTP = "com.squareup.okhttp3:okhttp:4.9.1";
        public static String OKIO = "com.squareup.okio:okio:3.0.0";
        public static String RETROFIT = "com.squareup.retrofit2:retrofit:2.9.0";
        public static String RETROFIT_CONVERT_GSON = "com.squareup.retrofit2:converter-gson:2.9.0";

    }

    public static class Test {
        public static final String TEST_JUNIT = "junit:junit:4.13.2";
        public static final String TEST_ANDROIDX_JUNIT = "androidx.test.ext:junit:1.1.3";
        public static final String TEST_ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.4.0";
    }

    public static class Teemo {
        public static final String TEEMO_ANDROID_COMMON = "io.github.ilpanda.teemo:teemo-android-common:1.0.0";
        public static final String TEEMO_ANDROID_EPIC = "io.github.ilpanda.teemo:teemo-android-epic:1.3.0";
        public static final String TEEMO_ANDROID_EPIC_NO_OP = "io.github.ilpanda.teemo:teemo-android-epic-no-op:1.3.0";
        public static final String TEEMO_ANDROID_EPIC_DEMO = "io.github.ilpanda.teemo:teemo-android-epic-demo:1.3.0";
        public static final String TEEMO_JAVA_BASE = "io.github.ilpanda.teemo:teemo-java-base:1.0.0";
        public static final String TEEMO_FREE_REFLECTION = "io.github.ilpanda.teemo:teemo-android-free-reflection:1.0.0";
        public static final String TEEMO_ANDROID_DEBUG = "io.github.ilpanda.teemo:teemo-android-debug:1.0.0";
        public static final String TEEMO_ANDROID_TOAST = "io.github.ilpanda.teemo:teemo-android-toast:1.0.0";
    }


}


