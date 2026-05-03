package com.hsharz.redline.core.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class KeyStoreManager {
    fun encrypt(data: ByteArray): ByteArray {

    }

    fun decrypt(data: ByteArray): ByteArray {

    }

    private fun getOrCreateKey(): SecretKey {
    }
}