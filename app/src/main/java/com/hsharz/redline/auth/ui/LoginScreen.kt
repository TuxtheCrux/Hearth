package com.hsharz.redline.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue


@Composable
fun LoginScreen(authViewModel: AuthViewModel) {
    val loginState by authViewModel.loginState.collectAsStateWithLifecycle()
    var openRegisterScreen by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    Column {
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            placeholder = { Text("Email or Username") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            placeholder = { Text("Password") }

        )
    }
}