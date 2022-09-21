package cn.ilpanda.arch.extension

import cn.ilpanda.arch.java.base.JsonUtils
import cn.ilpanda.arch.log.TMLog
import com.google.gson.GsonBuilder
import okio.buffer
import okio.source
import java.io.File

/**
 * 读取 Json 文件
 */
fun <T> File.fromJson(clz: Class<T>): T? {
    try {
        val string = this.source().buffer().use {
            it.readUtf8()
        }
        return JsonUtils.fromJson(string, clz)
    } catch (e: Exception) {
        TMLog.e("JsonUtils", e.stackTraceToString())
    }
    return null
}

/**
 * 读取 Json 文件
 */
inline fun <reified T : Any> File.fromJson(): T? {
    try {
        return this.fromJson(T::class.java)
    } catch (e: Exception) {
        TMLog.e("JsonUtils", e.stackTraceToString())
    }
    return null
}

inline fun <reified T : Any> String.fromJson(): T? {
    return JsonUtils.fromJson(this, T::class.java)
}

inline fun <reified T : Any> String.fromArray(): List<T>? {
    return JsonUtils.fromArray(this, T::class.java)
}

fun Any.toJson(): String? {
    return JsonUtils.toJson(this)
}

fun Any.toPrettyJson(): String? {
    val gson = GsonBuilder().setPrettyPrinting().create()
    try {
        return gson.toJson(this)
    } catch (e: Exception) {
        TMLog.e("JsonUtils", e.stackTraceToString())
    }
    return null
}
