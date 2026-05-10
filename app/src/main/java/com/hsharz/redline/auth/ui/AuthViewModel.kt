package com.hsharz.redline.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsharz.redline.auth.domain.AuthState
import com.hsharz.redline.auth.domain.CreateHashUseCase
import com.hsharz.redline.auth.domain.VerifyCredentialsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val verifyCredentialsUseCase: VerifyCredentialsUseCase,
    private val createHashUseCase: CreateHashUseCase
) : ViewModel() {
    private var countLoginAttempts = 0
    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState = _loginState.asStateFlow()

    fun createAcc(masterKey: String, username: String) =
        viewModelScope.launch(Dispatchers.Default) {
            createHashUseCase.invoke(masterKey = masterKey.toCharArray(), username = username)
            _loginState.value = AuthState.AccountCreated
        }

    fun verifyUser(masterKey: String, username: String) =
        viewModelScope.launch(Dispatchers.Default) {
            if (!verifyCredentialsUseCase.invoke(
                    masterKey = masterKey.toCharArray(),
                    username = username
                )
            ) {
                countLoginAttempts++
                _loginState.value = AuthState.LoginError
                if (countLoginAttempts > 3) {
                    _loginState.value = AuthState.TooManyAttempts
                }
            } else {
                _loginState.value = AuthState.LoginSuccess
            }

        }
}