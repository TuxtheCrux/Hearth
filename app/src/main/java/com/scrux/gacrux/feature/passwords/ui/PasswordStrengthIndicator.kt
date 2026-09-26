package com.scrux.gacrux.feature.passwords.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.scrux.gacrux.feature.passwords.domain.PasswordStrengthCalcUseCase

@Composable
fun PasswordStrengthIndicator(passwordStrength: PasswordStrengthCalcUseCase.PasswordStrengthType) {


    Row(
        modifier = Modifier.padding(horizontal = 14.dp)
    ) {
        Text(
            text = passwordStrength.label,
            color = Color(passwordStrength.color.toColorInt())
        )
        Icon(
            modifier = Modifier.padding(start = 8.dp),
            painter = painterResource(id = passwordStrength.iconRes),
            contentDescription = null,
            tint = Color(passwordStrength.color.toColorInt())
        )
    }
}