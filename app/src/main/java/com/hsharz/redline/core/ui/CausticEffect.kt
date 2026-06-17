package com.hsharz.redline.core.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.sin
import kotlin.math.cos

private val CausticLight = Color(0xFFE63946)
private val CausticHighlight = Color(0xFFFF6B6B)

/**
 * Kaustik-ähnlicher Shimmer-Effekt inspiriert vom Licht auf dem Meeresboden.
 * Mehrere überlagernde Lichtflecken bewegen sich mit unterschiedlichen
 * Geschwindigkeiten und erzeugen ein organisches, welliges Muster.
 *
 * Universal anwendbar auf jeden Modifier.
 */
@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
fun Modifier.causticShimmer(): Modifier {

    val transition = rememberInfiniteTransition(label = "caustic")

    // Hauptwelle – langsam, große Bewegung
    val wave1 by transition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 7000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave1"
    )

    // Zweite Welle – etwas schneller, andere Phase
    val wave2 by transition.animateFloat(
        initialValue = (Math.PI).toFloat(),
        targetValue = (3 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave2"
    )

    // Dritte Welle – schneller, kleine Akzente
    val wave3 by transition.animateFloat(
        initialValue = (0.5f * Math.PI).toFloat(),
        targetValue = (2.5f * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave3"
    )

    // Pulsierender Gesamt-Alpha – das "Atmen" des Lichts
    val breathAlpha by transition.animateFloat(
        initialValue = 0.04f,
        targetValue = 0.09f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breath"
    )

    return this.drawWithContent {
        drawContent()

        val w = size.width
        val h = size.height

        // Lichtfleck 1 – groß, bewegt sich in einer Lemniskate (∞-Form)
        val x1 = w * (0.3f + 0.35f * sin(wave1))
        val y1 = h * (0.5f + 0.3f * sin(wave1 * 0.7f))
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    CausticHighlight.copy(alpha = breathAlpha * 1.2f),
                    CausticLight.copy(alpha = breathAlpha * 0.5f),
                    Color.Transparent
                ),
                center = Offset(x1, y1),
                radius = w * 0.45f
            ),
            radius = w * 0.45f,
            center = Offset(x1, y1)
        )

        // Lichtfleck 2 – mittel, bewegt sich diagonal versetzt
        val x2 = w * (0.6f + 0.25f * cos(wave2))
        val y2 = h * (0.4f + 0.35f * sin(wave2 * 1.3f))
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    CausticHighlight.copy(alpha = breathAlpha),
                    CausticLight.copy(alpha = breathAlpha * 0.4f),
                    Color.Transparent
                ),
                center = Offset(x2, y2),
                radius = w * 0.35f
            ),
            radius = w * 0.35f,
            center = Offset(x2, y2)
        )

        // Lichtfleck 3 – klein, schnell, erzeugt den Netz-Effekt
        val x3 = w * (0.5f + 0.4f * sin(wave3 * 1.5f))
        val y3 = h * (0.6f + 0.25f * cos(wave3))
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    CausticHighlight.copy(alpha = breathAlpha * 0.8f),
                    Color.Transparent
                ),
                center = Offset(x3, y3),
                radius = w * 0.25f
            ),
            radius = w * 0.25f,
            center = Offset(x3, y3)
        )

        // Lichtfleck 4 – Randglow links
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    CausticLight.copy(alpha = breathAlpha * 0.6f),
                    Color.Transparent
                ),
                center = Offset(0f, h * (0.5f + 0.3f * sin(wave2 * 0.5f))),
                radius = w * 0.3f
            ),
            radius = w * 0.3f,
            center = Offset(0f, h * (0.5f + 0.3f * sin(wave2 * 0.5f)))
        )

        // Lichtfleck 5 – Randglow rechts
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    CausticLight.copy(alpha = breathAlpha * 0.6f),
                    Color.Transparent
                ),
                center = Offset(w, h * (0.5f + 0.3f * cos(wave3 * 0.7f))),
                radius = w * 0.3f
            ),
            radius = w * 0.3f,
            center = Offset(w, h * (0.5f + 0.3f * cos(wave3 * 0.7f)))
        )
    }
}