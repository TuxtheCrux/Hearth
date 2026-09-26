package com.scrux.gacrux.feature.passwords.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.scrux.gacrux.feature.passwords.domain.PasswordDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordEditSheet(
    selectedPassword: PasswordDetail,
    onSave: (id: Long, newPassword: String, newEmail: String, newWebOrApp: String) -> Unit,
    onDismiss: () -> Unit
) {
    var newPassword by rememberSaveable { mutableStateOf(selectedPassword.password) }
    var newEmail by rememberSaveable { mutableStateOf(selectedPassword.email) }
    var newWebOrApp by rememberSaveable { mutableStateOf(selectedPassword.webOrApp) }

    var emailError by rememberSaveable { mutableStateOf(false) }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    var webOrAppError by rememberSaveable { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
    ) {
        Column {
            Button(
                onClick = {
                    if (newPassword.isNotBlank() &&
                        newEmail.isNotBlank() &&
                        newWebOrApp.isNotBlank()
                    ) {
                        onSave(
                            selectedPassword.id,
                            newPassword,
                            newEmail,
                            newWebOrApp
                        )
                        onDismiss()
                    } else {
                        passwordError = true
                        emailError = true
                        webOrAppError = true
                    }
                },
                content = {
                    Text(text = "Save")
                }
            )
            OutlinedTextField(
                value = newPassword,
                onValueChange = {
                    newPassword = it
                    passwordError = false
                },
                label = { Text(text = "Password") },
                isError = passwordError
            )
            OutlinedTextField(
                value = newEmail,
                onValueChange = {
                    newEmail = it
                    emailError = false
                },
                label = { Text(text = "Email") },
                isError = emailError
            )
            OutlinedTextField(
                value = newWebOrApp,
                onValueChange = {
                    newWebOrApp = it
                    webOrAppError = false
                },
                label = { Text(text = "Website or App") },
                isError = webOrAppError
            )
        }
    }
}