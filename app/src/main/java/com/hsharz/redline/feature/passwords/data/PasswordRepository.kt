package com.hsharz.redline.feature.passwords.data

import com.hsharz.redline.core.security.CryptoManager
import com.hsharz.redline.core.security.SessionManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//TODO add encryption/decryption
class PasswordRepository @Inject constructor(
    private val dao: PasswordDao,
    private val cryptoManager: CryptoManager,
    private val sessionManager: SessionManager
) {
    suspend fun insertPassword(password: PasswordEntity) {
        val masterKey = sessionManager.getMasterKeyCopy()
        val encryptedPassword = cryptoManager.encrypt(password.encryptedPassword, masterKey)
        val encryptedEmail = cryptoManager.encrypt(password.email, masterKey)
        dao.insertPassword(
            password.copy(
                encryptedPassword = encryptedPassword,
                email = encryptedEmail
            )
        )
    }

    suspend fun updatePassword(password: PasswordEntity) {
        val masterKey = sessionManager.getMasterKeyCopy()
        val encryptedPassword = cryptoManager.encrypt(password.encryptedPassword, masterKey)
        val encryptedEmail = cryptoManager.encrypt(password.email, masterKey)
        dao.updatePassword(password.copy(
                encryptedPassword = encryptedPassword,
                email = encryptedEmail
            )
        )
    }

    suspend fun deletePassword(password: PasswordEntity) = dao.deletePassword(password)
    fun getAllPasswords(): Flow<List<PasswordEntity>> = dao.getAllPasswords()
}