package com.ilpanda.arch.common.kotlin

import android.media.ExifInterface
import cn.ilpanda.arch.java.base.TMLog
import okio.buffer
import okio.source
import java.io.File
import java.io.IOException


private const val EXIF_MAGIC_NUMBER = 0xFFD8
private const val GIF_HEADER = 0x474946
private const val PNG_HEADER = -0x76afb1b9
private const val RIFF_HEADER = 0x52494646
private const val WEBP_HEADER = 0x57454250

/**
 * 根据图片文件头，获取图片格式类型
 */
@Throws(IOException::class)
fun File.imageType(): ImageType {
    if (!exists()) return ImageType.UNKNOWN

    var imageType = ImageType.UNKNOWN
    this.source().buffer().use { bufferedSource ->
        // 前 2 个字节
        val firstTwoBytes: Short = bufferedSource.readShort()
        if (firstTwoBytes == EXIF_MAGIC_NUMBER.toShort()) {
            imageType = ImageType.JPEG
            return imageType
        }
        // 前 3 个字节
        val firstThreeBytes: Int = firstTwoBytes.toInt() shl 8 or bufferedSource.readByte().toInt()
        if (firstThreeBytes == GIF_HEADER) {
            imageType = ImageType.GIF
            return imageType
        }

        // 前 4 个字节
        val firstFourBytes = firstThreeBytes shl 8 or bufferedSource.readByte().toInt()
        if (firstFourBytes == PNG_HEADER) {
            bufferedSource.skip(25 - 4)
            try {
                val alpha: Int = bufferedSource.readByte().toInt()
                // A RGB indexed PNG can also have transparency. Better safe than sorry!
                imageType = if (alpha >= 3) ImageType.PNG_A else ImageType.PNG
                return imageType
            } catch (e: IOException) {
                // TODO(b/143917798): Re-enable this logging when dependent tests are fixed.
                // if (Log.isLoggable(TAG, Log.ERROR)) {
                //   Log.e(TAG, "Unexpected EOF, assuming no alpha", e);
                // }
                imageType = ImageType.PNG
                return imageType
            }
        }

        // 前 4 个字节
        if (firstFourBytes == RIFF_HEADER) {
            // 跳过 5-8 这四个字节
            bufferedSource.skip(4)
            // 读取 9-12 这四个字节
            val thirdFourInt: Int = bufferedSource.readInt()
            if (thirdFourInt == WEBP_HEADER) {
                imageType = ImageType.WEBP
                return imageType
            }
        }
    }
    return imageType
}

/**
 * 是否为图片类型
 */
fun File.isImage(): Boolean {
    return imageType() != ImageType.UNKNOWN
}

private const val DEGREE_90 = 90
private const val DEGREE_180 = 180
private const val DEGREE_270 = 270

/**
 * 获取图片的旋转角度
 */
fun File.imageRotate(): Int {
    var degree = 0

    if (!exists()) return degree

    var exif: ExifInterface? = null
    try {
        exif = ExifInterface(this.absolutePath)
    } catch (ex: IOException) {
        TMLog.e("imageRotation", "cannot read exif $ex")
    }

    // We only recognize a subset of orientation tag values.
    degree = when (exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1) ?: -1) {

        ExifInterface.ORIENTATION_ROTATE_90 -> DEGREE_90

        ExifInterface.ORIENTATION_ROTATE_180 -> DEGREE_180

        ExifInterface.ORIENTATION_ROTATE_270 -> DEGREE_270

        else -> 0
    }

    return degree
}

enum class ImageType {
    JPEG,
    PNG,
    PNG_A,
    GIF,
    WEBP,
    UNKNOWN
}

