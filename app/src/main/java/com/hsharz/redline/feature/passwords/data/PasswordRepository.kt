package com.hsharz.redline.feature.passwords.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//TODO add encryption/decryption
class PasswordRepository @Inject constructor(private val dao: PasswordDao) {
    suspend fun insertPassword(password: PasswordEntity) = dao.insertPassword(password)
    suspend fun updatePassword(password: PasswordEntity) = dao.updatePassword(password)
    suspend fun deletePassword(password: PasswordEntity) = dao.deletePassword(password)
    fun getAllPasswords(): Flow<List<PasswordEntity>> = dao.getAllPasswords()
}