package com.hsharz.redline.feature.passwords.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hsharz.redline.core.security.SessionManager
import com.hsharz.redline.feature.passwords.domain.DeletePasswordUseCase
import com.hsharz.redline.feature.passwords.domain.GetPasswordDetailUseCase
import com.hsharz.redline.feature.passwords.domain.PasswordDetail
import com.hsharz.redline.feature.passwords.domain.GetPasswordListUseCase
import com.hsharz.redline.feature.passwords.domain.InsertPasswordUseCase
import com.hsharz.redline.feature.passwords.domain.UpdatePasswordUseCase
import com.hsharz.redline.feature.passwords.domain.UpdatedPasswordData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val getPasswordDetailUseCase: GetPasswordDetailUseCase,
    private val getPasswordListUseCase: GetPasswordListUseCase,
    private val insertPasswordUseCase: InsertPasswordUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val sessionManager: SessionManager
) :
    ViewModel() {
    val passwords = getPasswordListUseCase.invoke()
    val passwordDetail = MutableStateFlow<PasswordDetail?>(null)
    private val sessionScope = CoroutineScope(Dispatchers.Main)

    init {
        sessionExpired()
    }

    private fun sessionExpired() = sessionScope.launch {
        sessionManager.sessionExpired.collect {
            viewModelScope.cancel()
        }
    }

    override fun onCleared() {
        sessionScope.cancel()
    }

    fun insertPassword(password: String, email: String, webOrApp: String) = viewModelScope.launch {
        insertPasswordUseCase.createPassword(password, email = email, websiteOrApp = webOrApp)
    }

    fun updatePassword(updatedPasswordData: UpdatedPasswordData) =
        viewModelScope.launch {
            updatePasswordUseCase.updatePassword(updatedPasswordData)
        }

    fun deletePassword(passwordId: Long) = viewModelScope.launch {
        deletePasswordUseCase.deletePassword(passwordId)
    }

    fun loadPasswordDetail(id: Long) = viewModelScope.launch {
        passwordDetail.value = getPasswordDetailUseCase.invoke(id)
    }
}