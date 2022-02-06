package com.ilpanda.arch_common.kotlin

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.fragment.app.Fragment

/**
 * Fragment 所在的 Activity 否为横屏
 */
inline var Fragment.isLandscape: Boolean
    get() = activity?.isLandscape == true
    set(value) {
        activity?.isLandscape = value
    }

/**
 * Activity 是否为横屏
 */
inline var Activity.isLandscape: Boolean
    get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    set(value) {
        requestedOrientation = if (value) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

/**
 * Fragment 所在的 Activity 否为竖屏
 */
inline var Fragment.isPortrait: Boolean
    get() = activity?.isPortrait == true
    set(value) {
        activity?.isPortrait = value
    }

/**
 * Activity 是否为竖屏
 */
inline var Activity.isPortrait: Boolean
    get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    set(value) {
        requestedOrientation = if (value) {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }
