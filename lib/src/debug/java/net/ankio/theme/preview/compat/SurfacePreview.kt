/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.compat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeSurface
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.preview.PreviewHost

@PreviewAll
@Composable
private fun ThemeSurfacePreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemeSurface(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            color = AnkioTheme.colorScheme.surfaceContainer,
        ) {
            Box(Modifier.padding(12.dp)) {
                ThemeText(
                    text = "ThemeSurface",
                    style = AnkioTheme.textStyles.body2,
                    color = AnkioTheme.colorScheme.onSurface,
                )
            }
        }
    }
}
