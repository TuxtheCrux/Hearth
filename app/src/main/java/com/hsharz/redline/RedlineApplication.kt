package com.hsharz.redline

import android.app.Application
import com.hsharz.redline.core.security.SessionManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RedlineApplication : Application() {
    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate() {
        super.onCreate()
        System.loadLibrary("sqlcipher")
    }
}