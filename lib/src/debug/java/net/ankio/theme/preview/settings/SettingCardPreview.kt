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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeSwitch
import net.ankio.theme.preview.PreviewHost
import net.ankio.theme.settings.SettingCard

@PreviewAll
@Composable
private fun SettingCardPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    var checked by remember { mutableStateOf(true) }
    PreviewHost(config) {
        SettingCard(
            icon = {
                ThemeIcon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = null,
                    tint = AnkioTheme.colorScheme.primary,
                )
            },
            title = "SettingCard",
            subtitle = "通用设置项卡片",
            onClick = { checked = !checked },
            trailing = {
                ThemeSwitch(
                    checked = checked,
                    onCheckedChange = { checked = it },
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@PreviewAll
@Composable
private fun SettingCardTrailingValuePreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        SettingCard(
            icon = {
                ThemeIcon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = null,
                    tint = AnkioTheme.colorScheme.primary,
                )
            },
            title = "SettingCard",
            subtitle = "下拉右侧展示当前值",
            trailingValue = "选项 B",
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
