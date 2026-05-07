package com.hsharz.redline.feature.passwords.domain

import com.hsharz.redline.feature.passwords.data.PasswordEntity
import com.hsharz.redline.feature.passwords.data.PasswordRepository
import javax.inject.Inject


class UpdatePasswordUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository
) {
    suspend fun updatePassword(updatedPasswordData: UpdatedPasswordData) {
        passwordRepository.updatePassword(updatedPasswordData)
    }
}