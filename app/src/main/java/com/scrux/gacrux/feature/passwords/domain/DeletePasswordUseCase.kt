package com.scrux.gacrux.feature.passwords.domain

import com.scrux.gacrux.feature.passwords.data.PasswordRepository
import javax.inject.Inject

class DeletePasswordUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository
) {
    suspend fun deletePassword(passwordId: Long) {
        passwordRepository.deletePassword(passwordId)
    }
}
