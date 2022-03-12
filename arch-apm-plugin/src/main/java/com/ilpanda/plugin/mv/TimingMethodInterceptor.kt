package com.ilpanda.plugin.mv

import com.ilpanda.plugin.extesion.BasePluginExtension
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

class TimingMethodInterceptor(
    className: String,
    methodVisitor: MethodVisitor,
    access: Int,
    name: String,
    descriptor: String?,
    basePluginExtension: BasePluginExtension
) : BaseMethodInterceptor(className, methodVisitor, access, name, descriptor, basePluginExtension), Opcodes {

    private var startVarIndex = 0
    private val methodName: String
    private val blockMethodClassName="com/hunter/library/timing/BlockManager"

    override fun visitCode() {
        super.visitCode()
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        startVarIndex = newLocal(Type.LONG_TYPE)
        mv.visitVarInsn(Opcodes.LSTORE, startVarIndex)
    }

    override fun visitInsn(opcode: Int) {
        if (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN || opcode == Opcodes.ATHROW) {
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
            mv.visitVarInsn(Opcodes.LLOAD, startVarIndex)
            mv.visitInsn(Opcodes.LSUB)
            val index = newLocal(Type.LONG_TYPE)
            mv.visitVarInsn(Opcodes.LSTORE, index)
            mv.visitLdcInsn(methodName)
            mv.visitVarInsn(Opcodes.LLOAD, index)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, blockMethodClassName, "timingMethod", "(Ljava/lang/String;J)V", false)
        }
        super.visitInsn(opcode)
    }

    init {
        methodName = name.replace("/", ".")
    }
}