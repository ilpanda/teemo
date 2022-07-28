package com.ilpanda.arch.common.kotlin

import android.R
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat


fun Drawable.warpTint(@ColorInt color: Int): Drawable {
    val newDrawable = DrawableCompat.wrap(this)
    DrawableCompat.setTint(newDrawable, color)
    return newDrawable
}

/**
 * 对drawable进行各种状态颜色刷色
 *
 * @param normalColor  默认颜色
 * @param selectedColor  高亮颜色
 * @return drawable
 */
private fun Drawable.tintDrawableWithStateListColor(
    @ColorInt normalColor: Int,
    @ColorInt selectedColor: Int
): Drawable? {
    val newDrawable = DrawableCompat.wrap(this)
    DrawableCompat.setTintList(newDrawable, getColorStateList(normalColor, selectedColor))
    return newDrawable
}

/**
 * 定义的带圆角的样式
 *
 * @param color         描边颜色
 * @param radii         边缘弧度 (4个角 依次为：左上、右上、右下、左下)
 * @param strokeWidth   描边
 * @return GradientDrawable
 */
fun getGradientDrawable(color: Int, radii: FloatArray, strokeWidth: Int): GradientDrawable {
    val drawable = GradientDrawable()
    drawable.cornerRadii = radii
    drawable.setStroke(strokeWidth, color)
    return drawable
}

/**
 *
 */
fun getColorStateList(@ColorInt normalColor: Int, @ColorInt selectedColor: Int): ColorStateList {
    val colors = intArrayOf(selectedColor, selectedColor, selectedColor, normalColor)
    val states = arrayOfNulls<IntArray>(4)
    states[0] = intArrayOf(R.attr.state_focused)
    states[1] = intArrayOf(R.attr.state_pressed)
    states[2] = intArrayOf(R.attr.state_selected)
    states[3] = intArrayOf()
    return ColorStateList(states, colors)
}

