package com.example.core.compose

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsIgnoringVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsIgnoringVisibility
import androidx.compose.foundation.layout.union
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
val WindowInsets.Companion.safeArea: WindowInsets
    @Composable
    get() = WindowInsets.statusBarsIgnoringVisibility
        .union(WindowInsets.navigationBarsIgnoringVisibility)
        .union(WindowInsets.displayCutout)

@Composable
fun safeAreaStartPadding(): Dp {
    return WindowInsets.safeArea.asPaddingValues()
        .calculateStartPadding(LocalLayoutDirection.current)
}

@Composable
fun safeAreaEndPadding(): Dp {
    return WindowInsets.safeArea.asPaddingValues()
        .calculateEndPadding(LocalLayoutDirection.current)
}

@Composable
fun safeAreaBottomPadding(): Dp {
    return WindowInsets.safeArea.asPaddingValues().calculateBottomPadding()
}

@Composable
fun safeAreaTopPadding(): Dp {
    return WindowInsets.safeArea.asPaddingValues().calculateTopPadding()
}

@Composable
fun imeBottomPadding(): Dp {
    return WindowInsets.ime.asPaddingValues().calculateBottomPadding()
}

@Composable
fun Modifier.safeAreaPadding(
    top: Boolean = true,
    start: Boolean = true,
    end: Boolean = true,
    bottom: Boolean = true,
): Modifier = composed {
    if (LocalInspectionMode.current) {
        Modifier
    } else {
        Modifier.padding(
            top = if (top) safeAreaTopPadding() else 0.dp,
            start = if (start) safeAreaStartPadding() else 0.dp,
            end = if (end) safeAreaEndPadding() else 0.dp,
            bottom = if (bottom) safeAreaBottomPadding() else 0.dp,
        )
    }
}

@Composable
fun SafeAreaPaddingValues(
    top: Boolean = true,
    start: Boolean = true,
    end: Boolean = true,
    bottom: Boolean = true,
): PaddingValues {
    return if (LocalInspectionMode.current) {
        PaddingValues()
    } else {
        PaddingValues(
            top = if (top) safeAreaTopPadding() else 0.dp,
            start = if (start) safeAreaStartPadding() else 0.dp,
            end = if (end) safeAreaEndPadding() else 0.dp,
            bottom = if (bottom) safeAreaBottomPadding() else 0.dp,
        )
    }
}

operator fun PaddingValues.plus(that: PaddingValues): PaddingValues = object : PaddingValues {
    override fun calculateBottomPadding(): Dp =
        this@plus.calculateBottomPadding() + that.calculateBottomPadding()

    override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp =
        this@plus.calculateLeftPadding(layoutDirection) + that.calculateLeftPadding(layoutDirection)

    override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp =
        this@plus.calculateRightPadding(layoutDirection) + that.calculateRightPadding(
            layoutDirection,
        )

    override fun calculateTopPadding(): Dp =
        this@plus.calculateTopPadding() + that.calculateTopPadding()
}

operator fun PaddingValues.minus(that: PaddingValues): PaddingValues = object : PaddingValues {
    override fun calculateBottomPadding(): Dp =
        this@minus.calculateBottomPadding() - that.calculateBottomPadding()

    override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp =
        this@minus.calculateLeftPadding(layoutDirection) - that.calculateLeftPadding(layoutDirection)

    override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp =
        this@minus.calculateRightPadding(layoutDirection) - that.calculateRightPadding(
            layoutDirection,
        )

    override fun calculateTopPadding(): Dp =
        this@minus.calculateTopPadding() - that.calculateTopPadding()
}

fun PaddingValues.append(
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp,
) = this + PaddingValues(start, top, end, bottom)

@Composable
fun Modifier.safeAreaWithImePadding(
    top: Boolean = true,
    start: Boolean = true,
    end: Boolean = true,
    bottom: Boolean = true,
): Modifier = composed {
    if (LocalInspectionMode.current) {
        Modifier
    } else {
        Modifier.padding(
            top = if (top) safeAreaTopPadding() else 0.dp,
            start = if (start) safeAreaStartPadding() else 0.dp,
            end = if (end) safeAreaEndPadding() else 0.dp,
            bottom = if (bottom) {
                maxOf(
                    safeAreaBottomPadding(),
                    WindowInsets.ime.getBottom(LocalDensity.current).let {
                        with(LocalDensity.current) { it.toDp() }
                    },
                )
            } else {
                0.dp
            },
        )
    }
}

@Composable
fun SafeAreaWithImePaddingValues(
    top: Boolean = true,
    start: Boolean = true,
    end: Boolean = true,
    bottom: Boolean = true,
): PaddingValues {
    return if (LocalInspectionMode.current) {
        PaddingValues()
    } else {
        PaddingValues(
            top = if (top) safeAreaTopPadding() else 0.dp,
            start = if (start) safeAreaStartPadding() else 0.dp,
            end = if (end) safeAreaEndPadding() else 0.dp,
            bottom = if (bottom) {
                maxOf(
                    safeAreaBottomPadding(),
                    WindowInsets.ime.getBottom(LocalDensity.current).let {
                        with(LocalDensity.current) { it.toDp() }
                    },
                )
            } else {
                0.dp
            },
        )
    }
}
