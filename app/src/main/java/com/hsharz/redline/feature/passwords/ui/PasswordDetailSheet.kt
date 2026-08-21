package com.hsharz.redline.feature.passwords.ui

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hsharz.redline.feature.passwords.data.EntryType
import com.hsharz.redline.feature.passwords.domain.PasswordDetail
import java.text.SimpleDateFormat
import java.util.Locale

//TODO
// 1. Add edit and delete buttons/function
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
    onDismiss: () -> Unit,
    onEditClick: (Long) -> Unit
) {
    val timeInHours = remember(selectedPassword) {
        DateUtils.getRelativeTimeSpanString(selectedPassword.lastModified)
            .toString()
    }
    val timeAsDate = remember(selectedPassword) {
        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(
            selectedPassword.lastModified
        )
    }
    val hidePasswordState = rememberSaveable { mutableStateOf(true) }
    val displayPassword =
        if (hidePasswordState.value) "•".repeat(selectedPassword.password.length)
        else selectedPassword.password

    val passwordDetail by viewModel.passwordDetail.collectAsStateWithLifecycle()
    LaunchedEffect(passwordDetail) {
        passwordDetail?.let { viewModel.checkPasswordStrength(it.password) }
    }
    val passwordStrength by viewModel.passwordStrength.collectAsStateWithLifecycle()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState
    ) {
        Column {
            Button(
                modifier = Modifier.padding(horizontal = 10.dp),
                onClick = {
                    onEditClick(selectedPassword.id)
                },
                content = {
                    Text(text = "Edit")
                }
            )

            ListItem(
                overlineContent = { Text(text = "Username") },
                headlineContent = { Text(text = selectedPassword.email) }
            )
            PasswordStrengthIndicator(passwordStrength = passwordStrength)
            ListItem(
                overlineContent = { Text(text = "Password") },
                headlineContent = { Text(text = displayPassword) },
                trailingContent = {
                    IconButton(
                        onClick = { hidePasswordState.value = !hidePasswordState.value },
                        content = {
                            Icon(
                                imageVector =
                                    if (hidePasswordState.value) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                contentDescription = "Hide or show password"
                            )
                        }
                    )
                }
            )
            ListItem(
                overlineContent = {
                    if (selectedPassword.entryType == EntryType.WEBSITE) {
                        Text(text = "Website")
                    } else Text(text = "App")
                },
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
}
