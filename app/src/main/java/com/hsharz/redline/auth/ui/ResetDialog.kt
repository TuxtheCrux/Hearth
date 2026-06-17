package com.hsharz.redline.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetDialog(
    onConfirm: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = { }
    )
    {
        Surface(
            shape = AlertDialogDefaults.shape,
            color = AlertDialogDefaults.containerColor,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column (
                modifier = Modifier.padding(24.dp)
            ){
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Say Bye Bye"
                )
                Text(
                    text = "Ohh... ohhh... Security can be unfair, but this is on you. " +
                            "Better know you password next time"
                )
                HorizontalDivider()
                Button(
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = { onConfirm() },
                    content = {
                        Text(text = "Guilty")
                    }
                )
            }
        }
    }
}
