/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAll
import net.ankio.theme.PreviewAllTall
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.preview.PreviewHost
import net.ankio.theme.settings.SettingCardPosition
import net.ankio.theme.settings.ThemeSettingTextField

@PreviewAll
@Composable
private fun ThemeSettingTextFieldPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    var value by remember { mutableStateOf("") }
    PreviewHost(config) {
        ThemeSettingTextField(
            value = value,
            onValueChange = { value = it },
            title = "邮箱",
            summary = "仅 leadingIcon，无右侧按钮",
            placeholder = "name@example.com",
            startAction = { SettingFieldStartIcon(Icons.Filled.Email) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@PreviewAll
@Composable
private fun ThemeSettingTextFieldWithActionsPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    var value by remember { mutableStateOf("ankio@ankio.net") }
    PreviewHost(config) {
        ThemeSettingTextField(
            value = value,
            onValueChange = { value = it },
            title = "邮箱",
            summary = "fieldEndAction 框内清除；endAction 框右侧扫码（均在卡片内）",
            placeholder = "name@example.com",
            startAction = { SettingFieldStartIcon(Icons.Filled.Email) },
            fieldEndAction = {
                ThemeIconButton(onClick = { value = "" }) {
                    ThemeIcon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "清除",
                        tint = AnkioTheme.colorScheme.onSurfaceVariant,
                    )
                }
            },
            endAction = {
                ThemeIconButton(onClick = {}) {
                    ThemeIcon(
                        imageVector = Icons.Filled.QrCodeScanner,
                        contentDescription = "扫码",
                        tint = AnkioTheme.colorScheme.primary,
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@PreviewAllTall
@Composable
private fun ThemeSettingTextFieldGroupedPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    var top by remember { mutableStateOf("192.168.1.1") }
    var bottom by remember { mutableStateOf("8080") }
    PreviewHost(config, contentPadding = 12.dp) {
        Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
            ThemeSettingTextField(
                value = top,
                onValueChange = { top = it },
                title = "主机",
                startAction = { SettingFieldStartIcon(Icons.Filled.Email) },
                endAction = {
                    ThemeIconButton(onClick = {}) {
                        ThemeIcon(
                            imageVector = Icons.Filled.QrCodeScanner,
                            contentDescription = "扫码",
                            tint = AnkioTheme.colorScheme.primary,
                        )
                    }
                },
                position = SettingCardPosition.First,
                modifier = Modifier.fillMaxWidth(),
            )
            ThemeSettingTextField(
                value = bottom,
                onValueChange = { bottom = it },
                title = "端口",
                startAction = { SettingFieldStartIcon(Icons.Filled.Email) },
                fieldEndAction = {
                    ThemeIconButton(onClick = { bottom = "" }) {
                        ThemeIcon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "清除",
                            tint = AnkioTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
                position = SettingCardPosition.Last,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
