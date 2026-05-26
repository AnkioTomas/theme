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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.preview.PreviewHost
import net.ankio.theme.settings.ThemeSettingClick

@PreviewAll
@Composable
private fun ThemeSettingClickPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemeSettingClick(
            title = "ThemeSettingClick",
            summary = "点击跳转",
            onClick = {},
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
