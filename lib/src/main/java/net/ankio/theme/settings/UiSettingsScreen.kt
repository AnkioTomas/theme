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

package net.ankio.theme.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import net.ankio.theme.ColorMode
import net.ankio.theme.PreviewAll
import net.ankio.theme.PreviewAllThemes
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.ThemeSettings
import net.ankio.theme.R
import net.ankio.theme.UiMode
import net.ankio.theme.themeKeyOptions

/**
 * UI 设置数据，用于 [UiSettingsScreen] 的选项展示。
 * 各 entries 默认 `emptyList()`，表示由 lib 自动提供本地化文案与全部枚举项；
 * 调用方仅在需要裁剪选项或自定义文案时才传入。
 */
data class UiSettingsOptions(
    val uiModeEntries: List<Pair<String, String>> = emptyList(),
    val colorModeEntries: List<Pair<Int, String>> = emptyList(),
    val themeColorEntries: List<Pair<String, String>> = emptyList(),
)


@SuppressLint("RememberReturnType")
@PreviewAll
@Composable
private fun UiSettingsScreenPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    /** 预览用 UiSettingsOptions（英语） */
    val previewOptions = UiSettingsOptions(
        uiModeEntries = listOf("miuix" to "Miuix", "material" to "Material"),
        colorModeEntries = listOf(
            0 to "Follow system (MIUI)",
            1 to "Light",
            2 to "Dark",
            3 to "Follow system (Monet)",
            4 to "Light (Monet)",
            5 to "Dark (Monet)",
        ),
        themeColorEntries = emptyList(), // 使用 theme 库自带的 theme_color_xxx 多语言
    )

    PreviewAllThemes(config) {
        UiSettingsScreen(
            options = previewOptions,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}

/**
 * 独立的 UI 设置组件。
 * 展示：UI 风格、颜色模式、跟随系统强调色、主题色选择。
 * 需在 [ThemeSettings.init] 后使用，配置由 theme 库直接管理。
 *
 * @param onThemeChanged 主题变更后回调，如 setDefaultNightMode + activity.recreate()
 */
@Composable
fun UiSettingsScreen(
    options: UiSettingsOptions = UiSettingsOptions(),
    modifier: Modifier = Modifier,
    onThemeChanged: () -> Unit = {},
) {
    var uiMode by remember { mutableStateOf(ThemeSettings.uiMode) }
    var colorMode by remember { mutableStateOf(ThemeSettings.colorMode) }
    var followSystemAccent by remember { mutableStateOf(ThemeSettings.followSystemAccent) }
    var themeColor by remember { mutableStateOf(ThemeSettings.themeColor) }
    var displayPercentage by remember { mutableStateOf(ThemeSettings.displayPercentage) }

    val scrollState = rememberScrollState()

    val uiModeEntries = options.uiModeEntries.takeIf { it.isNotEmpty() } ?: defaultUiModeEntries()
    val colorModeEntries = options.colorModeEntries.takeIf { it.isNotEmpty() } ?: defaultColorModeEntries()

    // 「主题色」分组仅在颜色源真正参与渲染时显示：
    // - Material UI 引擎：始终用 keyColor（动态色或种子色），始终显示
    // - Miuix UI 引擎：仅 colorMode 是 Monet* 时使用 keyColor，其它模式（默认调色板）下隐藏，
    //   避免向用户暴露「选了不生效」的设置项
    val showThemeColorSection = UiMode.fromValue(uiMode) == UiMode.Material ||
            ColorMode.fromValue(colorMode).isMonet

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(scrollState)
            .padding(16.dp),
    ) {
        SectionHeader(text = stringResource(R.string.theme_section_style))
        UiModeSelector(
            value = uiMode,
            onValueChange = {
                uiMode = it
                ThemeSettings.uiMode = it
                onThemeChanged()
            },
            entries = uiModeEntries,
            position = SettingCardPosition.First,
        )
        ColorModeSelector(
            value = colorMode,
            onValueChange = {
                colorMode = it
                ThemeSettings.colorMode = it
                onThemeChanged()
            },
            entries = colorModeEntries,
            position = SettingCardPosition.Middle,
        )
        DisplayPercentageSlider(
            value = displayPercentage,
            onValueChange = { displayPercentage = it },
            onValueChangeFinished = {
                ThemeSettings.displayPercentage = displayPercentage
                onThemeChanged()
            },
            position = SettingCardPosition.Last,
        )

        if (showThemeColorSection) {
            SectionHeader(text = stringResource(R.string.theme_section_color))
            FollowSystemAccentSwitch(
                checked = followSystemAccent,
                onCheckedChange = {
                    followSystemAccent = it
                    ThemeSettings.followSystemAccent = it
                    onThemeChanged()
                },
                position = SettingCardPosition.First,
            )
            ThemeColorSelector(
                value = themeColor,
                onValueChange = {
                    themeColor = it
                    ThemeSettings.themeColor = it
                    followSystemAccent = false
                    onThemeChanged()
                },
                entries = options.themeColorEntries,
                themeKeyOptions = themeKeyOptions,
                onThemeChanged = onThemeChanged,
                position = SettingCardPosition.Last,
            )
        }

        Spacer(Modifier.height(4.dp))
    }
}

/** 默认 UI 引擎选项（Miuix / Material），文案走 lib 多语言资源 */
@Composable
private fun defaultUiModeEntries(): List<Pair<String, String>> = listOf(
    "miuix" to stringResource(R.string.theme_ui_mode_miuix),
    "material" to stringResource(R.string.theme_ui_mode_material),
)

/** 默认颜色模式选项（7 项 ColorMode），文案走 lib 多语言资源 */
@Composable
private fun defaultColorModeEntries(): List<Pair<Int, String>> = listOf(
    ColorMode.SYSTEM.value to stringResource(R.string.theme_color_mode_system),
    ColorMode.LIGHT.value to stringResource(R.string.theme_color_mode_light),
    ColorMode.DARK.value to stringResource(R.string.theme_color_mode_dark),
    ColorMode.MONET_SYSTEM.value to stringResource(R.string.theme_color_mode_monet_system),
    ColorMode.MONET_LIGHT.value to stringResource(R.string.theme_color_mode_monet_light),
    ColorMode.MONET_DARK.value to stringResource(R.string.theme_color_mode_monet_dark),
    ColorMode.DARK_AMOLED.value to stringResource(R.string.theme_color_mode_dark_amoled),
)
