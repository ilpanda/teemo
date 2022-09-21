package cn.ilpanda.arch.extension

import okio.buffer
import okio.source
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import kotlin.system.exitProcess

/**
 * 根据换行符对字符串进行分割等价于 String.lines()
 * \n： Unix、Linux 和 macOS。
 * \r\n：Windows 。
 * \r: macOS 9 以及早期版本。
 */
fun String.multiLine() = split("\\r?\\n|\\r".toRegex())

/**
 * 将 Throwable 的堆栈转换为 String
 */
fun Throwable.toStackTrace(): String {
    val result: Writer = StringWriter()
    val printWriter = PrintWriter(result)
    val cause: Throwable = this
    cause.printStackTrace(printWriter)
    val stackTrace = result.toString()
    printWriter.close()
    return stackTrace.trim { it <= ' ' }
}

/**
 * 移除字符串末尾的文件扩展命名，如：foo.txt -> foo
 */
fun String.removeExtension(): String {
    val lastIndex = this.lastIndexOf('.')
    if (lastIndex != -1) {
        return this.substring(0, lastIndex)
    }
    return this
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