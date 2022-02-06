package com.ilpanda.arch_common

import android.app.Application
import android.content.Context

class ArchCommonInit {

    fun init(context: Context) {
        application = context.applicationContext as Application
    }

    companion object {
        internal lateinit var application: Application private set
    }

}