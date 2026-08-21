package com.hsharz.redline.feature.passwords.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hsharz.redline.core.ui.causticShimmer
import com.hsharz.redline.feature.passwords.domain.PasswordListItem

//TODO Swipe to delete -> solve Deprecation warning
// Consider writing own saver for selectedPassword
// Make search case unsensitive
/**
 * Screen for displaying all passwords
 * @param viewModel
 * @param modifier
 * @author Tux_the_Crux
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordScreen(viewModel: PasswordViewModel, modifier: Modifier = Modifier) {
    val passwords by viewModel.passwords.collectAsStateWithLifecycle(initialValue = emptyList())
    //BottomSheet
    var openDetailSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val addBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)


    val detail by viewModel.passwordDetail.collectAsStateWithLifecycle()

    //Searchbar
    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    var openAddPasswordSheet by rememberSaveable { mutableStateOf(false) }

    val sortedPasswords = remember(passwords, query) {
        passwords.filter { it.webOrApp.contains(query, ignoreCase = true) }.sortedWith(
            compareBy(String.CASE_INSENSITIVE_ORDER)
            { it.webOrApp })
    }

    var openEditSheetState by remember { mutableStateOf(false) }
    var editId by remember { mutableStateOf<Long?>(null) }


    /**
     * Scaffold for the screen
     */
    Scaffold(
        topBar = {
            PasswordTopBar(
                query = query,
                onQueryChange = { newQuery -> query = newQuery },
                onActiveChange = {},
                active = active,
                onAddClick = { openAddPasswordSheet = true }
            )
        },
        content = { innerPadding ->
            /**
             * LazyColumn for displaying all passwords
             */
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                content = {
                    LazyColumn(
                        content = {
                            items(sortedPasswords, key = { it.id }) { password ->
                                val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
                                    confirmValueChange = {
                                        if (it == SwipeToDismissBoxValue.EndToStart) {
                                            viewModel.deletePassword(password.id)
                                            true
                                        } else {
                                            false
                                        }
                                    }
                                )
                                /**
                                 * Swipe to dismiss box for deleting passwords
                                 */
                                SwipeToDismissBox(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = 16.dp,
                                            vertical = 8.dp
                                        ),
                                    state = swipeToDismissBoxState,
                                    backgroundContent = {
                                        val color = if (swipeToDismissBoxState.dismissDirection
                                            == SwipeToDismissBoxValue.EndToStart
                                        ) {
                                            Color.Red
                                        } else {
                                            Color.Transparent
                                        }
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(color),
                                            contentAlignment = Alignment.CenterEnd
                                        ) {
                                            if (swipeToDismissBoxState.dismissDirection
                                                == SwipeToDismissBoxValue.EndToStart
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Delete",
                                                    modifier = Modifier.padding(12.dp),
                                                    tint = Color.White
                                                )
                                            }
                                        }
                                    }
                                ) {
                                    PasswordCard(
                                        password,
                                        modifier = Modifier
                                    ) { clickedPassword ->
                                        viewModel.getPasswordDetail(clickedPassword.id)
                                        openDetailSheet = true
                                    }
                                }
                            }
                        }
                    )
                }
            )
        }
    )
    //Check if a bottom sheet is open
    if (openDetailSheet && detail != null) {
        PasswordDetailSheet(
            detail!!,
            bottomSheetState,
            viewModel,
            onDismiss = { openDetailSheet = false },
            onEditClick = {
                openDetailSheet = false
                editId = it
                openEditSheetState = true

            }
        )
    }
    if (openEditSheetState && editId != null && detail != null) {
        PasswordEditSheet(
            selectedPassword = detail!!,
            onSave = { passwordId, newPassword, newEmail, newWebOrApp ->
                viewModel.saveAndReload(passwordId, newPassword, newEmail, newWebOrApp)
                openEditSheetState = false
                openDetailSheet = true
            },
            onDismiss = { openEditSheetState = false }
        )
    }
    if (openAddPasswordSheet) {
        PasswordAddPasswordSheet(
            viewModel = viewModel,
            addBottomSheetState = addBottomSheetState,
            onDismiss = { openAddPasswordSheet = false }
        )
    }
}

/**
 * Card for each password
 * @param passwordListItem
 * @param onCardClick
 * @param modifier
 */
@Composable
fun PasswordCard(
    passwordListItem: PasswordListItem,
    modifier: Modifier = Modifier,
    onCardClick: (PasswordListItem) -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).causticShimmer(),
        onClick = {
            onCardClick(passwordListItem)
        },
        content = {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = passwordListItem.webOrApp,
                    style = typography.titleLarge
                )

            }
        }
    )
}