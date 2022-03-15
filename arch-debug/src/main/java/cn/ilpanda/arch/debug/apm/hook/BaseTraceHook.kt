package cn.ilpanda.arch.debug.apm.hook

import android.util.Log
import de.robv.android.xposed.XC_MethodHook

open class BaseTraceHook() : XC_MethodHook() {

    private val TAG = "TeemoEpicHook"

    override fun beforeHookedMethod(param: MethodHookParam?) {
        super.beforeHookedMethod(param)
        Log.i(TAG, "beforeHookedMethod: " + Log.getStackTraceString(RuntimeException()))
    }

}
