package com.ilpanda.arch_common.kotlin

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import androidx.fragment.app.Fragment
import com.ilpanda.arch_common.java.utils.ScreenUtil


fun Context.dipToPx(value: Int): Int = ScreenUtil.dipToPx(this, value)
fun Context.dipToPx(value: Float): Int = ScreenUtil.dipToPx(this, value.toInt())
fun Context.pxToDip(value: Int): Int = ScreenUtil.pxToDip(this, value.toFloat())
fun Context.pxToDip(value: Float): Int = ScreenUtil.pxToDip(this, value)
fun Context.spToPx(value: Int): Int = ScreenUtil.spToPx(this, value.toFloat())
fun Context.spToPx(value: Float): Int = ScreenUtil.spToPx(this, value)

// App 可用宽度
fun Context.screenWidth(): Int = ScreenUtil.getScreenWidth(this)

// App 可用高度
fun Context.screenHeight(): Int = ScreenUtil.getScreenHeight(this)

// 屏幕真实宽度
fun Context.realScreenWidth(): Int = ScreenUtil.getRealScreenWidth(this)

// 屏幕真实高度
fun Context.realScreenHeight(): Int = ScreenUtil.getScreenHeight(this)

// 状态栏高度
fun Context.statusBarHeight(): Int = ScreenUtil.getStatusBarHeight(this)

// the same for the views
inline fun View.dipToPx(value: Int): Int = context.dipToPx(value)
inline fun View.dipToPx(value: Float): Int = context.dipToPx(value)
inline fun View.pxToDip(value: Float): Int = context.pxToDip(value)
inline fun View.pxToDip(value: Int): Int = context.pxToDip(value)
inline fun View.spToPx(value: Int): Int = context.spToPx(value)
inline fun View.spToPx(value: Float): Int = context.spToPx(value)
inline fun View.screenWidth(): Int = context.screenWidth()
inline fun View.screenHeight(): Int = context.screenHeight()
inline fun View.realScreenWidth(): Int = context.realScreenWidth()
inline fun View.realScreenHeight(): Int = context.realScreenHeight()
inline fun View.statusBarHeight(): Int = context.statusBarHeight()

// must be called after attached.
fun Fragment.dipToPx(value: Int): Int = context!!.dipToPx(value)
fun Fragment.dipToPx(value: Float): Int = context!!.dipToPx(value)
fun Fragment.spToPx(value: Int): Int = context!!.spToPx(value)
fun Fragment.spToPx(value: Float): Int = context!!.spToPx(value)
fun Fragment.pxToDip(px: Int): Int = context!!.pxToDip(px)
fun Fragment.pxToDip(px: Float): Int = context!!.pxToDip(px)
fun Fragment.screenWidth(): Int = context!!.screenWidth()
fun Fragment.screenHeight(): Int = context!!.screenHeight()
fun Fragment.realScreenWidth(): Int = context!!.realScreenWidth()
fun Fragment.realScreenHeight(): Int = context!!.realScreenHeight()
fun Fragment.statusBarHeight(): Int = context!!.statusBarHeight()


// Int、Float、Double dp
inline val Int.dp: Int get() = toFloat().dp
inline val Long.dp: Int get() = toFloat().dp
inline val Double.dp: Int get() = toFloat().dp
inline val Float.dp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics).toInt()

// Int、Float、Double sp
inline val Int.sp: Float get() = toFloat().sp
inline val Long.sp: Float get() = toFloat().sp
inline val Double.sp: Float get() = toFloat().sp
inline val Float.sp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)
