package com.hsharz.redline.feature.passwords.domain

data class PasswordDetail(
    val id: Long,
    val password: String,
    val email: String,
    val webOrApp: String,
    val lastModified: Long
)