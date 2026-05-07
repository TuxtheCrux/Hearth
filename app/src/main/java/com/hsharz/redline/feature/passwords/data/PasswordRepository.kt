package com.hsharz.redline.feature.passwords.data

import com.hsharz.redline.core.security.CryptoManager
import com.hsharz.redline.core.security.SessionManager
import com.hsharz.redline.feature.passwords.domain.UpdatedPasswordData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

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

    suspend fun updatePassword(updatedPasswordData: UpdatedPasswordData) {
        val masterKey = sessionManager.getMasterKeyCopy()
        val encryptedPassword = cryptoManager.encrypt(updatedPasswordData.newPassword, masterKey)
        val encryptedEmail = cryptoManager.encrypt(updatedPasswordData.newEmail, masterKey)
        val passwordToUpdate = dao.getPasswordById(updatedPasswordData.id)
        dao.updatePassword(
            passwordToUpdate!!.copy(
                encryptedPassword = encryptedPassword,
                email = encryptedEmail,
                websiteOrApp = updatedPasswordData.newWebOrApp,
                lastModified = System.currentTimeMillis()
            )
        )
    }

    suspend fun deletePassword(passwordId: Long) = dao.deletePassword(passwordId)
    fun getAllPasswords(): Flow<List<PasswordEntity>> = dao.getAllPasswords()
    suspend fun getPasswordById(id: Long): PasswordEntity? = dao.getPasswordById(id)
}