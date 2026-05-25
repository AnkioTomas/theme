/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.preview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import net.ankio.theme.AppSettings
import net.ankio.theme.ColorMode
import net.ankio.theme.PreviewAll
import net.ankio.theme.PreviewAllThemes
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.UiMode
import net.ankio.theme.seedColorFromThemeKey

private val demoSeed = seedColorFromThemeKey("MATERIAL_DEFAULT")

/** 2 种常用主题：Miuix 浅色 + Material 浅色 */
class DemoPreviewParameterProvider : PreviewParameterProvider<ThemePreviewConfig> {
    private val configs = listOf(
        ThemePreviewConfig(
            name = "Miuix Light",
            appSettings = AppSettings(ColorMode.LIGHT, demoSeed),
            darkConfig = false,
            uiMode = UiMode.Miuix,
        ),
        ThemePreviewConfig(
            name = "Material Light",
            appSettings = AppSettings(ColorMode.LIGHT, demoSeed),
            darkConfig = false,
            uiMode = UiMode.Material,
        ),
    )

    override val values = configs.asSequence()

    override fun getDisplayName(index: Int): String? = configs.getOrNull(index)?.name
}

@PreviewAll
@Composable
private fun DemoAppLayoutPreview(
    @PreviewParameter(DemoPreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewAllThemes(config) {
        DemoAppScaffold(
            pageTitle = "Theme Demo",
            modifier = Modifier.fillMaxSize(),
        )
    }
}
