/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-3.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package net.ankio.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

private val previewSeed = seedColorFromThemeKey("MATERIAL_DEFAULT")
private val previewSeedRed = seedColorFromThemeKey("MATERIAL_RED")

/** 主题预览配置，供 @PreviewAll 使用 */
data class ThemePreviewConfig(
    val name: String,
    val appSettings: AppSettings,
    val darkConfig: Boolean,
    val uiMode: UiMode = UiMode.Miuix
) {
    override fun toString() = name
}

/** 8 种主题预览：4 种 ColorMode × 2 种 UI 风格（Miuix / Material） */
class ThemePreviewParameterProvider : PreviewParameterProvider<ThemePreviewConfig> {
    private val configs = listOf(
        ThemePreviewConfig(
            "Miuix Light",
            AppSettings(ColorMode.LIGHT, previewSeed),
            false,
            UiMode.Miuix
        ),
        ThemePreviewConfig(
            "Miuix Dark",
            AppSettings(ColorMode.DARK, previewSeed),
            true,
            UiMode.Miuix
        ),
        ThemePreviewConfig(
            "Miuix MonetLight",
            AppSettings(ColorMode.MONET_LIGHT, previewSeed),
            false,
            UiMode.Miuix
        ),
        ThemePreviewConfig(
            "Miuix MonetDark",
            AppSettings(ColorMode.MONET_DARK, previewSeed),
            true,
            UiMode.Miuix
        ),
        ThemePreviewConfig(
            "Material Light",
            AppSettings(ColorMode.LIGHT, previewSeed),
            false,
            UiMode.Material
        ),
        ThemePreviewConfig(
            "Material Dark",
            AppSettings(ColorMode.DARK, previewSeed),
            true,
            UiMode.Material
        ),
        ThemePreviewConfig(
            "Material MonetLight",
            AppSettings(ColorMode.MONET_LIGHT, previewSeedRed),
            false,
            UiMode.Material
        ),
        ThemePreviewConfig(
            "Material MonetDark",
            AppSettings(ColorMode.MONET_DARK, previewSeedRed),
            true,
            UiMode.Material
        ),
    )

    override val values = configs.asSequence()

    /** 预览面板显示名称，替代默认的 config0/config1... */
    override fun getDisplayName(index: Int): String? = configs.getOrNull(index)?.name
}

/**
 * 8 种主题预览的注解。
 *
 * 用法：
 * ```
 * @PreviewAll
 * @Composable
 * fun XxxPreview(
 *     @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig
 * ) {
 *     PreviewAllThemes(config) { XxxContent() }
 * }
 * ```
 */
/** 单组件预览视口（约手机宽，足够放下一个控件/卡片）。 */
@Preview(
    showSystemUi = false,
    widthDp = 390,
    heightDp = 320,
)
annotation class PreviewAll

/** 纵向内容较多的预览（排版表、多行 Toast、色板等）。 */
@Preview(
    showSystemUi = false,
    widthDp = 390,
    heightDp = 560,
)
annotation class PreviewAllTall

/** 整页/大区块预览（设置页、BottomSheet 等）。 */
@Preview(
    showSystemUi = false,
    widthDp = 390,
    heightDp = 844,
)
annotation class PreviewAllScreen

/**
 * 主题预览包装器，根据 config 提供 Configuration、LocalUiMode 和 AutoTheme。
 */
@Composable
fun PreviewAllThemes(
    config: ThemePreviewConfig,
    content: @Composable () -> Unit
) {
    val ctx = LocalContext.current
    ThemeSettings.init(ctx)
    val baseConfig = LocalConfiguration.current
    val overrideConfig = Configuration(baseConfig).apply {
        uiMode = if (config.darkConfig) Configuration.UI_MODE_NIGHT_YES
        else Configuration.UI_MODE_NIGHT_NO
    }
    CompositionLocalProvider(LocalConfiguration provides overrideConfig) {
        CompositionLocalProvider(LocalUiMode provides config.uiMode) {
            AutoTheme(appSettings = config.appSettings) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    content()
                }
            }
        }
    }
}

