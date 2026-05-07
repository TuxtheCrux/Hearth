package com.hsharz.redline.feature.passwords.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.hsharz.redline.feature.passwords.domain.PasswordDetail
import com.hsharz.redline.feature.passwords.domain.UpdatedPasswordData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSheet(
    selectedPassword: PasswordDetail,
    editSheetState: SheetState,
    viewModel: PasswordViewModel,
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
        sheetState = editSheetState
    ) {
        Column {
            Button(
                onClick = {
                    if (newPassword.isNotBlank() &&
                        newEmail.isNotBlank() &&
                        newWebOrApp.isNotBlank()
                    ) {
                        val updatedPasswordData = UpdatedPasswordData(
                            id = selectedPassword.id,
                            newPassword = newPassword,
                            newEmail = newEmail,
                            newWebOrApp = newWebOrApp
                        )
                        viewModel.updatePassword(
                            updatedPasswordData
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