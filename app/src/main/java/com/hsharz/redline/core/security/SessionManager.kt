package com.hsharz.redline.core.security

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class SessionManager @Inject constructor() {
    private lateinit var masterKey: CharArray
    private val handler = Handler(Looper.getMainLooper())
    private val TIMEOUT_MS: Long = 60_000

    //Graceful session termination
    private val _sessionExpired = MutableSharedFlow<Unit>()
    val sessionExpired = _sessionExpired.asSharedFlow()
    private var isLocked = true


    fun getMasterKeyCopy(): CharArray {
        if (!::masterKey.isInitialized || isLocked) {
            throw IllegalStateException("Masterkey is not initialized")
        }
        val copiedKey = masterKey.copyOf()
        resetTimer()
        return copiedKey
    }

    fun unlock(key: CharArray) {
        resetTimer()
        isLocked = false
        masterKey = key
    }

    fun lock() {
        CoroutineScope(Dispatchers.Main).launch {
            _sessionExpired.emit(Unit)
            isLocked = true
            masterKey.fill('\u0000')
            handler.removeCallbacksAndMessages(null)
        }
    }


    fun resetTimer() {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({ lock() }, TIMEOUT_MS)
    }
}