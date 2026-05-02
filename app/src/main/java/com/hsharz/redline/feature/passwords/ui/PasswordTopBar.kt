package com.hsharz.redline.feature.passwords.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

//TODO add topbar with search and multi selection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTopBar(
    query: String,
    onQueryChange: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onAddClick: () -> Unit,
) {
    TopAppBar(
        title = {
            val colors1 = SearchBarDefaults.colors()
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = query,
                        onQueryChange = onQueryChange,
                        onSearch = {

                        },
                        expanded = active,
                        onExpandedChange = onActiveChange,
                        enabled = true,
                        placeholder = null,
                        leadingIcon = null,
                        trailingIcon = null,
                        colors = colors1.inputFieldColors,
                        interactionSource = null,
                    )
                },
                expanded = active,
                onExpandedChange = onActiveChange,
                modifier = Modifier,
                shape = SearchBarDefaults.inputFieldShape,
                colors = colors1,
                tonalElevation = SearchBarDefaults.TonalElevation,
                shadowElevation = SearchBarDefaults.ShadowElevation,
                windowInsets = SearchBarDefaults.windowInsets,
                content = {}
            )
        },
        actions = {
            IconButton(
                onClick = {
                    onAddClick()
                },
                content = {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            )
        }
    )
}