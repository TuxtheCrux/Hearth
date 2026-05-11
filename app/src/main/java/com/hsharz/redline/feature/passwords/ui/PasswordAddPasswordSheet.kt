package com.hsharz.redline.feature.passwords.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//TODO Passkey logik
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordAddPasswordSheet(
    viewModel: PasswordViewModel,
    onDismiss: () -> Unit,
    addBottomSheetState: SheetState
) {
    var email by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var websiteOrApp by rememberSaveable { mutableStateOf("") }

    var emailError by rememberSaveable { mutableStateOf(false) }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    var websiteOrAppError by rememberSaveable { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = addBottomSheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (email.isNotEmpty() && newPassword.isNotEmpty() &&
                        websiteOrApp.isNotEmpty()
                    ) {
                        viewModel.insertPassword(
                            password = newPassword,
                            email = email,
                            webOrApp = websiteOrApp
                        )
                        onDismiss()
                    } else {
                        emailError = email.isEmpty()
                        passwordError = newPassword.isEmpty()
                        websiteOrAppError = websiteOrApp.isEmpty()
                    }
                },
                content = { Text(text = "Create Password") }
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false
                },
                label = { Text(text = "Email") },
                isError = emailError,
            )
            OutlinedTextField(
                value = newPassword,
                onValueChange = {
                    newPassword = it
                    passwordError = false
                },
                label = { Text(text = "New Password") },
                isError = passwordError
            )
            OutlinedTextField(
                value = websiteOrApp,
                onValueChange = {
                    websiteOrApp = it
                    websiteOrAppError = false
                },
                label = { Text(text = "Website or App") },
                isError = websiteOrAppError
            )
        }
    }
}