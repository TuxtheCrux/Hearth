package com.hsharz.redline.core.security

import java.security.SecureRandom
import android.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.inject.Inject

class AuthCryption @Inject constructor() {

    fun generateHash(masterKey: CharArray): Pair<String, String> {
        val salt = ByteArray(32).also { SecureRandom().nextBytes(it) }
        val kdf = PBEKeySpec(masterKey, salt, 600_000, 256)
        val encKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val secret = encKey.generateSecret(kdf)
        kdf.clearPassword()
        val hash = secret.encoded
        val encodedSalt = Base64.encodeToString(salt, Base64.DEFAULT)
        val encodedHash = Base64.encodeToString(hash, Base64.DEFAULT)
        return Pair(encodedSalt, encodedHash)
    }

    fun verify(masterKey: CharArray, storedSalt: String, storedHash: String): Boolean {
        val decodedStoredSalt = Base64.decode(storedSalt, Base64.DEFAULT)
        val decodedStoredHash = Base64.decode(storedHash, Base64.DEFAULT)
        val kdf = PBEKeySpec(masterKey, decodedStoredSalt, 600_000, 256)
        val decKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val secret = decKey.generateSecret(kdf)
        kdf.clearPassword()
        val hash = secret.encoded
        return decodedStoredHash.contentEquals(hash)
    }
}