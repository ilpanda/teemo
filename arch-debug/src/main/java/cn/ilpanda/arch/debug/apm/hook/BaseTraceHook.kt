package cn.ilpanda.arch.debug.apm.hook

import android.util.Log
import de.robv.android.xposed.XC_MethodHook

open class BaseTraceHook(val logTag: String = "") : XC_MethodHook() {

    override fun beforeHookedMethod(param: MethodHookParam?) {
        super.beforeHookedMethod(param)
        Log.i("EpicHook: $logTag", Log.getStackTraceString(RuntimeException()))
    }
}
