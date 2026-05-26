/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.compat

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeCard
import net.ankio.theme.compat.ThemeLazyListPopupColumn
import net.ankio.theme.compat.ThemeListPopupColumn
import net.ankio.theme.compat.ThemeListPopupItem
import net.ankio.theme.compat.ThemeSuperListPopup
import net.ankio.theme.preview.PreviewHost

@PreviewAll
@Composable
private fun ThemeSuperListPopupPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemeSuperListPopup(show = true, onDismissRequest = {}) {
            ThemeListPopupColumn {
                ThemeListPopupItem(text = "项目 1", onClick = {})
                ThemeListPopupItem(text = "项目 2", onClick = {})
            }
        }
    }
}

@PreviewAll
@Composable
private fun ThemeListPopupColumnPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemeCard(modifier = Modifier.fillMaxWidth()) {
            ThemeListPopupColumn {
                ThemeListPopupItem(text = "列项 1", onClick = {})
                ThemeListPopupItem(text = "列项 2", onClick = {})
            }
        }
    }
}

@PreviewAll
@Composable
private fun ThemeListPopupItemPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemeCard(modifier = Modifier.fillMaxWidth()) {
            ThemeListPopupItem(text = "ThemeListPopupItem", onClick = {})
        }
    }
}

@PreviewAll
@Composable
private fun ThemeLazyListPopupColumnPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemeCard(modifier = Modifier.fillMaxWidth().height(120.dp)) {
            ThemeLazyListPopupColumn(items = listOf("一", "二", "三")) { item ->
                ThemeListPopupItem(text = item, onClick = {})
            }
        }
    }
}
