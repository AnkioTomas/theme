/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.sheet

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.zIndex
import kotlinx.coroutines.flow.first

/**
 * [ThemeSheet.show] 全屏 overlay：遮罩与内容分别用 M3 对齐的 spring，内容仅位移（不叠 fade）。
 */
@Composable
internal fun AnimatedSheetOverlay(
    visible: Boolean,
    shape: ThemeSheetShape,
    cancelable: Boolean,
    onDismissRequest: () -> Unit,
    onExitComplete: () -> Unit,
    content: @Composable (dismiss: () -> Unit) -> Unit,
) {
    val dismiss = onDismissRequest
    val transitionState = remember { MutableTransitionState(false) }
    LaunchedEffect(visible) {
        transitionState.targetState = visible
    }
    val transition = rememberTransition(transitionState, label = "themeSheetOverlay")

    val scrimAlpha by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) ThemeSheetMotion.scrimShowSpec
            else ThemeSheetMotion.scrimHideSpec
        },
        label = "scrimAlpha",
    ) { shown -> if (shown) ThemeSheetMotion.ScrimAlpha else 0f }

    val slideProgress by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) ThemeSheetMotion.sheetShowSpec
            else ThemeSheetMotion.sheetHideSpec
        },
        label = "sheetSlide",
    ) { shown -> if (shown) 0f else 1f }

    // 首次组合时 visible 为 false，不能触发 onExitComplete，否则刚 addView 就被 detach
    var hasEntered by remember { mutableStateOf(false) }
    LaunchedEffect(visible) {
        if (visible) {
            hasEntered = true
        } else if (hasEntered) {
            snapshotFlow { transition.isRunning }.first { running -> !running }
            onExitComplete()
        }
    }

    var sheetHeightPx by remember { mutableIntStateOf(0) }

    Box(Modifier.fillMaxSize().imePadding()) {
        if (cancelable) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = scrimAlpha))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        enabled = visible,
                        onClick = dismiss,
                    ),
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(1f)
                .onSizeChanged { sheetHeightPx = it.height }
                .graphicsLayer {
                    translationY = slideProgress * sheetHeightPx
                },
        ) {
            SheetContainer(shape = shape) { content(dismiss) }
        }
    }
}
