package cn.ilpanda.arch.debug.apm.hook

val DEFAULT_PROPERTY = DefaultBuilder()

class DefaultBuilder(
    val isDebug: Boolean = false,
    val installToast: Boolean = true,
    val installDialog: Boolean = true,
    val installPopupWindow: Boolean = true,
    val installActivity: Boolean = true,
) {
    fun copy(
        isDebug: Boolean = this.isDebug,
        installToast: Boolean = this.installToast,
        installDialog: Boolean = this.installDialog,
        installPopupWindow: Boolean = this.installPopupWindow,
        installActivity: Boolean = this.installActivity,
    ) = DefaultBuilder(
        isDebug,
        installToast,
        installDialog,
        installPopupWindow,
        installActivity,
    )
}

class EpicConfig(
    val isDebug: Boolean = false,
    val installToast: Boolean = false,
    val installDialog: Boolean = false,
    val installPopupWindow: Boolean = false,
    val installActivity: Boolean = false,
    val customHandlerSet: HashSet<EpicHandler> = hashSetOf(),
)

class EpicConfigBuilder {

    private var defaults: DefaultBuilder

    private val customHandlerSet = hashSetOf<EpicHandler>()

    constructor() {
        defaults = DEFAULT_PROPERTY
    }

    fun installToast(enable: Boolean) = apply {
        defaults = defaults.copy(installToast = enable)
    }

    fun installDialog(enable: Boolean) = apply {
        defaults = defaults.copy(installDialog = enable)
    }

    fun installPopupWindow(enable: Boolean) = apply {
        defaults = defaults.copy(installPopupWindow = enable)
    }

    fun installActivity(enable: Boolean) = apply {
        defaults = defaults.copy(installActivity = enable)
    }

    fun installCustomHandler(epicHandler: EpicHandler) {
        customHandlerSet.add(epicHandler)
    }

    fun build(): EpicConfig {
        return EpicConfig(
            isDebug = defaults.isDebug,
            installToast = defaults.installToast,
            installDialog = defaults.installDialog,
            installPopupWindow = defaults.installPopupWindow,
            installActivity = defaults.installActivity,
            customHandlerSet = this.customHandlerSet,
        )
    }

}