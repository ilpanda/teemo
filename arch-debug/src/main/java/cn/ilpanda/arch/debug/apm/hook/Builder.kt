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

class EpicConfig private constructor(
    val isDebug: Boolean = DEFAULT_PROPERTY.isDebug,
    val installToast: Boolean = DEFAULT_PROPERTY.installToast,
    val installDialog: Boolean = DEFAULT_PROPERTY.installDialog,
    val installPopupWindow: Boolean = DEFAULT_PROPERTY.installPopupWindow,
    val installActivity: Boolean = DEFAULT_PROPERTY.installActivity,
    val customHandlerSet: HashSet<EpicHandler> = hashSetOf(),
) {
    class EpicConfigBuilder {

        private var defaults: DefaultBuilder

        private val customHandlerSet = hashSetOf<EpicHandler>()

        constructor(debug: Boolean) {
            defaults = DEFAULT_PROPERTY
            defaults = defaults.copy(isDebug = debug)
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

        fun installCustomHandler(epicHandler: EpicHandler) = apply {
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
}


