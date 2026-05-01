package com.hsharz.redline.core.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object Passwords : Routes

    @Serializable
    data object Scanner : Routes

    @Serializable
    data object Cipher : Routes
}