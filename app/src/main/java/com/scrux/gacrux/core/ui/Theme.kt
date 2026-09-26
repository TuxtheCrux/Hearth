package com.scrux.gacrux.core.ui

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val DarkColorScheme = darkColorScheme(
    primary = GacruxRed,
    onPrimary = AcruxBright,
    primaryContainer = GacruxRedDim,
    onPrimaryContainer = AcruxBright,

    secondary = GacruxRed,
    onSecondary = Coalsack,
    secondaryContainer = GacruxRedDim,
    onSecondaryContainer = AcruxWhite,

    tertiary = GacruxRed,
    onTertiary = Coalsack,
    tertiaryContainer = NightBlueHigh,
    onTertiaryContainer = AcruxWhite,

    //Flächen
    background = Coalsack,
    onBackground = AcruxWhite,
    surface = NightDeep,
    onSurface = AcruxWhite,
    surfaceVariant = NightBlue,
    onSurfaceVariant = StarDust,

    //Elevations-Stufen
    surfaceContainerLowest = Coalsack,
    surfaceContainerLow = NightDeep,
    surfaceContainer = NightBlue,
    surfaceContainerHigh = NightBlueHigh,
    surfaceContainerHighest = Horizon,
    surfaceTint = Horizon,

    //Ränder & Divider
    outline = Horizon,
    outlineVariant = NightBlue,

    //Fehlerzustände
    error = NebulaViolet,
    onError = Coalsack,
    errorContainer = Color(0xFF5C1A1A),
    onErrorContainer = Color(0xFFFFD9D6),

    //Snack bars
    inverseSurface = AcruxWhite,
    inverseOnSurface = Coalsack,
    inversePrimary = GacruxRedDim,
)

@Composable
fun RedlineTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val controller = WindowCompat.getInsetsController(window, view)
            controller.isAppearanceLightStatusBars = false   // false = helle Icons
            controller.isAppearanceLightNavigationBars = false
        }
    }
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}