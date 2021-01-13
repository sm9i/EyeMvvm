package com.sm9i.base.application

import android.app.Application


open class BaseApplication : Application() {
    companion object {
        lateinit var INSTANCE: Application
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}