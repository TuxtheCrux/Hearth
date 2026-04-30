package com.hsharz.redline.core.navigation

sealed interface Routes {
    data object Passwords : Routes
    data object Scanner : Routes
    data object Cipher : Routes
}