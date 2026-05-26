/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.compat

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeCustomButton
import net.ankio.theme.compat.ThemePrimaryButton
import net.ankio.theme.compat.ThemeSecondaryButton
import net.ankio.theme.preview.PreviewHost

@PreviewAll
@Composable
private fun ThemePrimaryButtonPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemePrimaryButton(onClick = {}, text = "Primary")
    }
}

@PreviewAll
@Composable
private fun ThemeSecondaryButtonPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemeSecondaryButton(onClick = {}, text = "Secondary")
    }
}

@PreviewAll
@Composable
private fun ThemeCustomButtonPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemeCustomButton(
            onClick = {},
            containerColor = AnkioTheme.colorScheme.tertiaryContainer,
            contentColor = AnkioTheme.colorScheme.onTertiaryContainer,
            text = "Custom",
        )
    }
}
