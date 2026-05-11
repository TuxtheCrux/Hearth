package com.hsharz.redline.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import com.hsharz.redline.auth.domain.AuthState
import com.hsharz.redline.core.navigation.Routes

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val loginState by authViewModel.loginState.collectAsStateWithLifecycle()
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var emailError by rememberSaveable { mutableStateOf(false) }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    Column {
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = false
            },
            isError = emailError,
            placeholder = { Text("Email or Username") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = false
            },
            isError = passwordError,
            placeholder = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    authViewModel.verifyUser(password, email)
                } else {
                    emailError = email.isEmpty()
                    passwordError = password.isEmpty()
                }
            },
            content = {
                Text(text = "Login")
            }
        )
        Button(
            onClick = {
                navController.navigate(Routes.Register)
            },
            content = {
                Text(text = "Register")
            }
        )
    }
    LaunchedEffect(loginState) {
        when (loginState) {
            AuthState.LoginSuccess -> navController.navigate(Routes.Passwords)
            AuthState.LoginError -> {
                emailError = true
                passwordError = true
            }
            //AuthState.TooManyAttempts -> //ResetPassword
            else -> Unit
        }
    }
}