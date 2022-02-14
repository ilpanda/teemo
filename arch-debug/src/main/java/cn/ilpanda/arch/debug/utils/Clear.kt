package cn.ilpanda.arch.debug.utils

import android.content.Context
import android.os.Environment
import java.io.File

/**
 * 删除 App 数据
 * /data/data/Android/<packageName>
 * /sdcard/Android/data/<packageName>
 */
fun Context.clearApplicationData() {
    getDataDir(this).deleteRecursively()
    getExternalDataDir(this)?.deleteRecursively()
}

private fun getDataDir(context: Context): File {
    return File(File.separator + "data" + File.separator + "data" + File.separator + context.packageName)
}

private fun getExternalDataDir(context: Context): File? {
    var file: File? = null
    if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
        file = context.externalCacheDir
    }
    return file?.parentFile
}
