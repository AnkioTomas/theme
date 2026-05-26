/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.compat

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeSuperSpinner
import net.ankio.theme.preview.PreviewHost
import top.yukonga.miuix.kmp.basic.SpinnerEntry

@PreviewAll
@Composable
private fun ThemeSuperSpinnerPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    var index by remember { mutableIntStateOf(0) }
    PreviewHost(config) {
        ThemeSuperSpinner(
            items = listOf(
                SpinnerEntry(title = "选项 A"),
                SpinnerEntry(title = "选项 B"),
            ),
            selectedIndex = index,
            onSelectedIndexChange = { index = it },
            title = "ThemeSuperSpinner",
            summary = "副标题",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
