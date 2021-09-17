package com.ilpanda.arch_common.kotlin

import android.app.Activity
import androidx.fragment.app.Fragment


inline fun <reified T> Activity.getValue(name: String, defaultValue: T? = null) = lazy {
    val value = intent?.extras?.get(name)
    if (value is T) value else defaultValue
}

inline fun <reified T : Any> Activity.getValueNonNull(name: String, defaultValue: T? = null) = lazy {
    val value = intent?.extras?.get(name)
    requireNotNull((if (value is T) value else defaultValue)) { name }
}


inline fun <reified T : Any> Fragment.getValue(name: String, defaultValue: T? = null) = lazy {
    val value = arguments?.get(name)
    if (value is T) value else defaultValue
}


inline fun <reified T : Any> Fragment.getValueNonNull(name: String, defaultValue: T? = null) = lazy {
    val value = arguments?.get(name)
    requireNotNull(if (value is T) value else defaultValue) { name }
}