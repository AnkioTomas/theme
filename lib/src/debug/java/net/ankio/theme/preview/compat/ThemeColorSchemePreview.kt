/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.compat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.AutoThemeTokens
import net.ankio.theme.PreviewAllTall
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.preview.PreviewHost

@PreviewAllTall
@Composable
private fun ThemeColorSchemePreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        val cs = AnkioTheme.colorScheme
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeText(
                text = "colorScheme",
                style = AnkioTheme.textStyles.title4,
                color = cs.onSurface,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ColorSwatch("primary", cs.primary)
                ColorSwatch("secondary", cs.secondary)
                ColorSwatch("tertiary", cs.tertiary)
                ColorSwatch("surface", cs.surface)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ColorSwatch("primaryC", cs.primaryContainer)
                ColorSwatch("secondaryC", cs.secondaryContainer)
                ColorSwatch("tertiaryC", cs.tertiaryContainer)
                ColorSwatch("errorC", cs.errorContainer)
            }
            val extra = AutoThemeTokens.extraColors
            ThemeText(
                text = "extraColors",
                style = AnkioTheme.textStyles.title4,
                color = cs.onSurface,
            )
            SemanticSwatch("info", extra.info.text, extra.info.bg)
            SemanticSwatch("warning", extra.warning.text, extra.warning.bg)
            SemanticSwatch("error", extra.error.text, extra.error.bg)
            SemanticSwatch("success", extra.success.text, extra.success.bg)
            SemanticSwatch("debug", extra.debug.text, extra.debug.bg)
        }
    }
}

@Composable
private fun ColorSwatch(label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color),
        )
        ThemeText(
            text = label,
            style = AnkioTheme.textStyles.footnote2,
            color = AnkioTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun SemanticSwatch(label: String, text: Color, bg: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(width = 48.dp, height = 28.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(bg),
        )
        ThemeText(
            text = label,
            style = AnkioTheme.textStyles.body2,
            color = text,
        )
    }
}
