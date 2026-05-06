package com.hsharz.redline

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RedlineApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        System.loadLibrary("sqlcipher")
    }
}