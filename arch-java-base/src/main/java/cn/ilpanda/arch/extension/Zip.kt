package cn.ilpanda.arch.extension

import cn.ilpanda.arch.java.base.Closeables
import cn.ilpanda.arch.java.base.CrashUtil
import cn.ilpanda.arch.java.base.TMLog
import okio.buffer
import okio.sink
import okio.source
import java.io.File
import java.io.FileInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


/**
 * 解压 zip 文件
 */
fun File.unzip(dstFile: File): Boolean {
    return unzip(dstFile.absolutePath)
}

/**
 * 解压 Zip 文件，
 * dstDir: 解压后的 zip 文件所在的目录
 */
fun File.unzip(dstDir: String): Boolean {
    var inZip: ZipInputStream? = null
    try {
        inZip = ZipInputStream(FileInputStream(this))
        var zipEntry: ZipEntry? = null
        var szName = ""
        while (inZip.nextEntry.also { zipEntry = it } != null) {
            szName = zipEntry!!.name
            if (zipEntry!!.isDirectory) {
                // get the folder name of the widget
                szName = szName.substring(0, szName.length - 1)
                val folder = File(dstDir + File.separator + szName)
                folder.mkdirs()
            } else {
                val file = File(dstDir + File.separator + szName)
                if (file.parentFile?.exists() == false) {
                    file.parentFile!!.mkdirs()
                }
                file.createNewFile()
                file.sink().buffer().use { sink ->
                    inZip.source().buffer().readAll(sink)
                }
            }
        }
    } catch (e: Exception) {
        TMLog.e("unzip file failed", CrashUtil.getThreadStack(e))
        return false
    } finally {
        Closeables.closeQuietly(inZip)
    }
    return true
}

/**
 * 根据文件头判断是否为 zip 文件
 */
fun File.isZip(): Boolean {
    source().buffer().use {
        val readInt = it.readInt()
        if (readInt == 0x504B0304 || readInt == 0x504B0506 || readInt == 0x504B0708) {
            return true
        }
        return false
    }
}