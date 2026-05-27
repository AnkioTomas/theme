/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Percent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.ThemeSettings
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.preview.PreviewHost
import net.ankio.theme.settings.ThemeSettingSlider

@PreviewAll
@Composable
private fun ThemeSettingSliderPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    var displayPercentage by remember { mutableIntStateOf(100) }
    PreviewHost(config) {
        ThemeSettingSlider(
            title = "显示比例",
            summary = "与 UiSettingsScreen 相同：拖动改本地值，松手再持久化",
            value = displayPercentage.toFloat(),
            onValueChange = { displayPercentage = ThemeSettings.snapDisplayPercentage(it) },
            valueRange = ThemeSettings.displayPercentageValueRange,
            steps = ThemeSettings.displayPercentageSliderSteps,
            valueLabel = "$displayPercentage%",
            startAction = {
                ThemeIcon(
                    imageVector = Icons.Filled.Percent,
                    contentDescription = null,
                    tint = AnkioTheme.colorScheme.primary,
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
