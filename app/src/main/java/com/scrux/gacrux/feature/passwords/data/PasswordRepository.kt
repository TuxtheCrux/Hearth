package com.scrux.gacrux.feature.passwords.data

import com.scrux.gacrux.core.security.CryptoManager
import com.scrux.gacrux.core.security.SessionManager
import com.scrux.gacrux.feature.passwords.domain.UpdatedPasswordData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PasswordRepository @Inject constructor(
    private val dao: PasswordDao,
    private val cryptoManager: CryptoManager,
    private val sessionManager: SessionManager
) {
    suspend fun insertPassword(password: PasswordEntity) {
        val masterKey = sessionManager.getMasterKeyCopy()
        try {
            val (encryptedPassword, encryptedEmail) = withContext(Dispatchers.Default) {
                val plainPassword = cryptoManager.encrypt(password.encryptedPassword, masterKey)
                val email = cryptoManager.encrypt(password.email, masterKey)
                Pair(plainPassword, email)
            }
            dao.insertPassword(
                password.copy(
                    encryptedPassword = encryptedPassword,
                    email = encryptedEmail
                )
            )
        } finally {
            masterKey.fill('\u0000')
        }
    }

    suspend fun updatePassword(updatedPasswordData: UpdatedPasswordData) {
        val masterKey = sessionManager.getMasterKeyCopy()
        try {
            val (encryptedPassword, encryptedEmail) = withContext(Dispatchers.Default) {
                val plainPassword = cryptoManager.encrypt(
                    updatedPasswordData.newPassword,
                    masterKey
                )
                val email = cryptoManager.encrypt(updatedPasswordData.newEmail, masterKey)
                Pair(plainPassword, email)
            }
            val passwordToUpdate = dao.getPasswordById(updatedPasswordData.id)
            dao.updatePassword(
                passwordToUpdate!!.copy(
                    encryptedPassword = encryptedPassword,
                    email = encryptedEmail,
                    websiteOrApp = updatedPasswordData.newWebOrApp,
                    lastModified = System.currentTimeMillis()
                )
            )
        } finally {
            masterKey.fill('\u0000')
        }
    }

    suspend fun deletePassword(passwordId: Long) = dao.deletePassword(passwordId)
    fun getAllPasswords(): Flow<List<PasswordEntity>> = dao.getAllPasswords()
    suspend fun getPasswordById(id: Long): PasswordEntity? = dao.getPasswordById(id)
}