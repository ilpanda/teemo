package com.ilpanda.plugin

import com.ilpanda.plugin.utils.AccessCodeUtils
import com.ilpanda.plugin.utils.Constants
import com.ilpanda.plugin.utils.PluginLog
import org.objectweb.asm.ClassVisitor

open class BaseClassInterceptor(classVisitor: ClassVisitor?) : ClassVisitor(Constants.ASM_VERSION, classVisitor) {
    protected var className: String = ""
    protected var signature: String? = ""
    protected var superName: String? = ""
    protected var interfaces: Array<out String>? = null

    override fun visit(version: Int, access: Int, name: String, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
//        PluginLog.info("开始访问【类】，name = $name, superName = $superName, version = $version, access = ${AccessCodeUtils.accessCode2String(access)}")
        this.className = name
        this.signature = signature
        this.superName = superName
        this.interfaces = interfaces
    }

    override fun visitEnd() {
        super.visitEnd()
//        PluginLog.info("结束访问类")
    }

}
