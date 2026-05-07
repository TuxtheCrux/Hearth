package com.hsharz.redline.feature.passwords.ui

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.hsharz.redline.feature.passwords.domain.PasswordDetail
import java.text.SimpleDateFormat
import java.util.Locale

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
    selectedPassword: PasswordDetail,
    bottomSheetState: SheetState,
    viewModel: PasswordViewModel,
    onDismiss: () -> Unit
) {
    var openEditSheet by rememberSaveable { mutableStateOf(false) }
    val editSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val timeInHours = remember(selectedPassword) {
        DateUtils.getRelativeTimeSpanString(selectedPassword.lastModified)
            .toString()
    }
    val timeAsDate = remember(selectedPassword) {
        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(
            selectedPassword.lastModified
        )
    }
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState
    ) {
        Column {
            Button(
                onClick = {
                    openEditSheet = true
                },
                content = {
                    Text(text = "Edit")
                }
            )

            ListItem(
                overlineContent = { Text(text = "Username") },
                headlineContent = { Text(text = selectedPassword.email) }
            )
            ListItem(
                overlineContent = { Text(text = "Password") },
                headlineContent = { Text(text = selectedPassword.password) }
            )
            ListItem(
                overlineContent = { Text(text = "Website or App") },
                headlineContent = {
                    Text(text = selectedPassword.webOrApp)
                }
            )
            ListItem(
                overlineContent = { Text(text = "Last Changed") },
                headlineContent = {
                    Text(
                        text = if (
                            System.currentTimeMillis() - selectedPassword.lastModified
                            < DateUtils.DAY_IN_MILLIS
                        ) {
                            timeInHours
                        } else {
                            timeAsDate
                        }
                    )
                }
            )
        }
    }
    if (openEditSheet) {
        EditSheet(
            selectedPassword = selectedPassword,
            editSheetState = editSheetState,
            viewModel = viewModel,
            onDismiss = { openEditSheet = false }
        )
    }
}
