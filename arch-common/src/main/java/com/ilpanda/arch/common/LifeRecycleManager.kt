package com.ilpanda.arch.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.os.Bundle
import java.util.*

/**
 * Activity 生命周期管理类
 * 可以监听 App 在前后台
 */
class LifeRecycleManager private constructor() : ActivityLifecycleCallbacks {

    private val TAG = LifeRecycleManager::class.java.simpleName

    private val mStateListeners: MutableList<StateListener> = ArrayList()

    private var mCurrentActivity: Activity? = null

    fun init(context: Context) {
        (context.applicationContext as Application).registerActivityLifecycleCallbacks(this)
    }

    private var mCount = 0
    private val activities = ArrayList<Activity>()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (mCurrentActivity == null) {
            mCurrentActivity = activity
        }
        activities.add(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        mCount++
        if (mCount == 1) {
            for (stateListener in mStateListeners) {
                stateListener.onForeground()
            }
        }
    }

    override fun onActivityResumed(activity: Activity) {
        mCurrentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {
        mCount--
        if (mCount == 0) {
            for (stateListener in mStateListeners) {
                stateListener.onBackground()
            }
            mCurrentActivity = null
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {
        activities.remove(activity)
    }

    val isBackground: Boolean
        get() = mCount == 0

    val isForeground: Boolean
        get() = mCount != 0

    interface StateListener {
        fun onBackground()
        fun onForeground()
    }

    fun register(stateListener: StateListener) {
        mStateListeners.add(stateListener)
    }

    fun currentActivity(): Activity? {
        return mCurrentActivity
    }

    fun unregister(stateListener: StateListener) {
        mStateListeners.remove(stateListener)
    }

    @SuppressLint("StaticFieldLeak")
    private object SingleHolder {
        val holder = LifeRecycleManager()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        val instance = SingleHolder.holder
    }

}