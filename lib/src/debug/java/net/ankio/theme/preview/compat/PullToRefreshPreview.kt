/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.compat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemePullToRefresh
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.preview.PreviewHost

@PreviewAll
@Composable
private fun ThemePullToRefreshPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemePullToRefresh(
            isRefreshing = false,
            onRefresh = {},
            modifier = Modifier.fillMaxWidth().height(120.dp),
        ) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                ThemeText(
                    text = "下拉刷新内容",
                    style = AnkioTheme.textStyles.body2,
                    color = AnkioTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
