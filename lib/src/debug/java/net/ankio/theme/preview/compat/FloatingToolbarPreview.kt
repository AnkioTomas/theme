/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.compat

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeFloatingToolbar
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.preview.PreviewHost

@PreviewAll
@Composable
private fun ThemeFloatingToolbarPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemeFloatingToolbar {
            ThemeIconButton(onClick = {}) {
                ThemeIcon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = AnkioTheme.colorScheme.primary,
                )
            }
            ThemeText(
                text = "Toolbar",
                style = AnkioTheme.textStyles.body1,
                color = AnkioTheme.colorScheme.onSurface,
            )
            ThemeIconButton(onClick = {}) {
                ThemeIcon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = AnkioTheme.colorScheme.primary,
                )
            }
        }
    }
}
