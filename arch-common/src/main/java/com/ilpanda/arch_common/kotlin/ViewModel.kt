package com.ilpanda.arch_common.kotlin

import androidx.activity.ComponentActivity
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.Factory


/**
 * 通过属性代理的方式，实现 Activity 之间的数据通信
 * ViewModel 不要持有 Activity 或 View 对象，以防内存泄漏
 */
inline fun <reified VM : ViewModel> ComponentActivity.activityBusViewModel(
    noinline scopeName: () -> String,
    noinline factoryProducer: (() -> Factory)? = null
): Lazy<VM> {

    val factoryPromise = factoryProducer ?: { defaultViewModelProviderFactory }
    return ActivityBusViewModelLazy(this, scopeName, VM::class.java, factoryPromise)
}

class ActivityBusViewModelLazy<T : ViewModel>(var activity: ComponentActivity,
    var scopeName: () -> String,
    var viewModelClass: Class<T>,
    val factoryProducer: () -> Factory
) : Lazy<T> {

    private var viewModel: ViewModel? = null

    override val value: T
        get() {
            if (viewModel != null) return (viewModel as T?)!!
            var vmStore = commonViewStore(scopeName(), activity)

            val factory = factoryProducer()
            viewModel = ViewModelProvider(vmStore, factory).get(viewModelClass)
            return (viewModel as T)
        }

    override fun isInitialized(): Boolean {
        return viewModel != null
    }
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class VMScope(val scopeName: String) {

}

private var cache: HashMap<String, ActivityBusViewModelOwner> = hashMapOf()

/**
 * 通过注解的方式，实现 Activity 之间的数据通信
 * ViewModel 不要持有 Activity 或 View 对象，以防内存泄漏
 */
fun ComponentActivity.injectViewModel() {
    this::class.java.declaredFields.forEach { field ->
        field.getAnnotation(VMScope::class.java)?.also {
            val vmStore = commonViewStore(it.scopeName, this)
            val viewModel = ViewModelProvider(vmStore).get(field.type as Class<ViewModel>)
            field.isAccessible = true
            field.set(this, viewModel)
        }
    }
}

fun commonViewStore(scopeName: String, activity: ComponentActivity): ActivityBusViewModelOwner {
    var vmStore: ActivityBusViewModelOwner? = null
    if (cache.containsKey(scopeName)) {
        vmStore = cache[scopeName]
    } else {
        vmStore = ActivityBusViewModelOwner()
        cache[scopeName] = vmStore
    }
    vmStore!!.bindHost(activity)
    return vmStore
}

class ActivityBusViewModelOwner : ViewModelStoreOwner {

    var activityList = arrayListOf<ComponentActivity>()

    private var vmStore: ViewModelStore? = null

    override fun getViewModelStore(): ViewModelStore {
        if (vmStore == null)
            vmStore = ViewModelStore()
        return vmStore!!
    }

    fun bindHost(componentActivity: ComponentActivity) {
        if (!activityList.contains(componentActivity)) {
            activityList.add(componentActivity)
            componentActivity.lifecycle.addObserver(object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        activityList.remove(componentActivity)
                        if (activityList.isEmpty()) {
                            cache.entries.find { it.value == this@ActivityBusViewModelOwner }?.also {
                                viewModelStore.clear()
                                cache.remove(it.key)
                            }
                        }
                    }
                }
            })
        }
    }
}




