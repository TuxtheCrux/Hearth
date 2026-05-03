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
import com.hsharz.redline.feature.passwords.data.PasswordEntity
import java.text.SimpleDateFormat
import java.util.Locale

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
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val addBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var selectedPassword by remember { mutableStateOf<PasswordEntity?>(null) }

    //Searchbar
    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    var openAddPasswordSheet by rememberSaveable { mutableStateOf(false) }

    val sortedPasswords = remember(passwords, query) {
        passwords.filter { it.websiteOrApp.contains(query) }.sortedWith(
            compareBy(String.CASE_INSENSITIVE_ORDER)
            { it.websiteOrApp })
    }


    /**
     * Scaffold for the screen
     * @author Tux_the_Crux
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
             * @author Tux_the_Crux
             */
            Column(
                modifier = modifier.fillMaxSize().padding(innerPadding),
                content = {
                    LazyColumn(
                        content = {
                            items(sortedPasswords, key = { it.id }) { password ->
                                val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
                                    confirmValueChange = {
                                        if (it == SwipeToDismissBoxValue.EndToStart) {
                                            viewModel.deletePassword(password)
                                            true
                                        } else {
                                            false
                                        }
                                    }
                                )
                                /**
                                 * Swipe to dismiss box for deleting passwords
                                 * @author Tux_the_Crux
                                 */
                                SwipeToDismissBox(
                                    modifier = Modifier.fillMaxWidth().padding(
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
                                            modifier = Modifier.fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(color),
                                            contentAlignment = Alignment.CenterEnd
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete",
                                                modifier = Modifier.padding(12.dp),
                                                tint = Color.White
                                            )
                                        }
                                    }
                                ) {
                                    PasswordCard(
                                        password,
                                        modifier = Modifier
                                    ) { clickedPassword ->
                                        selectedPassword = clickedPassword
                                        openBottomSheet = true
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
    if (openBottomSheet) {
        val formattedTimestamp = SimpleDateFormat(
            "dd-MM-yyyy HH:mm",
            Locale.getDefault()
        ).format(selectedPassword?.lastModified)
        PasswordDetailSheet(
            selectedPassword!!,
            bottomSheetState,
            formattedTimestamp,
            onDismiss = { openBottomSheet = false })
    }
    if (openAddPasswordSheet) {
        PasswordAddPasswordSheet(
            viewModel = viewModel,
            bottomSheetState = addBottomSheetState,
            onDismiss = { openAddPasswordSheet = false }
        )
    }
}

/**
 * Card for each password
 * @param passwordEntity
 * @param onCardClick
 * @author Tux_the_Crux
 */
@Composable
fun PasswordCard(
    passwordEntity: PasswordEntity,
    modifier: Modifier = Modifier,
    onCardClick: (PasswordEntity) -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.fillMaxWidth(),
        onClick = {
            onCardClick(passwordEntity)
        },
        content = {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = passwordEntity.websiteOrApp,
                    style = typography.titleLarge
                )

            }
        }
    )
}