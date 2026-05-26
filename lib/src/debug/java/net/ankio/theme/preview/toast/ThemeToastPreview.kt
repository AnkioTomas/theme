/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.toast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import net.ankio.theme.PreviewAllTall
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.preview.PreviewHost
import net.ankio.theme.toast.OverlayToastContent
import net.ankio.theme.toast.ThemeToast

@PreviewAllTall
@Composable
private fun ThemeToastStylesPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeToast.Style.entries.forEach { style ->
                OverlayToastContent(
                    message = style.name,
                    style = style,
                    darkTheme = config.darkConfig,
                    trailingContent = null,
                )
            }
        }
    }
}
