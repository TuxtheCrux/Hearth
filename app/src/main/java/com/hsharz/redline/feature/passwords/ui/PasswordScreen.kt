package com.hsharz.redline.feature.passwords.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hsharz.redline.feature.passwords.data.PasswordEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordScreen(viewModel: PasswordViewModel, modifier: Modifier = Modifier) {
    val passwords by viewModel.passwords.collectAsStateWithLifecycle(initialValue = emptyList())
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var selectedPassword by rememberSaveable { mutableStateOf<PasswordEntity?>(null) }
    Column(
        modifier = modifier.fillMaxSize(),
        content = {
            LazyColumn(
                content = {
                    items(passwords) { password ->
                        PasswordCard(
                            password
                        ) { clickedPassword ->
                            selectedPassword = clickedPassword
                            openBottomSheet = true
                        }
                    }
                }
            )
        }
    )
    if (openBottomSheet) {
        PasswordDetailSheet(
            selectedPassword!!,
            bottomSheetState,
            onDismiss = { openBottomSheet = false })
    }
}

@Composable
fun PasswordCard(
    passwordEntity: PasswordEntity,
    onCardClick: (PasswordEntity) -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = {
            onCardClick(passwordEntity)
        },
        content = {
            Column {
                Text(text = passwordEntity.websiteOrApp)
            }
        }
    )
}

