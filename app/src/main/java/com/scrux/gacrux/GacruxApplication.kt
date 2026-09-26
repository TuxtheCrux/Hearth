package com.scrux.gacrux

import android.app.Application
import com.scrux.gacrux.core.security.SessionManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GacruxApplication : Application() {
    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate() {
        super.onCreate()
        System.loadLibrary("sqlcipher")
    }
}