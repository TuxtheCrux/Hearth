package com.hsharz.redline.feature.cipher.domain

import java.math.BigInteger

interface CipherAlgorithm {
    val cipherType: CipherType
    val keySpaceSize: BigInteger
    fun isValidKey(rawKey: String): Boolean
    fun encrypt(plainText: String, rawKey: String): String
    fun decrypt(cipherText: String, rawKey: String): String
    fun toAnalyzableBytes(cipherText: String): ByteArray
}