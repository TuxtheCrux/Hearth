package com.hsharz.redline.feature.passwords.domain

import com.hsharz.redline.core.security.CryptoManager
import com.hsharz.redline.core.security.SessionManager
import com.hsharz.redline.feature.passwords.data.PasswordRepository
import javax.inject.Inject

class GetPasswordDetailUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository,
    private val cryptoManager: CryptoManager,
    private val sessionManager: SessionManager
) {
    suspend fun invoke(id: Long): PasswordDetail {
        val encryptedPasswordEntity = passwordRepository.getPasswordById(id)
            ?: throw IllegalStateException("Password darf nicht null sein")
        val masterKey = sessionManager.getMasterKeyCopy()
        val decryptedPassword = cryptoManager.decrypt(
            encryptedPasswordEntity.encryptedPassword,
            masterKey
        )
        val decryptedEmail = cryptoManager.decrypt(
            encryptedPasswordEntity.email,
            masterKey
        )
        return PasswordDetail(
            id,
            decryptedPassword,
            decryptedEmail,
            encryptedPasswordEntity.websiteOrApp,
            encryptedPasswordEntity.lastModified
        )
    }

}