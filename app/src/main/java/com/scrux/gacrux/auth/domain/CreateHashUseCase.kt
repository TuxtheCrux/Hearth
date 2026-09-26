package com.scrux.gacrux.auth.domain

import com.scrux.gacrux.auth.data.AuthRepository
import com.scrux.gacrux.core.security.AuthCryption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateHashUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val authCryption: AuthCryption
) {
    suspend fun invoke(masterKey: CharArray, username: String) {
        val authPair = withContext(Dispatchers.Default) {
            authCryption.generateHash(masterKey)
        }
        authRepository.saveCredentials(username, authPair.first, authPair.second)
    }
}