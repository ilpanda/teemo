package cn.ilpanda.arch.log

import cn.ilpanda.arch.extension.toStackTrace
import cn.ilpanda.arch.log.TMLog.TMLogDelegate

class TMLogDefaultDelegate : TMLogDelegate {

    override fun e(tag: String, msg: String, vararg obj: Any) {
        println(tag + " : " + String.format(msg, obj))
    }

    override fun w(tag: String, msg: String, vararg obj: Any) {
        println(tag + " : " + String.format(msg, obj))
    }

    override fun i(tag: String, msg: String, vararg obj: Any) {
        println(tag + " : " + String.format(msg, obj))
    }

    override fun d(tag: String, msg: String, vararg obj: Any) {
        println(tag + " : " + String.format(msg, obj))
    }

    override fun printErrStackTrace(
        tag: String, tr: Throwable, format: String, vararg obj: Any?
    ) {
        println(tag + " : " + String.format(format, obj) + "\n ${tr.toStackTrace()}")
    }

    override fun printErrStackTrace(tag: String, tr: Throwable) {
        println(tag + " : " + "\n ${tr.toStackTrace()}")
    }

}