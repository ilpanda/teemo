package extension

import com.google.gson.GsonBuilder
import com.ilpanda.arch_common.java.utils.JsonUtils


fun Any.toJson(): String? {
    return JsonUtils.toJson(this);
}

fun Any.toPrettyJson(): String? {
    val gson = GsonBuilder().setPrettyPrinting().create()
    return gson.toJson(this);
}

inline fun <reified T : Any> String.fromJson(): T {
    return JsonUtils.fromJson(this, T::class.java);
}