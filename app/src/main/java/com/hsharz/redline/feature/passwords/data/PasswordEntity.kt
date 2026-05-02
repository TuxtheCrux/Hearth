package com.hsharz.redline.feature.passwords.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val entryType: EntryType,
    val encryptedPassword: String,
    val email: String,
    val websiteOrApp: String,
    val passkey: Boolean,
    val lastModified: Long = System.currentTimeMillis()
)