package com.ilpanda.arch_common.kotlin

import android.graphics.Color
import androidx.annotation.ColorInt
import java.util.*


/**
 * 生成随机颜色，界面调试时可使用
 */
@ColorInt
fun Random.nextColor(): Int {
    var rnd = Random()
    var color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    return color
}


