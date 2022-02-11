package com.ilpanda.arch.common.kotlin

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaMetadataRetriever.METADATA_KEY_DURATION
import android.net.Uri
import java.io.File

data class BaseVideoInfo(
    var width: Int = 0,     // 视频宽
    var height: Int = 0,    // 视频高
    var duration: Long = 0, // 视频时长
    var path: String = "",  // 视频路径
    var rotation: Int = 0,  // 视频旋转角度
    var bitRate: Int = 0,   // 码率
    var size: Long = 0,     // 文件大小
)

val File.uri get() = this.absolutePath.asUri()

fun File.toBaseVideoInfo(context: Context): BaseVideoInfo {
    if (!exists()) return BaseVideoInfo()
    return MediaMetadataRetriever().use {
        it.setDataSource(context, Uri.fromFile(this))
        extractBaseVideoInfo(it).also { info ->
            info.path = absolutePath
            info.size = length()
        }
    }
}

fun Uri.toBaseVideoInfo(context: Context): BaseVideoInfo = MediaMetadataRetriever().use {
    val filePath = this.path
    it.setDataSource(context, this)
    return extractBaseVideoInfo(it).apply {
        path = filePath!!
    }
}

private fun extractBaseVideoInfo(retriever: MediaMetadataRetriever): BaseVideoInfo {
    val duration = retriever.extractMetadata(METADATA_KEY_DURATION)?.toLongOrNull() ?: 0
    val width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toIntOrNull() ?: 0
    val height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toIntOrNull() ?: 0
    val rotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)?.toIntOrNull() ?: 0
    val bitRate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)?.toIntOrNull() ?: 0
    return BaseVideoInfo(width, height, duration, "", rotation, bitRate)
}

inline fun <R> MediaMetadataRetriever.use(block: (MediaMetadataRetriever) -> R): R {
    return block(this).also {
        release()
    }
}

fun String?.asUri(): Uri? {
    try {
        return Uri.parse(this)
    } catch (e: Exception) {
    }
    return null
}