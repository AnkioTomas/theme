/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.QrCodeScanner
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
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.preview.PreviewHost
import net.ankio.theme.settings.ThemeSettingComboField

@PreviewAll
@Composable
private fun ThemeSettingComboFieldPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    var index by remember { mutableIntStateOf(1) }
    PreviewHost(config) {
        ThemeSettingComboField(
            items = listOf("默认", "蓝色", "绿色"),
            selectedIndex = index,
            onSelectedIndexChange = { index = it },
            title = "主题色",
            placeholder = "请选择",
            startAction = { SettingFieldStartIcon(Icons.Filled.Palette) },
            fieldEndAction = {
                ThemeIconButton(onClick = { index = 0 }) {
                    ThemeIcon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "重置",
                        tint = AnkioTheme.colorScheme.onSurfaceVariant,
                    )
                }
            },
            endAction = {
                Row {
                    ThemeIconButton(onClick = {}) {
                        ThemeIcon(
                            imageVector = Icons.Filled.QrCodeScanner,
                            contentDescription = "扫码",
                            tint = AnkioTheme.colorScheme.primary,
                        )
                    }
                    ThemeIconButton(onClick = {}) {
                        ThemeIcon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "更多",
                            tint = AnkioTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
