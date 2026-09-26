package com.scrux.gacrux.feature.passwords.domain

import com.scrux.gacrux.feature.passwords.data.EntryType

data class PasswordDetail(
    val id: Long,
    val password: String,
    val email: String,
    val webOrApp: String,
    val entryType: EntryType,
    val lastModified: Long
)