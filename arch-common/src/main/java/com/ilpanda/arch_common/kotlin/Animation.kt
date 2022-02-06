package com.ilpanda.arch_common.kotlin

import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.view.animation.TranslateAnimation

/**
 * 底部退出动画
 */
fun View.animTransBottomOut(duration: Long = 500,
    interpolator: Interpolator = DecelerateInterpolator(),
    fillAfter: Boolean = true,
    onAnimationEnd: View.() -> Unit = {}
) {
    val animation = TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f
    )
    animation.duration = duration
    animation.interpolator = interpolator
    animation.fillAfter = fillAfter
    animation.doOnAnimation(onAnimationEnd = {
        onAnimationEnd()
    })
    startAnimation(animation)
}

/**
 * 底部进入动画
 */
fun View.animTransBottomIn(duration: Long = 500,
    interpolator: Interpolator = DecelerateInterpolator(),
    fillAfter: Boolean = true,
    onAnimationEnd: View.() -> Unit = {}
) {
    val animation = TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f
    )
    animation.duration = duration
    animation.interpolator = interpolator
    animation.fillAfter = fillAfter
    animation.doOnAnimation(onAnimationEnd = {
        onAnimationEnd()
    })
    startAnimation(animation)
}

/**
 * 简化动画的回调使用
 */
fun Animation.doOnAnimation(onAnimationStart: () -> Unit = {}, onAnimationEnd: () -> Unit = {}, onAnimationRepeat: () -> Unit = {}) {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {
            onAnimationRepeat()
        }

        override fun onAnimationEnd(animation: Animation?) {
            onAnimationEnd()
        }

        override fun onAnimationStart(animation: Animation?) {
            onAnimationStart()
        }
    })
}
