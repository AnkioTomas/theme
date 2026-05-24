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
import androidx.compose.ui.platform.LocalContext
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
import net.ankio.theme.themeKeyOptions

/**
 * UI 设置数据，用于 [UiSettingsScreen] 的选项展示。
 * 选择器标题使用 theme 库多语言（默认英语），此处仅传选项值及显示文案。
 */
data class UiSettingsOptions(
    val uiModeEntries: List<Pair<String, String>>,
    val colorModeEntries: List<Pair<Int, String>>,
    val themeColorEntries: List<Pair<String, String>>,
)


@SuppressLint("RememberReturnType")
@PreviewAll
@Composable
private fun UiSettingsScreenPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    val ctx = LocalContext.current
    // remember 在 composition 阶段同步执行，确保 init 在任何 ThemeSettings 访问之前完成
    remember {
        ThemeSettings.init(ctx)
    }
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
    options: UiSettingsOptions,
    modifier: Modifier = Modifier,
    onThemeChanged: () -> Unit = {},
) {
    var uiMode by remember { mutableStateOf(ThemeSettings.uiMode) }
    var colorMode by remember { mutableStateOf(ThemeSettings.colorMode) }
    var followSystemAccent by remember { mutableStateOf(ThemeSettings.followSystemAccent) }
    var themeColor by remember { mutableStateOf(ThemeSettings.themeColor) }
    var displayPercentage by remember { mutableStateOf(ThemeSettings.displayPercentage) }

    val scrollState = rememberScrollState()

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
            entries = options.uiModeEntries,
        )

        ColorModeSelector(
            value = colorMode,
            onValueChange = {
                colorMode = it
                ThemeSettings.colorMode = it
                onThemeChanged()
            },
            entries = options.colorModeEntries,
        )

        FollowSystemAccentSwitch(
            checked = followSystemAccent,
            onCheckedChange = {
                followSystemAccent = it
                ThemeSettings.followSystemAccent = it
                onThemeChanged()
            },
        )

        SectionHeader(text = stringResource(R.string.theme_section_color))
        ThemeColorSelector(
            value = themeColor,
            onValueChange = {
                themeColor = it
                ThemeSettings.themeColor = it
                onThemeChanged()
            },
            entries = options.themeColorEntries,
            themeKeyOptions = themeKeyOptions,
            onThemeChanged = onThemeChanged,
        )

        Spacer(Modifier.height(4.dp))

        DisplayPercentageSlider(
            value = displayPercentage,
            onValueChange = {
                displayPercentage = it
                ThemeSettings.displayPercentage = it
            },
            onThemeChanged = onThemeChanged,
        )
    }
}
