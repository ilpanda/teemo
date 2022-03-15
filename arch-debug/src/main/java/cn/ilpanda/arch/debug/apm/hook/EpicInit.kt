package cn.ilpanda.arch.debug.apm.hook

import com.ilpanda.arch.common.kotlin.application
import com.ilpanda.arch.common.utils.DeviceUtil

object EpicInit {

    @JvmStatic
    fun init(epicConfig: EpicConfig) {

        // 1. 模拟器不使用 epic
        // 2. 正式发布不使用 epic
        if (DeviceUtil.isEmulator(application) || !epicConfig.isDebug) {
            return
        }

        initInnerHandler(epicConfig)

        installCustomHandler(epicConfig)
    }

    private fun initInnerHandler(epicConfig: EpicConfig) {
        if (epicConfig.installDialog) {
            epicConfig.customHandlerSet.add(DialogHandler())
        }

        if (epicConfig.installToast) {
            epicConfig.customHandlerSet.add(ToastHandler())
        }

        if (epicConfig.installPopupWindow) {
            epicConfig.customHandlerSet.add(PopupWindowHandler())
        }

        if (epicConfig.installActivity) {
            epicConfig.customHandlerSet.add(ActivityHandler())
        }
    }

    private fun installCustomHandler(epicConfig: EpicConfig) {
        epicConfig.customHandlerSet.forEach {
            it.handle()
        }
    }

}

