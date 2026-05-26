/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import net.ankio.theme.PreviewAllScreen
import net.ankio.theme.PreviewAllThemes
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.settings.UiSettingsOptions
import net.ankio.theme.settings.UiSettingsScreen

@PreviewAllScreen
@Composable
private fun UiSettingsScreenPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    val previewOptions = UiSettingsOptions(
        uiModeEntries = listOf("miuix" to "Miuix", "material" to "Material"),
        colorModeEntries = listOf(
            0 to "Follow system (MIUI)",
            1 to "Light",
            2 to "Dark",
            3 to "Follow system (Monet)",
            4 to "Light (Monet)",
            5 to "Dark (Monet)",
        ),
        themeColorEntries = emptyList(),
    )

    PreviewAllThemes(config) {
        UiSettingsScreen(
            options = previewOptions,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}
