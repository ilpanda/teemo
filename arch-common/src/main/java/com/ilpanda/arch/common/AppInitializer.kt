package com.ilpanda.arch.common

import android.app.Application
import android.content.Context
import androidx.startup.Initializer

internal class AppInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        application = context as Application
        LifeRecycleManager.instance.init(context)
    }

    override fun dependencies() = emptyList<Class<Initializer<*>>>()

    companion object {
        internal lateinit var application: Application private set
    }
}
