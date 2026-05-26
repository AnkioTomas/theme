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
import net.ankio.theme.compat.ThemeCircularProgressIndicator
import net.ankio.theme.compat.ThemeLinearProgressIndicator
import net.ankio.theme.preview.PreviewHost

@PreviewAll
@Composable
private fun ThemeLinearProgressIndicatorPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemeLinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}

@PreviewAll
@Composable
private fun ThemeCircularProgressIndicatorPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        ThemeCircularProgressIndicator()
    }
}
