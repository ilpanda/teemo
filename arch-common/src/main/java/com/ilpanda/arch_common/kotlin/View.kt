package com.ilpanda.arch_common.kotlin

import android.view.View
import android.view.ViewTreeObserver


class SafeClickListener(private val interval: Long, private val safeClick: View.OnClickListener) : View.OnClickListener {
    private var lastClickTime: Long = 0;

    override fun onClick(v: View?) {
        if ((System.currentTimeMillis() - lastClickTime) <= interval) {
            return
        }
        lastClickTime = System.currentTimeMillis();
        v?.let { safeClick.onClick(v) };
    }
}


/**
 * View 防抖动点击
 * @param interval 点击时间间隔，单位为毫秒，如 600 表示 600 毫秒的多次点击，将视为一次点击
 */
fun View.setSafeOnClick(interval: Long = 600, safeClick: View.OnClickListener) {
    setOnClickListener(SafeClickListener(interval, safeClick))
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

/**
 * 获取 View 的宽、高
 */
fun View.doAddOnGlobalLayoutListener(block: (width: Int, height: Int) -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            block(width, height)
        }
    })
}








