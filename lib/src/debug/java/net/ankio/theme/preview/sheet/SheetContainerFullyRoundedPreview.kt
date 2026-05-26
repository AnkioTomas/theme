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
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemePrimaryButton
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.preview.PreviewHost
import net.ankio.theme.sheet.SheetContainer
import net.ankio.theme.sheet.ThemeSheetShape

@PreviewAll
@Composable
private fun SheetContainerFullyRoundedPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config, contentPadding = 0.dp) {
        SheetContainer(shape = ThemeSheetShape.FullyRounded) {
            ThemeText(
                text = "SheetContainer · 全圆角",
                style = AnkioTheme.textStyles.title3,
                color = AnkioTheme.colorScheme.onSurface,
            )
            ThemeText(
                text = "四角 48dp · 左右留白",
                style = AnkioTheme.textStyles.body2,
                color = AnkioTheme.colorScheme.onSurfaceVariant,
            )
            ThemePrimaryButton(
                onClick = {},
                text = "操作",
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
