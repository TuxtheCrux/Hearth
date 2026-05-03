package com.hsharz.redline.feature.passwords.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.hsharz.redline.feature.passwords.data.PasswordEntity

//TODO
// 1. Add edit and delete buttons/function
// 2. Add password strength indicator +
// 3. hide password at first and add show password button in password detail sheet
/**
 * Sheet for displaying details of a single password
 * @param selectedPassword
 * @param bottomSheetState
 * @param onDismiss
 * @author Tux_the_Crux
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordDetailSheet(
    selectedPassword: PasswordEntity,
    bottomSheetState: SheetState,
    formattedTimestamp: String,
    onDismiss: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState
    ) {
        Column {
            ListItem(
                overlineContent = { Text(text = "Username") },
                headlineContent = { Text(text = selectedPassword.email) }
            )
            ListItem(
                overlineContent = { Text(text = "Password") },
                headlineContent = { Text(text = selectedPassword.encryptedPassword) }
            )
            ListItem(
                overlineContent = { Text(text = "Website or App") },
                headlineContent = {
                    Text(text = selectedPassword.websiteOrApp)
                }
            )
            ListItem(
                overlineContent = { Text(text = "Last Changed") },
                headlineContent = {
                    Text(
                        text = formattedTimestamp
                    )
                }
            )
        }
    }
}
