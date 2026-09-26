package com.scrux.gacrux.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrux.gacrux.auth.domain.AuthState
import com.scrux.gacrux.auth.domain.CreateHashUseCase
import com.scrux.gacrux.auth.domain.ResetAppUseCase
import com.scrux.gacrux.auth.domain.VerifyCredentialsUseCase
import com.scrux.gacrux.core.security.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val verifyCredentialsUseCase: VerifyCredentialsUseCase,
    private val createHashUseCase: CreateHashUseCase,
    private val sessionManager: SessionManager,
    private val resetAppUseCase: ResetAppUseCase
) : ViewModel() {
    private var countLoginAttempts = 0
    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState = _loginState.asStateFlow()

    fun createAcc(masterKey: String, username: String) =
        viewModelScope.launch(Dispatchers.Default) {
            val masterKeyCharArr = masterKey.toCharArray()
            createHashUseCase.invoke(masterKey = masterKeyCharArr.copyOf(), username = username)
            sessionManager.unlock(masterKeyCharArr)
            _loginState.value = AuthState.AccountCreated
        }

    fun verifyUser(masterKey: String, username: String) =
        viewModelScope.launch(Dispatchers.Default) {
            val masterKeyCharArr = masterKey.toCharArray()
            if (!verifyCredentialsUseCase.invoke(
                    masterKey = masterKeyCharArr.copyOf(),
                    username = username
                )
            ) {
                countLoginAttempts++
                _loginState.value = AuthState.LoginError
                if (countLoginAttempts > 3) {
                    _loginState.value = AuthState.TooManyAttempts
                }
            } else {
                sessionManager.unlock(masterKeyCharArr)
                _loginState.value = AuthState.LoginSuccess
            }

        }

    fun resetApp() = viewModelScope.launch(Dispatchers.IO) {
        resetAppUseCase.invoke()
    }
}