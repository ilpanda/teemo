package com.ilpanda.plugin.mv

import com.ilpanda.plugin.extesion.BasePluginExtension
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class LineLogMethodInterceptor(
    className: String,
    methodVisitor: MethodVisitor,
    access: Int,
    name: String?,
    descriptor: String?,
    basePluginExtension: BasePluginExtension
) : BaseMethodInterceptor(className, methodVisitor, access, name, descriptor, basePluginExtension) {

    private var lineNumber = 0

    private val lineLogClassName = "cn/ilpanda/arch/debug/apm/linelog/LineNumberLog"

    override fun visitLineNumber(line: Int, start: Label) {
        lineNumber = line
        super.visitLineNumber(line, start)
    }

    override fun visitMethodInsn(opcode: Int, owner: String, name: String, desc: String, itf: Boolean) {
        if ("android/util/Log" == owner) {
            val linenumberConst = "$className: ${lineNumber}"
            if ("i" == name) {
                if ("(Ljava/lang/String;Ljava/lang/String;)I" == desc) {
                    mv.visitLdcInsn(linenumberConst)
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        lineLogClassName,
                        "i",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I",
                        false)
                } else if ("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I" == desc) {
                    mv.visitLdcInsn(linenumberConst)
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        lineLogClassName,
                        "i",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)I",
                        false)
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf)
                }
            } else if ("d" == name) {
                if ("(Ljava/lang/String;Ljava/lang/String;)I" == desc) {
                    mv.visitLdcInsn(linenumberConst)
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        lineLogClassName,
                        "d",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I",
                        false)
                } else if ("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I" == desc) {
                    mv.visitLdcInsn(linenumberConst)
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        lineLogClassName,
                        "d",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)I",
                        false)
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf)
                }
            } else if ("v" == name) {
                if ("(Ljava/lang/String;Ljava/lang/String;)I" == desc) {
                    mv.visitLdcInsn(linenumberConst)
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        lineLogClassName,
                        "v",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I",
                        false)
                } else if ("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I" == desc) {
                    mv.visitLdcInsn(linenumberConst)
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        lineLogClassName,
                        "v",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)I",
                        false)
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf)
                }
            } else if ("e" == name) {
                if ("(Ljava/lang/String;Ljava/lang/String;)I" == desc) {
                    mv.visitLdcInsn(linenumberConst)
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        lineLogClassName,
                        "e",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I",
                        false)
                } else if ("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I" == desc) {
                    mv.visitLdcInsn(linenumberConst)
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        lineLogClassName,
                        "e",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)I",
                        false)
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf)
                }
            } else if ("w" == name) {
                if ("(Ljava/lang/String;Ljava/lang/String;)I" == desc) {
                    mv.visitLdcInsn(linenumberConst)
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        lineLogClassName,
                        "w",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I",
                        false)
                } else if ("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I" == desc) {
                    mv.visitLdcInsn(linenumberConst)
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        lineLogClassName,
                        "w",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)I",
                        false)
                } else if ("(Ljava/lang/String;Ljava/lang/Throwable;)I" == desc) {
                    mv.visitLdcInsn(linenumberConst)
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        lineLogClassName,
                        "w",
                        "(Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)I",
                        false)
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf)
                }
            } else if ("println" == name) {
                if ("(ILjava/lang/String;Ljava/lang/String;)I" == desc) {
                    mv.visitLdcInsn(linenumberConst)
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        lineLogClassName,
                        "println",
                        "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)I",
                        false)
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf)
                }
            } else {
                super.visitMethodInsn(opcode, owner, name, desc, itf)
            }
        } else {
            super.visitMethodInsn(opcode, owner, name, desc, itf)
        }
    }

}