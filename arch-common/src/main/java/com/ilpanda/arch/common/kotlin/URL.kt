package com.ilpanda.arch.common.kotlin

import android.webkit.URLUtil
import cn.ilpanda.arch.extension.md5


fun String.fileName(): String {
    var fileName = try {
        URLUtil.guessFileName(this, null, null)
    } catch (e: Exception) {
        this.md5() ?: "unkonwn"
    }
    return fileName
}
