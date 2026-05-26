/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.PreviewAllThemes
import net.ankio.theme.ThemePreviewConfig

/** 单组件预览通用容器：8 主题由 [config] 注入。 */
@Composable
internal fun PreviewHost(
    config: ThemePreviewConfig,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    PreviewAllThemes(config) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            content()
        }
    }
}
