package com.ilpanda.plugin.extesion

import org.gradle.api.Action
import org.gradle.api.tasks.Nested

/**
 * 插件扩展属性配置
 */
open class PluginConfigExtension {

    /**
     * true: 开启该字节码插件
     * 默认为 true
     */
    var enable = true

    /**
     * true: 调试模式，打印更多日志。
     * 默认值为 false
     */
    var debug = false


    /**
     * 删除 Android 日志
     */
    @Nested
    val deleteAndroidLogMethod: AndroidLogExtension = AndroidLogExtension()

    open fun deleteAndroidLogMethod(action: Action<in AndroidLogExtension?>) {
        action.execute(deleteAndroidLogMethod)
    }

    /**
     * 监听耗时方法
     */
    @Nested
    val timingMethod: TimingMethodExtension = TimingMethodExtension()

    open fun timingMethod(action: Action<in TimingMethodExtension?>) {
        action.execute(timingMethod)
    }

    /**
     * Android Log 输出时附带行号
     */
    @Nested
    val lineLogMethod: LineLogMethodExtension = LineLogMethodExtension()

    open fun lineLogMethod(action: Action<in LineLogMethodExtension?>) {
        action.execute(lineLogMethod)
    }

    override fun toString(): String {
        return "PluginConfigExtension(enable=$enable, debug=$debug, deleteAndroidLogMethod=$deleteAndroidLogMethod, timingMethod=$timingMethod, lineLogMethod=$lineLogMethod)"
    }


}