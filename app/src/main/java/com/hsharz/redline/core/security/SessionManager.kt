package com.hsharz.redline.core.security

import android.os.Handler
import android.os.Looper
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class SessionManager @Inject constructor() {
    private lateinit var masterKey: CharArray
    private val handler = Handler(Looper.getMainLooper())
    private val TIMEOUT_MS: Long = 60_000

    fun getMasterKeyCopy(): CharArray {
        if (!::masterKey.isInitialized) {
            throw IllegalStateException("Masterkey is not initialized")
        }
        val copiedKey = masterKey.copyOf()
        resetTimer()
        return copiedKey
    }

    fun unlock(key: CharArray) {
        resetTimer()
        masterKey = key
    }

    fun lock() {
        masterKey.fill('\u0000')
        handler.removeCallbacksAndMessages(null)
    }


    fun resetTimer() {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({ lock() }, TIMEOUT_MS)
    }
}