package com.scrux.gacrux.core.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    data object Login : Routes {
    }

    @Serializable
    data object Register : Routes


    @Serializable
    data object Passwords : Routes

    @Serializable
    data object Scanner : Routes

    @Serializable
    data object Cipher : Routes
}