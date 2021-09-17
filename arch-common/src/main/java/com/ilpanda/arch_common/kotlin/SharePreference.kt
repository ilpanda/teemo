package com.ilpanda.arch_common.kotlin

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferenceProperty<T>(
        private val preferenceName: String? = null,
        private val key: String,
        private val defaultValue: T,
        private val getter: SharedPreferences.(String, T) -> T,
        private val setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor) : ReadWriteProperty<Context, T> {


    override fun setValue(thisRef: Context, property: KProperty<*>, value: T) {
        thisRef.getPreferences().edit()
                .setter(key, value)
                .commit()
    }

    override fun getValue(thisRef: Context, property: KProperty<*>): T = thisRef.getPreferences().getter(key, defaultValue)

    private fun Context.getPreferences(): SharedPreferences = getSharedPreferences(preferenceName
            ?: DEFAULT_PRE_NAME, Context.MODE_PRIVATE)

    companion object {
        const val DEFAULT_PRE_NAME = "app_pre"
    }

}

fun intSharePreference(key: String, defaultValue: Int = 0, preferenceName: String? = null): ReadWriteProperty<Context, Int> =
        PreferenceProperty(preferenceName = preferenceName, key = key, defaultValue = defaultValue, getter = SharedPreferences::getInt, setter = SharedPreferences.Editor::putInt)

fun booleanPreference(key: String, defaultValue: Boolean = false, preferenceName: String? = null): ReadWriteProperty<Context, Boolean> =
        PreferenceProperty(preferenceName = preferenceName, key = key, defaultValue = defaultValue, getter = SharedPreferences::getBoolean, setter = SharedPreferences.Editor::putBoolean)

fun longPreference(key: String, defaultValue: Long = 0, preferenceName: String? = null): ReadWriteProperty<Context, Long> =
        PreferenceProperty(preferenceName = preferenceName, key = key, defaultValue = defaultValue, getter = SharedPreferences::getLong, setter = SharedPreferences.Editor::putLong)

fun floatPreference(key: String, defaultValue: Float = 0f, preferenceName: String? = null): ReadWriteProperty<Context, Float> =
        PreferenceProperty(preferenceName = preferenceName, key = key, defaultValue = defaultValue, getter = SharedPreferences::getFloat, setter = SharedPreferences.Editor::putFloat)

fun stringPreference(key: String, defaultValue: String = "", preferenceName: String? = null): ReadWriteProperty<Context, String?> =
        PreferenceProperty(preferenceName = preferenceName, key = key, defaultValue = defaultValue, getter = SharedPreferences::getString, setter = SharedPreferences.Editor::putString)


