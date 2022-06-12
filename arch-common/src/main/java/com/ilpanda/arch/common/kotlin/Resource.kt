package com.ilpanda.arch.common.kotlin

import android.content.Context
import android.content.res.Resources.Theme
import android.os.Build

fun Context.getThemeName(theme: Theme): String? {
    return try {
        val mThemeResId: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val fThemeImpl = theme.javaClass.getDeclaredField("mThemeImpl")
            if (!fThemeImpl.isAccessible) fThemeImpl.isAccessible = true
            val mThemeImpl = fThemeImpl[theme]
            val clazz: Class<*> = javaClass
            val method = clazz.getMethod("getThemeResId")
            method.isAccessible = true
            mThemeResId = method.invoke(this) as Int
        } else {
            val fThemeResId = theme.javaClass.getDeclaredField("mThemeResId")
            if (!fThemeResId.isAccessible) fThemeResId.isAccessible = true
            mThemeResId = fThemeResId.getInt(theme)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            theme.resources.getResourceEntryName(mThemeResId)
        } else this.resources.getResourceEntryName(mThemeResId)
    } catch (e: Exception) {
        null
    }
}
