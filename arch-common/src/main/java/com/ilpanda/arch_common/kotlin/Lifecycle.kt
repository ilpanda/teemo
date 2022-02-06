package com.ilpanda.arch_common.kotlin

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

fun Application.doOnActivityLifecycle(
    onActivityCreated: ((Activity, Bundle?) -> Unit)? = null,
    onActivityStarted: ((Activity) -> Unit)? = null,
    onActivityResumed: ((Activity) -> Unit)? = null,
    onActivityPaused: ((Activity) -> Unit)? = null,
    onActivityStopped: ((Activity) -> Unit)? = null,
    onActivitySaveInstanceState: ((Activity, Bundle?) -> Unit)? = null,
    onActivityDestroyed: ((Activity) -> Unit)? = null,
): Application.ActivityLifecycleCallbacks =
    object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            onActivityCreated?.invoke(activity, savedInstanceState)
        }

        override fun onActivityStarted(activity: Activity) {
            onActivityStarted?.invoke(activity)
        }

        override fun onActivityResumed(activity: Activity) {
            onActivityResumed?.invoke(activity)
        }

        override fun onActivityPaused(activity: Activity) {
            onActivityPaused?.invoke(activity)
        }

        override fun onActivityStopped(activity: Activity) {
            onActivityStopped?.invoke(activity)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            onActivitySaveInstanceState?.invoke(activity, outState)
        }

        override fun onActivityDestroyed(activity: Activity) {
            onActivityDestroyed?.invoke(activity)
        }
    }.also {
        registerActivityLifecycleCallbacks(it)
    }


/**
 * 简化 lifecycle 的方法调用
 */
inline fun Fragment.doOnViewLifecycle(
    crossinline onCreateView: () -> Unit = {},
    crossinline onStart: () -> Unit = {},
    crossinline onResume: () -> Unit = {},
    crossinline onPause: () -> Unit = {},
    crossinline onStop: () -> Unit = {},
    crossinline onDestroyView: () -> Unit = {},
) = viewLifecycleOwner.doOnLifecycle(onCreateView, onStart, onResume, onPause, onStop, onDestroyView)

/**
 * 简化 lifecycle 的方法调用
 */
inline fun LifecycleOwner.doOnLifecycle(
    crossinline onCreate: () -> Unit = {},
    crossinline onStart: () -> Unit = {},
    crossinline onResume: () -> Unit = {},
    crossinline onPause: () -> Unit = {},
    crossinline onStop: () -> Unit = {},
    crossinline onDestroy: () -> Unit = {},
) {
    lifecycle.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            when (event) {
                Lifecycle.Event.ON_CREATE -> onCreate()
                Lifecycle.Event.ON_START -> onStart()
                Lifecycle.Event.ON_RESUME -> onResume()
                Lifecycle.Event.ON_PAUSE -> onPause()
                Lifecycle.Event.ON_STOP -> onStop()
                Lifecycle.Event.ON_DESTROY -> onDestroy()
            }
        }
    })
}


/** Suspend until [Lifecycle.getCurrentState] is at least [STARTED] */
@MainThread
suspend inline fun Lifecycle.awaitStarted() {
    // Fast path: we're already started.
    if (currentState.isAtLeast(Lifecycle.State.STARTED)) return

    // Slow path: observe the lifecycle until we're started.
    observeStarted()
}

/** Cannot be 'inline' due to a compiler bug. There is a test that guards against this bug. */
@MainThread
suspend fun Lifecycle.observeStarted() {
    var observer: LifecycleObserver? = null
    try {
        suspendCancellableCoroutine<Unit> { continuation ->
            observer = object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    when (event) {
                        Lifecycle.Event.ON_START -> continuation.resume(Unit)
                    }
                }
            }
            addObserver(observer!!)
        }
    } finally {
        // 'observer' will always be null if this method is marked as 'inline'.
        observer?.let(::removeObserver)
    }
}