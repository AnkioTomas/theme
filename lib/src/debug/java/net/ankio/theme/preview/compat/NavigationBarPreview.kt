/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.compat

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.PreviewParameter
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeNavigationBar
import net.ankio.theme.compat.ThemeNavigationBarItem
import net.ankio.theme.preview.PreviewHost

@PreviewAll
@Composable
private fun ThemeNavigationBarPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    var selected by remember { mutableIntStateOf(0) }
    PreviewHost(config) {
        ThemeNavigationBar {
            ThemeNavigationBarItem(
                selected = selected == 0,
                onClick = { selected = 0 },
                icon = Icons.Filled.Home,
                label = "首页",
            )
            ThemeNavigationBarItem(
                selected = selected == 1,
                onClick = { selected = 1 },
                icon = Icons.Filled.Settings,
                label = "设置",
            )
        }
    }
}
