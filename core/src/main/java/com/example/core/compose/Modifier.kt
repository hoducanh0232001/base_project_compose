package com.example.core.compose

import android.graphics.BlurMaskFilter
import android.os.SystemClock
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private enum class ItemState { Pressed, Idle }

fun Modifier.bounceClick(
    enabled: Boolean = true,
    scaleDown: Float = 0.96f,
    onClick: () -> Unit,
) = composed {
    if (enabled.not()) return@composed this
    var itemState by remember { mutableStateOf(ItemState.Idle) }
    val scale by animateFloatAsState(
        targetValue = if (itemState == ItemState.Pressed) scaleDown else 1f,
        label = "bounceClick",
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            enabled = enabled,
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick,
        )
        .pointerInput(itemState) {
            awaitPointerEventScope {
                itemState = if (itemState == ItemState.Pressed) {
                    waitForUpOrCancellation()
                    ItemState.Idle
                } else {
                    awaitFirstDown(false)
                    ItemState.Pressed
                }
            }
        }
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.bounceCombinedClickable(
    enabled: Boolean = true,
    scaleDown: Float = 0.96f,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
) = composed {
    val interactionSource = remember { MutableInteractionSource() }

    val animatable = remember {
        Animatable(1f)
    }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> animatable.animateTo(scaleDown)
                is PressInteraction.Release -> animatable.animateTo(1f)
                is PressInteraction.Cancel -> animatable.animateTo(1f)
            }
        }
    }

    Modifier
        .graphicsLayer {
            val scale = animatable.value
            scaleX = scale
            scaleY = scale
        }
        .combinedClickable(
            enabled = enabled,
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick,
            onLongClick = onLongClick,
            onDoubleClick = onDoubleClick,
        )
}

fun Modifier.silentClick(
    enabled: Boolean = true,
    onClick: () -> Unit,
): Modifier = composed {
    clickable(
        enabled = enabled,
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick,
    )
}

fun Modifier.disableClick(): Modifier = composed {
    clickable(
        enabled = true,
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
    ) {}
}

@Composable
fun Modifier.imeHideWhenTapOutside(): Modifier = composed {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    silentClick {
        softwareKeyboardController?.hide()
    }
}

fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp,
) = this.drawBehind {
    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint()
    paint.color = color

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}

fun Modifier.safeClickable(
    interval: Int = 500,
    enabled: Boolean = true,
    onSafeClick: () -> Unit,
): Modifier = composed {
    var lastTimeClicked by remember { mutableLongStateOf(0L) }
    this.clickable(enabled = enabled) {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastTimeClicked >= interval) {
            lastTimeClicked = currentTime
            onSafeClick()
        }
    }
}

@Composable
fun Modifier.ifThen(condition: Boolean, then: @Composable Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then()
    } else {
        this
    }
}
