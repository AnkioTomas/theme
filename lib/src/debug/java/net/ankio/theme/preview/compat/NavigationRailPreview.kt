/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.compat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemeNavigationRail
import net.ankio.theme.preview.PreviewHost

@PreviewAll
@Composable
private fun ThemeNavigationRailPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    var index by remember { mutableIntStateOf(0) }
    PreviewHost(config) {
        Box(modifier = Modifier.fillMaxWidth().height(160.dp)) {
            Row(Modifier.fillMaxWidth()) {
                ThemeNavigationRail(modifier = Modifier.fillMaxHeight()) {
                    ThemeIconButton(onClick = { index = 0 }) {
                        ThemeIcon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = null,
                            tint = if (index == 0) AnkioTheme.colorScheme.primary
                            else AnkioTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    ThemeIconButton(onClick = { index = 1 }) {
                        ThemeIcon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = null,
                            tint = if (index == 1) AnkioTheme.colorScheme.primary
                            else AnkioTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}
