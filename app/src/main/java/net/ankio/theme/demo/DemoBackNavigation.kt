/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo

import androidx.activity.BackEventCompat
import androidx.activity.compose.PredictiveBackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

/**
 * Demo 内导航的预测性返回：分类详情右滑退出、设置 Tab 返回组件目录。
 *
 * 须配合 Manifest `android:enableOnBackInvokedCallback="true"`。
 * [PredictiveBackHandler] 必须**无条件**组合，仅用 [enabled] 控制是否生效。
 */
@Composable
fun DemoPredictiveBackNavigation(
    openCategory: Boolean,
    onSettingsTab: Boolean,
    onPopCategory: () -> Unit,
    onLeaveSettingsTab: () -> Unit,
    content: @Composable (categoryBackProgress: Float) -> Unit,
) {
    var categoryBackProgress by rememberSaveable { mutableFloatStateOf(0f) }

    PredictiveBackHandler(enabled = onSettingsTab && !openCategory) { progress ->
        handlePredictiveBack(progress, onComplete = onLeaveSettingsTab)
    }

    PredictiveBackHandler(enabled = openCategory) { progress ->
        handlePredictiveBack(
            progress = progress,
            onProgress = { categoryBackProgress = it },
            onComplete = {
                categoryBackProgress = 0f
                onPopCategory()
            },
            onCancel = { categoryBackProgress = 0f },
        )
    }

    content(categoryBackProgress)
}

private suspend fun handlePredictiveBack(
    progress: Flow<BackEventCompat>,
    onProgress: (Float) -> Unit = {},
    onComplete: () -> Unit,
    onCancel: () -> Unit = {},
) {
    try {
        progress.collectLatest { event -> onProgress(event.progress) }
        onComplete()
    } catch (e: CancellationException) {
        onCancel()
        throw e
    }
}
