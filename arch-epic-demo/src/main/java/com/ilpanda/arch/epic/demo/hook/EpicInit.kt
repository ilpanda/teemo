package com.ilpanda.arch.epic.demo.hook

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.telephony.TelephonyManager

object EpicInit {

    @JvmStatic
    fun init(context: Context, epicConfig: EpicConfig) {

        // 1. 模拟器不使用 epic
        // 2. 正式发布不使用 epic
        if (isEmulator(context.applicationContext) || !epicConfig.isDebug) {
            return
        }

        initInnerHandler(epicConfig)

        installCustomHandler(epicConfig)
    }

    private fun initInnerHandler(epicConfig: EpicConfig) {
        if (epicConfig.installDialog) {
            epicConfig.addHandler(DialogHandler())
        }

        if (epicConfig.installToast) {
            epicConfig.addHandler(ToastHandler())
        }

        if (epicConfig.installPopupWindow) {
            epicConfig.addHandler(PopupWindowHandler())
        }

        if (epicConfig.installActivity) {
            epicConfig.addHandler(ActivityHandler())
        }
    }

    private fun installCustomHandler(epicConfig: EpicConfig) {
        epicConfig.handle()
    }

    /**
     * 是否为模拟器
     */
    private fun isEmulator(context: Context): Boolean {
        val checkProperty = (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MODEL.contains("sdk_gphone64_x86_64")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || "google_sdk" == Build.PRODUCT)
        if (checkProperty) return true
        var operatorName = ""
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val name = tm.networkOperatorName
        if (name != null) {
            operatorName = name
        }
        val checkOperatorName = operatorName.toLowerCase() == "android"
        if (checkOperatorName) return true
        val url = "tel:" + "123456"
        val intent = Intent()
        intent.data = Uri.parse(url)
        intent.action = Intent.ACTION_DIAL
        val checkDial = intent.resolveActivity(context.packageManager) == null
        return checkDial
    }
}

