package com.ilpanda.arch.common.kotlin

import android.content.Context
import android.content.res.AssetManager
import cn.ilpanda.arch.java.base.CrashUtil
import cn.ilpanda.arch.java.base.TMLog
import okio.buffer
import okio.sink
import okio.source
import java.io.File
import java.io.IOException


/**
 * 读取 assets 目录文件
 */
fun Context.readAssetsFile(src: String): String {
    return this.assets.open(src).source().buffer().use {
        it.readUtf8()
    }
}

/**
 * 将 assets 目录文件复制到指定路径
 * @param src assets 目录下文件夹路径，如 assets/resource 文件夹，传入 resource 目录即可。
 */
fun Context.copyAssetsFile(src: String, dst: String): Boolean {
    return copyAssetsFile(this.assets, src, dst)
}

/**
 * 将 assets 目录下的文件夹复制到指定路径
 */
fun Context.copyAssetsDir(src: String, dst: String) {
    copyAssetsDir(this.assets, src, dst)
}

/**
 *  递归拷贝 Asset 目录中的文件到 rootDir 中
 */
@Throws(IOException::class)
fun copyAssetsDir(assets: AssetManager, srcDir: String, rootDir: String) {
    if (isAssetsDir(assets, srcDir)) {
        val dir = File(rootDir + File.separator + srcDir)
        check(!(!dir.exists() && !dir.mkdirs())) { "mkdir failed" }
        for (s in assets.list(srcDir)!!) {
            copyAssetsDir(assets, "$srcDir/$s", rootDir)
        }
    } else {
        copyAssetsFile(assets, srcDir, File(rootDir, srcDir).absolutePath)
    }
}

/**
 * 判断是否存在 assets 资源子目录，如 assets/filter 目录。
 */
fun isAssetsDir(assets: AssetManager, path: String): Boolean {
    try {
        val files = assets.list(path)
        return files != null && files.isNotEmpty()
    } catch (e: IOException) {
        TMLog.e("isAssetsDir", CrashUtil.getThreadStack(e))
    }
    return false
}

/**
 * 复制 assets 目录下文件到指定路径
 * @param src assets 目录下文件路径，可以为相对路径。
 * @param dst 指定文件路径
 */
fun copyAssetsFile(assets: AssetManager, src: String, dst: String): Boolean {
    try {
        assets.open(src).source().buffer().use { bufferedSource ->
            File(dst).sink().buffer().use {
                it.writeAll(bufferedSource)
            }
        }
        return true
    } catch (e: Exception) {
        TMLog.e("copyAssetsFile", CrashUtil.getThreadStack(e))
    }
    return false
}