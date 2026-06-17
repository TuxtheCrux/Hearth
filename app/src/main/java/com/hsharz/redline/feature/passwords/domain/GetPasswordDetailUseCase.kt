package com.hsharz.redline.feature.passwords.domain

import com.hsharz.redline.core.security.CryptoManager
import com.hsharz.redline.core.security.SessionManager
import com.hsharz.redline.feature.passwords.data.PasswordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
        try {
            val (decryptedPassword, decryptedEmail) = withContext(Dispatchers.Default) {
                val password = cryptoManager.decrypt(
                    encryptedPasswordEntity.encryptedPassword,
                    masterKey
                )
                val email = cryptoManager.decrypt(
                    encryptedPasswordEntity.email,
                    masterKey
                )
                Pair(password, email)
            }
            return PasswordDetail(
                id,
                decryptedPassword,
                decryptedEmail,
                encryptedPasswordEntity.websiteOrApp,
                encryptedPasswordEntity.lastModified
            )
        } finally {
            masterKey.fill('\u0000')
        }
    }
}