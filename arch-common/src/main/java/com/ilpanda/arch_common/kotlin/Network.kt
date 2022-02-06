package com.ilpanda.arch_common.kotlin

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment

/**
 *  当前网络是否可用
 *  need <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 */
@RequiresPermission(ACCESS_NETWORK_STATE)
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
            hasCapability(NET_CAPABILITY_INTERNET) && hasCapability(NET_CAPABILITY_VALIDATED)
        }
    } else {
        @Suppress("DEPRECATION")
        connectivityManager.activeNetworkInfo?.isConnectedOrConnecting
    } ?: false
}

/**
 *  当前网络是否可用
 *  need <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 */
@RequiresPermission(ACCESS_NETWORK_STATE)
fun Fragment.isNetworkAvailable(): Boolean {
    return context?.isNetworkAvailable() ?: false
}
