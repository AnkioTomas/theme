/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.compat

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ButtonGroupPosition
import net.ankio.theme.compat.ThemeButtonGroup
import net.ankio.theme.compat.ThemeButtonStyle
import net.ankio.theme.compat.ThemeGroupButton
import net.ankio.theme.preview.PreviewHost

@PreviewAll
@Composable
private fun ThemeButtonGroupPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemeButtonGroup(modifier = Modifier.fillMaxWidth()) {
            ThemeGroupButton(
                onClick = {},
                text = "左",
                modifier = Modifier.weight(1f),
                position = ButtonGroupPosition.Start,
                style = ThemeButtonStyle.Secondary,
            )
            ThemeGroupButton(
                onClick = {},
                text = "右",
                modifier = Modifier.weight(1f),
                position = ButtonGroupPosition.End,
                style = ThemeButtonStyle.Primary,
            )
        }
    }
}
