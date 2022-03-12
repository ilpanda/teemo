package com.ilpanda.plugin.cv


import com.ilpanda.plugin.BaseClassInterceptor
import com.ilpanda.plugin.extesion.PluginConfigExtension
import com.ilpanda.plugin.mv.DeleteAndroidLogInterceptor
import com.ilpanda.plugin.mv.LineLogMethodInterceptor
import com.ilpanda.plugin.mv.TimingMethodInterceptor
import com.ilpanda.plugin.utils.PluginLog
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor

internal class InterceptorChain(classVisitor: ClassVisitor?, var pluginConfigExtension: PluginConfigExtension) : BaseClassInterceptor(classVisitor) {

    init {
        PluginLog.debug = pluginConfigExtension.debug
    }

    override fun visitMethod(access: Int, name: String, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
//        PluginLog.info("开始访问方法： name = $name, access = ${AccessCodeUtils.accessCode2String(access)}, descriptor = $descriptor")
        var methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions)

        // 删除 Android Log
        if (pluginConfigExtension.deleteAndroidLogMethod.enable) {
            methodVisitor =
                DeleteAndroidLogInterceptor(className, methodVisitor, access, name, descriptor, pluginConfigExtension.deleteAndroidLogMethod)
        }

        // 定位耗时方法
        if (pluginConfigExtension.timingMethod.enable && !className.startsWith("cn/ilpanda/arch/debug/apm")) {
            methodVisitor = TimingMethodInterceptor(className, methodVisitor, access, name, descriptor, pluginConfigExtension.timingMethod)
        }

        // 为 Android Log 添加行号
        if (pluginConfigExtension.lineLogMethod.enable && !className.startsWith("cn/ilpanda/arch/debug/apm")) {
            methodVisitor = LineLogMethodInterceptor(className, methodVisitor, access, name, descriptor, pluginConfigExtension.lineLogMethod)
        }


        return methodVisitor
    }
}
