package com.example.basecomposeproject.compose

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed

@Composable
fun Spinner(
    color: Color = MaterialTheme.colorScheme.primary,
    dotWidth: Dp = 4.dp,
    dotSize: Dp = 10.dp,
    totalDot: Int = 10,
    durationMillis: Int = 1000,
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition(label = "Spinner")

    val progress by transition.animateValue(
        initialValue = 0,
        targetValue = totalDot,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        typeConverter = Int.VectorConverter,
        label = "SpinnerProgress",
    )

    val colors = remember(totalDot, color) {
        buildList {
            repeat(totalDot) {
                add(color.copy(alpha = ((it + 1) / totalDot.toFloat()).coerceAtMost(1f)))
            }
        }
    }

    val startAngle = -90
    val stepAngle = 360f / (totalDot)
    Canvas(modifier = modifier) {
        val width = size.width
        val dotSizePx = dotSize.toPx()
        val dotWidthPx = dotWidth.toPx()
        colors.fastForEachIndexed { i, color ->
            val angle = startAngle - ((i + progress) % totalDot) * stepAngle
            rotate(angle) {
                drawRoundRect(
                    color = color,
                    topLeft = Offset(width - dotSizePx, width / 2 - dotWidthPx / 2),
                    size = Size(width = dotSizePx, height = dotWidthPx),
                    cornerRadius = CornerRadius(dotWidthPx * 0.4f),
                )
            }
        }
    }
}

@Preview
@Composable
private fun SpinnerPreview() {
    Spinner(
        dotSize = 22.dp,
        dotWidth = 11.dp,
        totalDot = 13,
        color = Color.White,
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape),
    )
}
