/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.preview.PreviewHost
import net.ankio.theme.settings.ThemeSectionHeader
import net.ankio.theme.settings.ThemeSettingDropdown

@PreviewAll
@Composable
private fun ThemeSectionHeaderPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemeSectionHeader(text = "分区标题")
    }
}

@PreviewAll
@Composable
private fun ThemeSettingDropdownPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    var index by remember { mutableIntStateOf(0) }
    PreviewHost(config) {
        ThemeSettingDropdown(
            items = listOf("选项 A", "选项 B"),
            selectedIndex = index,
            onSelectedIndexChange = { index = it },
            title = "ThemeSettingDropdown",
            summary = "下拉选择",
            startAction = {
                ThemeIcon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = null,
                    tint = AnkioTheme.colorScheme.primary,
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
