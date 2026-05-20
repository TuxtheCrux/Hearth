package com.hsharz.redline.auth.domain

import com.hsharz.redline.auth.data.AuthRepository
import com.hsharz.redline.core.security.AuthCryption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VerifyCredentialsUseCase @Inject constructor(
    private val authCryption: AuthCryption,
    private val authRepository: AuthRepository
) {
    suspend fun invoke(masterKey: CharArray, username: String): Boolean {
        val credentials = authRepository.getCredentials(username) ?: return false
        return withContext(Dispatchers.Default) {
            authCryption.verify(
                masterKey,
                credentials.first,
                credentials.second
            )
        }
    }
}