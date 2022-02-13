package com.ilpanda.arch.common.kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


/**
 * @param dst 保存路径
 * @param quality 压缩质量，100 表示不压缩，数字越小，压缩的图片越小，图片质量越差
 */
fun Bitmap.save(dst: String, quality: Int) {
    save(File(dst), quality)
}

/**
 * @param dst 保存路径
 * @param quality 压缩质量，100 表示不压缩，数字越小，压缩的图片越小，图片质量越差
 */
fun Bitmap.save(dst: File, quality: Int) {
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
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            bitmap.recycle()
        }

        FileOutputStream(dst).use {
            it.write(outputStream.toByteArray())
        }
    }
}

/**
 * 将本地图片经过压缩后，加载到本地
 * @param reqWidth 目标图片宽度
 * @param reqHeight 目标图片高度
 * @return 压缩后的图片
 */
fun File.compressImageKindly(reqWidth: Int, reqHeight: Int): Bitmap {
    val newOpts = BitmapFactory.Options()
    newOpts.inJustDecodeBounds = true
    BitmapFactory.decodeFile(this.absolutePath, newOpts)
    calculateInSampleSize(reqWidth, reqHeight, newOpts, true)
    return BitmapFactory.decodeFile(this.absolutePath, newOpts)
}

private fun calculateInSampleSize(
    reqWidth: Int,
    reqHeight: Int,
    options: BitmapFactory.Options,
    centerInside: Boolean) {
    calculateInSampleSize(reqWidth, reqHeight, options.outWidth, options.outHeight, options,
        centerInside)
}


private fun calculateInSampleSize(
    reqWidth: Int,
    reqHeight: Int,
    width: Int,
    height: Int,
    options: BitmapFactory.Options,
    centerInside: Boolean) {
    var sampleSize = 1
    if (height > reqHeight || width > reqWidth) {
        val heightRatio: Int
        val widthRatio: Int
        if (reqHeight == 0) {
            sampleSize = Math.floor((width.toFloat() / reqWidth.toFloat()).toDouble()).toInt()
        } else if (reqWidth == 0) {
            sampleSize = Math.floor((height.toFloat() / reqHeight.toFloat()).toDouble()).toInt()
        } else {
            heightRatio = Math.floor((height.toFloat() / reqHeight.toFloat()).toDouble()).toInt()
            widthRatio = Math.floor((width.toFloat() / reqWidth.toFloat()).toDouble()).toInt()
            sampleSize = if (centerInside) Math.max(heightRatio, widthRatio) else Math.min(heightRatio, widthRatio)
        }
    }
    options.inSampleSize = sampleSize
    options.inJustDecodeBounds = false
}
