package com.ilpanda.plugin.extesion

open class BasePluginExtension {

    var enable: Boolean = false // 是否开启插件，默认值为 false

    var whiteList: List<String> = arrayListOf() // 白名单

    var blackList: List<String> = arrayListOf() // 黑名单


    override fun toString(): String {
        return "BasePluginExtension(enable=$enable, whiteList=$whiteList, blackList=$blackList)"
    }


}