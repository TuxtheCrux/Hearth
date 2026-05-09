package com.hsharz.redline.auth.ui

import com.hsharz.redline.auth.domain.CreateHashUseCase
import com.hsharz.redline.auth.domain.VerifyCredentialsUseCase
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    verifyCredentialsUseCase: VerifyCredentialsUseCase,
    createHashUseCase: CreateHashUseCase
)