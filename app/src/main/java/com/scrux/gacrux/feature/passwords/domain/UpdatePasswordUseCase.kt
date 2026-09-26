package com.scrux.gacrux.feature.passwords.domain

import com.scrux.gacrux.feature.passwords.data.PasswordRepository
import javax.inject.Inject


class UpdatePasswordUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository
) {
    suspend fun updatePassword(updatedPasswordData: UpdatedPasswordData) {
        passwordRepository.updatePassword(updatedPasswordData)
    }
}