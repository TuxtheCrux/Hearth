package com.scrux.gacrux.auth.domain

sealed interface AuthState {
    data object Idle : AuthState
    data object LoginSuccess : AuthState
    data object LoginError : AuthState
    data object AccountCreated : AuthState
    data object TooManyAttempts : AuthState
}