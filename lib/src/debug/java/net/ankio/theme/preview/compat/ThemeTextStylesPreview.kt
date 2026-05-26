/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.compat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAllTall
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.preview.PreviewHost

@PreviewAllTall
@Composable
private fun ThemeTextStylesPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        val styles = AnkioTheme.textStyles
        val onSurface = AnkioTheme.colorScheme.onSurface
        val onVariant = AnkioTheme.colorScheme.onSurfaceVariant
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            ThemeText("title1", styles.title1, onSurface)
            ThemeText("title2", styles.title2, onSurface)
            ThemeText("title3", styles.title3, onSurface)
            ThemeText("title4", styles.title4, onSurface)
            ThemeText("headline1", styles.headline1, onSurface)
            ThemeText("headline2", styles.headline2, onSurface)
            ThemeText("subtitle", styles.subtitle, onVariant)
            ThemeText("main", styles.main, onSurface)
            ThemeText("paragraph", styles.paragraph, onSurface)
            ThemeText("body1", styles.body1, onSurface)
            ThemeText("body2", styles.body2, onSurface)
            ThemeText("button", styles.button, AnkioTheme.colorScheme.primary)
            ThemeText("footnote1", styles.footnote1, onVariant)
            ThemeText("footnote2", styles.footnote2, onVariant)
        }
    }
}
