/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.sheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import net.ankio.theme.PreviewAllScreen
import net.ankio.theme.PreviewAllThemes
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemePrimaryButton
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.sheet.ThemeBottomSheet

@PreviewAllScreen
@Composable
private fun ThemeBottomSheetPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewAllThemes(config) {
        ThemeBottomSheet(onDismissRequest = {}) { dismiss ->
            ThemeText(
                text = "ThemeBottomSheet",
                style = AnkioTheme.textStyles.title3,
                color = AnkioTheme.colorScheme.onSurface,
            )
            ThemePrimaryButton(
                onClick = dismiss,
                text = "关闭",
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
