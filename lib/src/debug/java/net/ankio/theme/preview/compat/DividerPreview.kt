/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.compat

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeHorizontalDivider
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.compat.ThemeVerticalDivider
import net.ankio.theme.preview.PreviewHost

@PreviewAll
@Composable
private fun ThemeHorizontalDividerPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemeHorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@PreviewAll
@Composable
private fun ThemeVerticalDividerPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        Row(modifier = Modifier.height(48.dp)) {
            ThemeText(
                text = "A",
                style = AnkioTheme.textStyles.body1,
                color = AnkioTheme.colorScheme.onSurface,
            )
            ThemeVerticalDivider(modifier = Modifier.fillMaxHeight())
            ThemeText(
                text = "B",
                style = AnkioTheme.textStyles.body1,
                color = AnkioTheme.colorScheme.onSurface,
            )
        }
    }
}
