package com.hsharz.redline.feature.passwords.domain

import com.hsharz.redline.feature.passwords.data.PasswordRepository
import javax.inject.Inject

class DeletePasswordUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository
) {
    suspend fun deletePassword(passwordId: Long) {
        passwordRepository.deletePassword(passwordId)
    }
}
