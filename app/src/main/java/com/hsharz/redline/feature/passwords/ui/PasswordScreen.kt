package com.hsharz.redline.feature.passwords.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hsharz.redline.feature.passwords.data.PasswordEntity

@Composable
fun PasswordScreen(viewModel: PasswordViewModel, modifier: Modifier = Modifier) {
    val passwords by viewModel.passwords.collectAsStateWithLifecycle(initialValue = emptyList())
    Column(
        modifier = modifier.fillMaxSize(),
        content = {
            LazyColumn(
                content = {
                    items(passwords) { password -> PasswordCard(password) }
                }
            )
        }
    )
}

@Composable
fun PasswordCard(passwordEntity: PasswordEntity){
    Card(
        onClick = {},
        content = {
            Column {
                Text(text = passwordEntity.websiteUrl)
            }
        }
    )
}

