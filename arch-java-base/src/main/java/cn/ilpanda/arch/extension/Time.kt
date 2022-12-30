package cn.ilpanda.arch.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * 格式化时间如： 2022-12-30 15:20:12
 * @param containMillisecond ： 格式化的时间中是否包含毫秒
 */
fun Date.formatTime(containMillisecond: Boolean = false): String {
    return time.formatTime(containMillisecond)
}

/**
 * 格式化时间，单位为毫秒
 */
fun Long.formatTime(containMillisecond: Boolean = false): String {
    val strFormat = if (containMillisecond) "yyyy-MM-dd HH:mm:ss:SSS" else "yyyy-MM-dd HH:mm:ss"
    return SimpleDateFormat(strFormat, Locale.getDefault()).format(this)
}