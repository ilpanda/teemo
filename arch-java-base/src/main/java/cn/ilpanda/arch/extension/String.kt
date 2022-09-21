package cn.ilpanda.arch.extension

import okio.buffer
import okio.source
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import kotlin.system.exitProcess

fun String.multiLine() = split("\\r?\\n|\\r".toRegex())

fun Throwable.toStackTrace(): String {
    val result: Writer = StringWriter()
    val printWriter = PrintWriter(result)
    val cause: Throwable = this
    cause.printStackTrace(printWriter)
    val stackTrace = result.toString()
    printWriter.close()
    return stackTrace.trim { it <= ' ' }
}

fun String.exec(ignoreError: Boolean = false): String {
    return Runtime.getRuntime().exec(arrayOf("/bin/sh", "-c", this)).let { it ->

        val buffer = it.inputStream.source().buffer()
        var line: String?
        var result: String? = null
        while (buffer.readUtf8Line().also { line = it } != null) {
            if (result == null) {
                result = line
            } else {
                result += "\n" + line
            }
        }
        result?.let {
            return result
        }

        val errorOutput = it.errorStream.source().buffer().readUtf8()
        if (errorOutput.isNotEmpty()) {
            if (!ignoreError) {
                println(errorOutput)
                exitProcess(1)
            }
        }
        errorOutput
    }
}