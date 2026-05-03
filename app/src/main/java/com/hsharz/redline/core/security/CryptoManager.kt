package com.hsharz.redline.core.security

//AES-GCM Verschlüsselung
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

//PBKDF2 Schlüsselableitung
import javax.crypto.spec.PBEKeySpec
import javax.crypto.SecretKeyFactory

//Zufallsgenerator für Salt und Nonce (IV)
import java.security.SecureRandom

//Base64-Kodierung für Schlüssel und Verschlüsselte Daten
import android.util.Base64


class CryptoManager {

    fun encrypt(plainText: String, masterKey: CharArray): String {
        val salt = ByteArray(32).also { SecureRandom().nextBytes(it) }
        val nonce = ByteArray(12).also { SecureRandom().nextBytes(it) }
        val kdf = PBEKeySpec(masterKey, salt, 400_000, 256)
        val encKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val secret = encKey.generateSecret(kdf)
        val aesGCM = SecretKeySpec(secret.encoded, "AES")
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(
            Cipher.ENCRYPT_MODE,
            aesGCM,
            GCMParameterSpec(
                128, nonce
            )
        )
        val encryptedData = cipher.doFinal(plainText.toByteArray())
        val blob = salt + nonce + encryptedData
        return Base64.encodeToString(blob, Base64.DEFAULT)
    }

    fun decrypt(encryptedText: String, masterKey: CharArray): String {
        val blob = Base64.decode(encryptedText, Base64.DEFAULT)
        val salt = blob.copyOfRange(0, 32)
        val nonce = blob.copyOfRange(32, 44)
        val cipherText = blob.copyOfRange(44, blob.size)

        val kdf = PBEKeySpec(masterKey, salt, 400_000, 256)
        val encKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val secret = encKey.generateSecret(kdf)
        val aesGCM = SecretKeySpec(secret.encoded, "AES")
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(
            Cipher.DECRYPT_MODE,
            aesGCM,
            GCMParameterSpec(128, nonce)
        )
        return String(cipher.doFinal(cipherText))
    }
}