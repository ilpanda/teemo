package com.ilpanda.plugin.mv

import com.ilpanda.plugin.extesion.BasePluginExtension
import com.ilpanda.plugin.utils.Constants
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter

open class BaseMethodInterceptor(
    val className: String,
    methodVisitor: MethodVisitor,
    access: Int,
    name: String?,
    descriptor: String?,
    val basePluginExtension: BasePluginExtension
) : AdviceAdapter(Constants.ASM_VERSION, methodVisitor, access, name, descriptor) {

    /**
     * 是否能够处理该 class 文件
     * @return true：可以处理 false：不能处理
     */
    fun canHandle(): Boolean {

        if (!basePluginExtension.enable) return false

        // 白名单
        if (basePluginExtension.whiteList.isNotEmpty()) {
            basePluginExtension.whiteList.forEach {
                if (className.startsWith(it)) {
                    return true
                }
            }
            return false
        }

        // 黑名单
        if (basePluginExtension.blackList.isNotEmpty()) {
            basePluginExtension.blackList.forEach {
                if (className.startsWith(it)) {
                    return false
                }
            }
            return true
        }

        return true
    }

}
