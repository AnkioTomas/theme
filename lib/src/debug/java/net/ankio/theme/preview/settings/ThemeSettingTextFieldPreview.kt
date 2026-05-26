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
import net.ankio.theme.preview.PreviewHost
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
            title = "ThemeSettingTextField",
            summary = "输入说明",
            placeholder = "请输入",
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
