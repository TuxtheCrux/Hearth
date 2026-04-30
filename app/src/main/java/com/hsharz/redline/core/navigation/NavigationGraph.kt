package com.hsharz.redline.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    Scaffold(
        bottomBar = {
            BottomBar(
                currentDestination = currentDestination,
                onTabSelected = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Passwords,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Routes.Passwords> {
                PasswordsScreen()
            }
            composable<Routes.Scanner> {
                ScannerScreen()
            }
            composable<Routes.Cipher> {
                CipherScreen()
            }
        }
    }
}



