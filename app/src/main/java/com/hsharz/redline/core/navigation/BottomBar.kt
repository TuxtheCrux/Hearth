package com.hsharz.redline.core.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute

data class BottomBarData(val route: Routes, val icon: ImageVector?, val label: String) {
    companion object {
        val tabs = listOf(
            BottomBarData(Routes.Passwords, null, "Passwords"),
            BottomBarData(Routes.Scanner, null, "Scanner"),
            BottomBarData(Routes.Cipher, null, "Cipher"),
        )
    }
}

@Composable
fun BottomBar(
    currentDestination: NavDestination?,
    onTabSelected: (Routes) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar {
        BottomBarData.tabs.forEach { tab ->
            NavigationBarItem(
                selected = currentDestination?.hasRoute(tab.route::class) == true,
                onClick = { onTabSelected(tab.route) },
                icon = { Icon(tab.icon!!, tab.label) },
                label = { Text(tab.label) }
            )
        }
    }
}