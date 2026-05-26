/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAllTall
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeText

@PreviewAllTall
@Composable
private fun AppShapesPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        val shapes = MaterialTheme.shapes
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ShapeRow(
                "extraSmall" to shapes.extraSmall,
                "small" to shapes.small,
                "medium" to shapes.medium,
                "large" to shapes.large,
                "extraLarge" to shapes.extraLarge,
            )
        }
    }
}

@Composable
private fun ShapeRow(vararg entries: Pair<String, Shape>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        entries.forEach { (name, shape) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 80.dp, height = 40.dp)
                        .clip(shape)
                        .background(AnkioTheme.colorScheme.primaryContainer),
                )
                ThemeText(
                    text = name,
                    style = AnkioTheme.textStyles.footnote1,
                    color = AnkioTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
