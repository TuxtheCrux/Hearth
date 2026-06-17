package com.hsharz.redline.feature.passwords.domain

import com.hsharz.redline.R
import javax.inject.Inject

class PasswordStrengthCalcUseCase @Inject constructor() {
    enum class PasswordStrengthType(val color: String, val label: String, val iconRes: Int) {
        WEAK(color = "#E63946", label = "Weak password", iconRes = R.drawable.ic_strength_weak),
        OK(color = "#E0A52E", label = "Ok password", iconRes = R.drawable.ic_strength_ok),
        STRONG(
            color = "#3FB950",
            label = "Strong password",
            iconRes = R.drawable.ic_strength_strong
        )
    }

    fun invoke(password: String): PasswordStrengthType {
        var score = 0
        if (password.length >= 9) score++
        if (password.contains(Regex("[^A-Za-z0-9]"))) score++
        if (password.groupingBy { it }.eachCount().values.all { it <= 2 }) score++
        if (
            !password.contains(
                Regex(
                    "(\\d{2}\\.\\d{2}\\.\\d{4}|\\d{2}/\\d{2}/\\d{4}|\\d{8}|\\d{4}-\\d{2}-\\d{2})"
                )
            )
        ) score++
        if (score >= 4) return PasswordStrengthType.STRONG
        return if (score == 3) PasswordStrengthType.OK
        else PasswordStrengthType.WEAK
    }
}