package com.ilpanda.plugin

import com.android.build.gradle.AppExtension
import com.ilpanda.plugin.extesion.PluginConfigExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.*

/**
 * Android 开发常用插件 apm 监控
 */
class TeemoAndroidPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val extension = target.extensions.create("pluginConfig", PluginConfigExtension::class.java)

        // 打印配置
        target.afterEvaluate { project ->
            (project.extensions.getByName("pluginConfig") as? PluginConfigExtension)?.let {
                println(it)
            }
        }

        var enablePlugin = extension.enable

        // 是否调试插件支持两种配置：
        // 1. 在 build.gradle 中配置扩展属性
        // 2. 在项目根目录的 gradle.properties 中配置 androidPlugin.enablePlugin
        val properties = Properties()
        if (target.rootProject.file("gradle.properties").exists()) {
            properties.load(target.rootProject.file("gradle.properties").inputStream())
            val key = "androidPlugin.enablePlugin"
            if (properties.containsKey(key)) {
                enablePlugin = properties.getProperty(key, "false").toBoolean()
            }
        }

        if (enablePlugin) {
            println("------------您已开启了 APM 调试插件--------------")
            val appExtension = target.extensions.getByType(AppExtension::class.java)
            appExtension.registerTransform(CommonTransform(extension))
        } else {
            println("------------您已关闭了 APM 调试插件--------------")
        }
    }
}