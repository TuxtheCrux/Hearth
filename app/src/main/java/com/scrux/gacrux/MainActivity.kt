package com.scrux.gacrux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.scrux.gacrux.core.navigation.NavigationGraph
import com.scrux.gacrux.core.security.SessionManager

import com.scrux.gacrux.core.ui.RedlineTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RedlineTheme {
                NavigationGraph(sessionManager)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        sessionManager.resetTimer()
    }

    override fun onResume() {
        super.onResume()
        if (!sessionManager.isSessionLocked()) {
            sessionManager.stopTimer()
        }
    }
}

