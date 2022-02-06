package com.ilpanda.arch_common.kotlin

import android.app.ActivityManager
import android.app.Service
import android.content.Context


// 判断 Service 是否已经启动
inline fun <reified T : Service> T.isRunning(): Boolean {
    val manager: ActivityManager? = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
    manager?.getRunningServices(Int.MAX_VALUE)?.let {
        for (service in it) {
            if (this::class.java.name == service.service.className) {
                return true
            }
        }
    }
    return false
}

// 判断 Service 是否已经启动
fun isRunning(context: Context, clz: Class<out Service>): Boolean {
    var manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
    manager?.let {
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (clz.name == service.service.className) {
                return true;
            }
        }
    }
    return false;
}