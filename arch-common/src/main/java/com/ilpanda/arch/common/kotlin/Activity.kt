package com.ilpanda.arch.common.kotlin

import android.app.Activity
import androidx.fragment.app.Fragment


/**
 *
 *  从 Intent 中根据 key 获取对应的 value
 *  @param key
 *  @param defaultValue 默认值
 *  使用方式：val name: String? by getValue("name")
 */
inline fun <reified T> Activity.getValue(key: String, defaultValue: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    if (value is T) value else defaultValue
}

/**
 *
 *  从 Intent 中根据 key 获取对应的 value，如果 Value 为空，则抛出异常。
 *  @param key
 *  @param defaultValue 默认值
 *  使用方式：val name: String? by getValueNonNull("name")
 */
inline fun <reified T : Any> Activity.getValueNonNull(key: String, defaultValue: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    requireNotNull((if (value is T) value else defaultValue)) { key }
}

inline fun <reified T : Any> Fragment.getValue(key: String, defaultValue: T? = null) = lazy {
    val value = arguments?.get(key)
    if (value is T) value else defaultValue
}

inline fun <reified T : Any> Fragment.getValueNonNull(key: String, defaultValue: T? = null) = lazy {
    val value = arguments?.get(key)
    requireNotNull(if (value is T) value else defaultValue) { key }
}