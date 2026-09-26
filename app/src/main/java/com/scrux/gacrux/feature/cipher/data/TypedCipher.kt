package com.scrux.gacrux.feature.cipher.data

internal interface TypedCipher<KeyType> {
    fun parseKey(raw: String): KeyType?
    fun encryptTyped(plainText: String, key: KeyType): String
    fun decryptTyped(cipherText: String, key: KeyType): String
}