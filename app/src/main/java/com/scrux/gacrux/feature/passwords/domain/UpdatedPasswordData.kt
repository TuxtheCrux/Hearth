package com.scrux.gacrux.feature.passwords.domain

data class UpdatedPasswordData(
    val id: Long,
    val newPassword: String,
    val newEmail: String,
    val newWebOrApp: String
)