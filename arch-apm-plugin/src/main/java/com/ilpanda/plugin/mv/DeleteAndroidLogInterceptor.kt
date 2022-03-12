package com.ilpanda.plugin.mv

import com.ilpanda.plugin.extesion.BasePluginExtension
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


/**
 * 删除工程当中 android.util.log 方法调用
 */
class DeleteAndroidLogInterceptor(
    className: String,
    methodVisitor: MethodVisitor,
    access: Int,
    name: String?,
    descriptor: String?,
    basePluginExtension: BasePluginExtension
) : BaseMethodInterceptor(className, methodVisitor, access, name, descriptor, basePluginExtension) {

    /**
     * 在方法内部调用的地方，检测 Log 的用法，然后进行删除
     */
    override fun visitMethodInsn(opcodeAndSource: Int, owner: String?, name: String?, descriptor: String?, isInterface: Boolean) {
        if (!canHandle()) {
            super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
            return
        }
        if (Opcodes.ACC_STATIC.and(opcodeAndSource) != 0 && owner == "android/util/Log" && (name == "d" || name == "i" || name == "e" || name == "w" || name == "v")
            && descriptor == "(Ljava/lang/String;Ljava/lang/String;)I"
        ) {
            /**
             * 直接 return 只是删除了 Log 指令的调用，但是对应的当前的操作数栈没有进行处理
             */
            return
        }
        super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
    }
}
