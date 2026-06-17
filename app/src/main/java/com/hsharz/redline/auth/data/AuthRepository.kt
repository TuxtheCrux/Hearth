package com.hsharz.redline.auth.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit


class AuthRepository @Inject constructor(@ApplicationContext private val context: Context) {
    val redlinePrefs = context.getSharedPreferences("RedlinePrefs", Context.MODE_PRIVATE)

    fun saveCredentials(username: String, salt: String, hash: String) {
        redlinePrefs.edit {
            putString(username + "_hash", hash)
            putString(username + "_salt", salt)
        }
    }

    fun getCredentials(username: String): Pair<String, String>? {
        val hashedCredential = redlinePrefs.getString(username + "_hash", null) ?: return null
        val credentialSalt = redlinePrefs.getString(username + "_salt", null) ?: return null
        return Pair(credentialSalt, hashedCredential)
    }

    fun clearPreferences() {
        redlinePrefs.edit {
            clear()
        }
    }
}