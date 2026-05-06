package com.hsharz.redline.core.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.SecureRandom
import javax.inject.Inject
import androidx.core.content.edit

class KeyStoreManager @Inject constructor(@ApplicationContext private val context: Context) {
    fun encrypt(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateKey())
        val nonce = cipher.iv
        val encryptedData = nonce + cipher.doFinal(data)
        return encryptedData
    }

    fun decrypt(data: ByteArray): ByteArray {
        val nonce = data.copyOfRange(0, 12)
        val rawKey = data.copyOfRange(12, data.size)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(
            Cipher.DECRYPT_MODE,
            getOrCreateKey(),
            GCMParameterSpec(128, nonce)
        )
        return cipher.doFinal(rawKey)
    }

    private fun getOrCreateKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        val key = keyStore.getKey("RedlineKey", null)
        if (key == null) {
            val keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                "AndroidKeyStore"
            )
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                "RedlineKey",
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
            keyGenerator.init(keyGenParameterSpec)
            return keyGenerator.generateKey()
        }
        return key as SecretKey
    }


    fun getOrCreateDatabasePassword(): ByteArray {
        val redlinePrefs = context.getSharedPreferences(
            "RedlinePrefs",
            Context.MODE_PRIVATE
        )
        val redlineKey = redlinePrefs.getString("RedlineDBKey", null)
        if (redlineKey == null) {
            val password = ByteArray(32).also { SecureRandom().nextBytes(it) }
            val encryptedPassword = encrypt(password)
            redlinePrefs.edit {
                putString(
                    "RedlineDBKey", Base64.encodeToString(
                        encryptedPassword,
                        Base64.DEFAULT
                    )
                )
            }
            return password
        } else {
            val decodedKey = Base64.decode(redlineKey, Base64.DEFAULT)
            return decrypt(decodedKey)
        }
    }
}