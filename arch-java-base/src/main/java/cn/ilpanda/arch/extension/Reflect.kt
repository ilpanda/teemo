package cn.ilpanda.arch.extension

import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

inline fun <reified T : Any> T.reflectSetField(filedName: String, value: Any) {
    T::class.memberProperties.firstOrNull {
        it.name == filedName
    }?.let {
        it.isAccessible = true
        if (it is KMutableProperty<*>) {
            it.setter.call(this, value)
        }
    }
}

inline fun <reified T : Any> T.reflectSetFieldMap(map: Map<String, Any>) {
    T::class.memberProperties.filter {
        map.containsKey(it.name)
    }.forEach {
        it.isAccessible = true
        if (it is KMutableProperty<*>) {
            it.setter.call(this, map[it.name])
        }
    }
}

inline fun <reified T : Any, R> T.reflectGetProperty(name: String): R? =
    T::class.memberProperties
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.get(this) as? R


inline fun <reified T> T.reflectCallFunc(name: String, vararg args: Any?): Any? =
    T::class.declaredMemberFunctions
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.call(this, *args)
