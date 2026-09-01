package com.hsharz.redline.feature.cipher.data

import com.hsharz.redline.feature.cipher.domain.CipherAlgorithm

internal abstract class CipherAlgorithmBase<KeyType> : CipherAlgorithm, TypedCipher<KeyType> {

    override fun isValidKey(rawKey: String): Boolean {
        return parseKey(rawKey) != null
    }

    override fun encrypt(plainText: String, rawKey: String): String {
        return encryptTyped(plainText, parseKeyOrThrow(rawKey))
    }

    override fun decrypt(cipherText: String, rawKey: String): String {
        return decryptTyped(cipherText, parseKeyOrThrow(rawKey))
    }

    private fun parseKeyOrThrow(rawKey: String): KeyType {
        return parseKey(rawKey) ?: throw IllegalArgumentException("KeyType is null $cipherType")
    }
}