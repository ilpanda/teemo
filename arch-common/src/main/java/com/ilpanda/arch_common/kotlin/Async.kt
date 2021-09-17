package com.ilpanda.arch_common.kotlin

import android.app.Activity
import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future


class AsyncContext<T>(var weakReference: WeakReference<T>)


/**
 * 异步执行任务
 */
fun <T> T.doAsync(crashBlock: ((Throwable) -> Unit)? = null, executorService: ExecutorService = BackgroundExecutor.executor, block: AsyncContext<T>.() -> Unit): Future<Unit> {
    val asyncContext = AsyncContext(WeakReference(this));
    return executorService.submit<Unit> {
        try {
            asyncContext.block()
        } catch (thr: Throwable) {
            crashBlock?.invoke(thr)
        }
    }
}

/**
 * 异步执行任务并返回结果
 */
fun <T, R> T.doAsyncWithResult(crashBlock: ((Throwable) -> Unit)? = null,
                               executorService: ExecutorService = BackgroundExecutor.executor,
                               block: AsyncContext<T>.() -> R): Future<R> {
    val asyncContext = AsyncContext(WeakReference(this));
    return executorService.submit<R> {
        try {
            asyncContext.block()
        } catch (thr: Throwable) {
            crashBlock?.invoke(thr)
            throw  thr
        }
    }
}


/**
 * 切换到主线程执行
 */
fun <T> AsyncContext<T>.onComplete(f: (T?) -> Unit) {
    val ref = weakReference.get()
    if (Looper.getMainLooper() === Looper.myLooper()) {
        f(ref)
    } else {
        ContextHelper.handler.post { f(ref) }
    }
}


/**
 * 如果 Activity 没有被销毁, 切换到主线程执行
 */
fun <T : Activity> AsyncContext<T>.activityUiThread(f: (T) -> Unit): Boolean {
    val activity = weakReference.get() ?: return false
    if (activity.isFinishing) return false
    activity.runOnUiThread { f(activity) }
    return true
}



private object ContextHelper {
    val handler = Handler(Looper.getMainLooper())
}


private object BackgroundExecutor {
    val executor: ExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    fun <T> submit(task: () -> T): Future<T> = executor.submit(task)
}