/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.ankio.theme.PreviewAllThemes
import net.ankio.theme.ThemePreviewConfig

/** 单组件预览通用容器：8 主题由 [config] 注入，占满 [@PreviewAll] 视口。 */
@Composable
internal fun PreviewHost(
    config: ThemePreviewConfig,
    modifier: Modifier = Modifier,
    contentPadding: Dp = 16.dp,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable () -> Unit,
) {
    PreviewAllThemes(config) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding),
            contentAlignment = contentAlignment,
        ) {
            content()
        }
    }
}
