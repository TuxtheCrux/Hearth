package com.hsharz.redline.feature.passwords.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsharz.redline.feature.passwords.data.EntryType
import com.hsharz.redline.feature.passwords.data.PasswordEntity
import com.hsharz.redline.feature.passwords.data.PasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PasswordViewModel @Inject constructor(private val repository: PasswordRepository) :
    ViewModel() {
    val passwords = repository.getAllPasswords()
    fun insertPassword(password: PasswordEntity) = viewModelScope.launch {
        repository.insertPassword(password)
    }

    fun updatePassword(password: PasswordEntity) = viewModelScope.launch {
        repository.updatePassword(password)
    }

    fun deletePassword(password: PasswordEntity) = viewModelScope.launch {
        repository.deletePassword(password)
    }

    fun isUrl(url: String): Boolean {
        return url.startsWith("http://") || url.startsWith("https://")
                || url.startsWith("www.")
    }

    fun createPassword(password: String, websiteOrApp: String, email: String) {
        val isWebsite: EntryType = if (isUrl(websiteOrApp)) {
            EntryType.WEBSITE
        } else {
            EntryType.APP
        }
        val passwordEntity = PasswordEntity(
            encryptedPassword = password, websiteOrApp = websiteOrApp,
            email = email, entryType = isWebsite, passkey = false
        )
        insertPassword(passwordEntity)
    }
}