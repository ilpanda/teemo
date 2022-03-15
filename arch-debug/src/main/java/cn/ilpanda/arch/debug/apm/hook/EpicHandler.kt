package cn.ilpanda.arch.debug.apm.hook

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupWindow
import android.widget.Toast
import de.robv.android.xposed.DexposedBridge

interface EpicHandler {

    fun handle()

}

class PopupWindowHandler : EpicHandler {

    override fun handle() {
        // Hook PopupWindow 的 showAsDropDown() 方法
        DexposedBridge.findAndHookMethod(PopupWindow::class.java, "showAsDropDown",
            View::class.java,
            Int::class.java,
            Int::class.java,
            Int::class.java,
            BaseTraceHook())

        // Hook PopupWindow 的 showAtLocation() 方法
        DexposedBridge.findAndHookMethod(PopupWindow::class.java, "showAtLocation",
            View::class.java,
            Int::class.java,
            Int::class.java,
            Int::class.java,
            BaseTraceHook())

        // Hook PopupWindow 的 dismiss() 方法
        DexposedBridge.findAndHookMethod(PopupWindow::class.java, "dismiss", BaseTraceHook())
    }

}

class ToastHandler : EpicHandler {
    override fun handle() {
        DexposedBridge.hookAllMethods(Toast::class.java, "show", BaseTraceHook())
    }
}

class ActivityHandler : EpicHandler {
    override fun handle() {
        // Hook Activity 的跳转方法
        DexposedBridge.findAndHookMethod(Activity::class.java,
            "startActivityForResult",
            Intent::class.java,
            Int::class.java,
            Bundle::class.java,
            BaseTraceHook())

        // Hook Activity 的 finish 方法
        DexposedBridge.findAndHookMethod(Activity::class.java, "finish")
    }
}


class DialogHandler : EpicHandler {
    override fun handle() {
        // Hook Dialog 的 show() 方法
        DexposedBridge.hookAllMethods(Dialog::class.java, "show", BaseTraceHook())

        // Hook Dialog 的 dismiss() 方法
        DexposedBridge.hookAllMethods(Dialog::class.java, "dismiss", BaseTraceHook())
    }
}







