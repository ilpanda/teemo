package com.ilpanda.arch_common.kotlin

import android.view.View


/**
 * View 的点击事件
 */
class SafeClickListener(private val interval: Long, val safeClick: (View) -> Unit) : View.OnClickListener {
    var lastClickTime: Long = 0;

    override fun onClick(v: View?) {
        if ((System.currentTimeMillis() - lastClickTime) < interval) {
            return
        }
        lastClickTime = System.currentTimeMillis();
        v?.let { safeClick(it) };
    }
}


fun View.setSafeOnClick(interval: Long = 1000, safeClick: (View) -> Unit) {
    setOnClickListener(SafeClickListener(interval, safeClick))
}


/**
 * View 的显示、隐藏
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.remove() {
    this.visibility = View.GONE
}

