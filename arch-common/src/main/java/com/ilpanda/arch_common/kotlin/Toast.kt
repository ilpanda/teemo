package com.ilpanda.arch_common.kotlin


import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.os.Build.VERSION.SDK_INT
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.ilpanda.myadnroid.arch_common.ILog
import kotlin.LazyThreadSafetyMode.NONE

@PublishedApi
internal fun Context.createToast(text: CharSequence, duration: Int): Toast {
    val ctx = if (SDK_INT == 25) SafeToastCtx(this) else this
    return Toast.makeText(ctx, text, duration)
}

@PublishedApi
internal fun Context.createToast(resId: Int, duration: Int): Toast {
    val ctx = if (SDK_INT == 25) SafeToastCtx(this) else this
    return Toast.makeText(ctx, this.resources.getText(resId), duration)
}

// toast
inline fun Context.toast(msg: CharSequence) = createToast(msg, Toast.LENGTH_SHORT).show()
inline fun Context.toast(@StringRes msgResId: Int) = createToast(msgResId, Toast.LENGTH_SHORT).show()
inline fun View.toast(msg: CharSequence) = context.toast(msg)
inline fun View.toast(@StringRes msgResId: Int) = context.toast(msgResId)
inline fun Fragment.toast(msg: CharSequence) = activity?.toast(msg)
inline fun Fragment.toast(@StringRes msgResId: Int) = activity?.toast(msgResId)

// long toast
inline fun Context.longToast(msg: CharSequence) = createToast(msg, Toast.LENGTH_LONG).show()
inline fun Context.longToast(@StringRes resId: Int) = createToast(resId, Toast.LENGTH_LONG).show()
inline fun View.longToast(msg: CharSequence) = context.longToast(msg)
inline fun View.longToast(@StringRes msgResId: Int) = context.longToast(msgResId)
inline fun Fragment.longToast(msg: CharSequence) = activity?.longToast(msg)
inline fun Fragment.longToast(@StringRes msgResId: Int) = activity?.longToast(msgResId)

/**
 * Avoids [WindowManager.BadTokenException] on API 25.
 */
private class SafeToastCtx(ctx: Context) : ContextWrapper(ctx) {

    private val toastWindowManager by lazy(NONE) { ToastWindowManager(baseContext.getSystemService(WINDOW_SERVICE) as WindowManager) }

    private val toastLayoutInflater by lazy(NONE) {
        (baseContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).cloneInContext(this)
    }

    override fun getApplicationContext(): Context = SafeToastCtx(baseContext.applicationContext)

    override fun getSystemService(name: String): Any = when (name) {
        Context.LAYOUT_INFLATER_SERVICE -> toastLayoutInflater
        Context.WINDOW_SERVICE -> toastWindowManager
        else -> super.getSystemService(name)
    }

    private class ToastWindowManager(private val base: WindowManager) : WindowManager by base {

        @SuppressLint("LogNotTimber") // Timber is not a dependency here, but lint passes through.
        override fun addView(view: View?, params: ViewGroup.LayoutParams?) {
            try {
                base.addView(view, params)
            } catch (e: WindowManager.BadTokenException) {
                ILog.e("SafeToast", "Couldn't add Toast to WindowManager", e)
            }
        }
    }
}
