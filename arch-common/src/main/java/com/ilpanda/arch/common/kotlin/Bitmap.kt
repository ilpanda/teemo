package com.ilpanda.arch.common.kotlin

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream

fun Bitmap.save(dst: String, quality: Int) {
    val src: Bitmap = this
    ByteArrayOutputStream().use { outputStream ->
        src.let {
            if (it.config != Bitmap.Config.ARGB_8888) {
                val resultBitmap = it.copy(Bitmap.Config.ARGB_8888, true)
                it.recycle()
                resultBitmap
            } else {
                it
            }
        }.also { bitmap ->
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream)
            bitmap.recycle()
        }

        FileOutputStream(dst).use {
            it.write(outputStream.toByteArray())
        }
    }
}