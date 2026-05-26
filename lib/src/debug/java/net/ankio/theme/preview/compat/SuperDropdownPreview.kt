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
import net.ankio.theme.compat.ThemeSuperDropdown
import net.ankio.theme.preview.PreviewHost

@PreviewAll
@Composable
private fun ThemeSuperDropdownPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    var index by remember { mutableIntStateOf(0) }
    PreviewHost(config) {
        ThemeSuperDropdown(
            items = listOf("选项 A", "选项 B"),
            selectedIndex = index,
            onSelectedIndexChange = { index = it },
            title = "ThemeSuperDropdown",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
