package com.hsharz.redline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hsharz.redline.core.navigation.NavigationGraph
import com.hsharz.redline.core.security.SessionManager

import com.hsharz.redline.core.ui.RedlineTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RedlineTheme {
                NavigationGraph(sessionManager)
            }
        }
    }
}

