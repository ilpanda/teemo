package com.ilpanda.arch.common.kotlin

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.ViewGroupUtils

/**
 * 扩大 View 的触摸面积
 * @author https://juejin.cn/post/6968237652017414151
 * @param dx 水平方向上增加的触摸区域，单位 px
 * @param dy 数值方向上增加的触摸区域，单位 px
 */
@SuppressLint("RestrictedApi")
fun View.expand(dx: Int, dy: Int) {
    class MultiTouchDelegate(bound: Rect? = null, delegateView: View) : TouchDelegate(bound, delegateView) {
        // 可以同时扩大多个 View 的触摸面积。
        val delegateViewMap = mutableMapOf<View, Rect>()
        private var delegateView: View? = null

        // 完全重写，屏蔽父类逻辑。
        override fun onTouchEvent(event: MotionEvent): Boolean {
            val x = event.x.toInt()
            val y = event.y.toInt()
            var handled = false
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    // Down 时，找到代理控件
                    delegateView = findDelegateViewUnder(x, y)
                }
                MotionEvent.ACTION_CANCEL -> {
                    delegateView = null
                }
            }
            // 如果找到了代理控件，则将所有事件都传递给它消费
            delegateView?.let {
                event.setLocation(it.width / 2f, it.height / 2f)
                handled = it.dispatchTouchEvent(event)
            }
            return handled
        }

        private fun findDelegateViewUnder(x: Int, y: Int): View? {
            delegateViewMap.forEach { entry -> if (entry.value.contains(x, y)) return entry.key }
            return null
        }
    }

    // 获取当前控件的父控件
    val parentView = parent as? ViewGroup
    // 若父控件不是 ViewGroup, 则直接返回
    parentView ?: return

    if (parentView.touchDelegate == null) parentView.touchDelegate = MultiTouchDelegate(delegateView = this)
    post {
        val rect = Rect()
        // 获取子控件在父控件中的区域
        ViewGroupUtils.getDescendantRect(parentView, this, rect)
        // 将响应区域扩大
        rect.inset(-dx, -dy)
        // 将子控件作为代理控件添加到 MultiTouchDelegate 中
        (parentView.touchDelegate as? MultiTouchDelegate)?.delegateViewMap?.put(this, rect)
    }


}