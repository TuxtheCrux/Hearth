package com.hsharz.redline.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hsharz.redline.feature.passwords.ui.PasswordScreen
import com.hsharz.redline.feature.passwords.ui.PasswordViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.hsharz.redline.auth.ui.AuthViewModel
import com.hsharz.redline.auth.ui.LoginScreen
import com.hsharz.redline.auth.ui.RegisterScreen
import com.hsharz.redline.core.security.SessionManager

@Composable
fun NavigationGraph(sessionManager: SessionManager) {
    val navController = rememberNavController()
    LaunchedEffect(Unit) {
        sessionManager.sessionExpired.collect {
            navController.navigate(Routes.Login) {
                popUpTo(Routes.Login) { inclusive = true }
            }
        }
    }
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val showBottomBar = !(currentDestination?.route?.contains("Login") == true
            || currentDestination?.route?.contains("Register") == true)
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
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
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Login,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Routes.Login> {
                val viewModel: AuthViewModel = hiltViewModel()
                LoginScreen(authViewModel = viewModel, navController = navController)
            }
            composable<Routes.Register> {
                val viewModel: AuthViewModel = hiltViewModel()
                RegisterScreen(
                    authViewModel = viewModel, navController = navController
                )
            }
            composable<Routes.Passwords> {
                val viewModel: PasswordViewModel = hiltViewModel()
                PasswordScreen(viewModel = viewModel)
            }
            composable<Routes.Scanner> {
                //ScannerScreen()
            }
            composable<Routes.Cipher> {
                //CipherScreen()
            }
        }
    }
}



