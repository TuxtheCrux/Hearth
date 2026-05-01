package com.hsharz.redline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hsharz.redline.core.navigation.NavigationGraph

import com.hsharz.redline.core.ui.RedlineTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RedlineTheme {
                NavigationGraph()
            }
        }
    }
}

