package com.ilpanda.arch.common.kotlin

import android.app.Activity
import android.app.Service
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf

/**
 * Starts the Activity [A], in a more concise way, while still allowing to configure the [Intent] in
 * the optional [configIntent] lambda.
 */
inline fun <reified T : Activity> Context.start(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(configIntent))
}

inline fun <reified T : Service> Context.startService(configIntent: Intent.() -> Unit = {}) {
    startService(Intent(this, T::class.java).apply(configIntent))
}

/**
 * Starts an Activity that supports the passed [action], in a more concise way,
 * while still allowing to configure the [Intent] in the optional [configIntent] lambda.
 *
 * If there's no matching [Activity], the underlying platform API will throw an
 * [ActivityNotFoundException].
 *
 * If there is more than one matching [Activity], the Android system may show an activity chooser to
 * the user.
 */
@Throws(ActivityNotFoundException::class)
inline fun Context.startActivity(action: String, configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(action).apply(configIntent))
}

inline fun <reified T : Activity> Context.startActivity(vararg pairs: Pair<String, Any?>, crossinline block: Intent.() -> Unit = {}
) = startActivity(intentOf<T>(*pairs).apply(block))


inline fun <reified T : Activity> Context.startService(vararg pairs: Pair<String, Any?>, crossinline block: Intent.() -> Unit = {}
) = startService(intentOf<T>(*pairs).apply(block))

inline fun <reified T> Context.intentOf(vararg pairs: Pair<String, *>): Intent = intentOf<T>(bundleOf(*pairs))

inline fun <reified T> Context.intentOf(bundle: Bundle): Intent = Intent(this, T::class.java).apply { putExtras(bundle) }




