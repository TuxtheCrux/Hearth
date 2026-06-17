package com.hsharz.redline.core.ui

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val DarkColorScheme = darkColorScheme(
    primary = RedlineRed,
    onPrimary = RedlineHeading,
    primaryContainer = RedlineRedDim,
    onPrimaryContainer = RedlineHeading,

    secondary = RedlineRed,
    onSecondary = RedlineBgDeep,
    secondaryContainer = RedlineRedDim,
    onSecondaryContainer = RedlineText,

    tertiary = RedlineRed,
    onTertiary = RedlineBgDeep,
    tertiaryContainer = RedlineSurface2,
    onTertiaryContainer = RedlineText,

    //Flächen
    background = RedlineBgDeep,
    onBackground = RedlineText,
    surface = RedlineBgSlide,
    onSurface = RedlineText,
    surfaceVariant = RedlineSurface,
    onSurfaceVariant = RedlineTextMute,

    //Elevations-Stufen
    surfaceContainerLowest = RedlineBgDeep,
    surfaceContainerLow = RedlineBgSlide,
    surfaceContainer = RedlineSurface,
    surfaceContainerHigh = RedlineSurface2,
    surfaceContainerHighest = RedlineBorder,
    surfaceTint = RedlineBorder,

    //Ränder & Divider
    outline = RedlineBorder,
    outlineVariant = RedlineSurface,

    //Fehlerzustände
    error = RedlineError,
    onError = RedlineBgDeep,
    errorContainer = Color(0xFF5C1A1A),
    onErrorContainer = Color(0xFFFFD9D6),

    //Snack bars
    inverseSurface = RedlineText,
    inverseOnSurface = RedlineBgDeep,
    inversePrimary = RedlineRedDim,
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