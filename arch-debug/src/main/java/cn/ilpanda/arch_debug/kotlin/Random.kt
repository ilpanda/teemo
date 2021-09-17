package cn.ilpanda.arch_debug.kotlin

import android.graphics.Color
import java.util.*


/**
 * 生成随机颜色
 */
fun Random.nextColor(): Int {
    var rnd = Random()
    var color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    return color
}


