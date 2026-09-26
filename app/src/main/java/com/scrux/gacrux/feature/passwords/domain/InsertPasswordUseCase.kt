package com.scrux.gacrux.feature.passwords.domain

import com.scrux.gacrux.feature.passwords.data.EntryType
import com.scrux.gacrux.feature.passwords.data.PasswordEntity
import com.scrux.gacrux.feature.passwords.data.PasswordRepository
import javax.inject.Inject

class InsertPasswordUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository
) {
    fun isUrl(url: String): Boolean {
        return url.startsWith("http://") || url.startsWith("https://")
                || url.startsWith("www.")
    }

    suspend fun createPassword(password: String, email: String, websiteOrApp: String) {
        val isWebsite: EntryType = if (isUrl(websiteOrApp)) {
            EntryType.WEBSITE
        } else {
            EntryType.APP
        }
        val passwordEntity = PasswordEntity(
            encryptedPassword = password, websiteOrApp = websiteOrApp,
            email = email, entryType = isWebsite, passkey = false
        )
        passwordRepository.insertPassword(passwordEntity)
    }
}