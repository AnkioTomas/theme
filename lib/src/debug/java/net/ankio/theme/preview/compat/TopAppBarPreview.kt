/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.compat

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemeTopAppBar
import net.ankio.theme.preview.PreviewHost

@PreviewAll
@Composable
private fun ThemeTopAppBarPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config, modifier = Modifier.padding(0.dp)) {
        ThemeTopAppBar(
            title = "Title",
            navigationIcon = {
                ThemeIconButton(onClick = {}) {
                    ThemeIcon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = null,
                        tint = AnkioTheme.colorScheme.onSurface,
                    )
                }
            },
            actions = {
                ThemeIconButton(onClick = {}) {
                    ThemeIcon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = AnkioTheme.colorScheme.onSurface,
                    )
                }
            },
        )
    }
}
