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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.R
import net.ankio.theme.compat.ThemeSuperDropdown
import net.ankio.theme.compat.ThemeSuperSpinner
import net.ankio.theme.compat.ThemeSwitch
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.seedColorFromThemeKey
import net.ankio.theme.themeKeyOptions
import net.ankio.theme.themeKeyToDisplayNameResId
import top.yukonga.miuix.kmp.basic.SpinnerEntry

/** 分区标题，使用主题色 */
@Composable
internal fun SectionHeader(
    text: String,
    modifier: Modifier = Modifier,
) {
    ThemeText(
        text = text,
        style = AnkioTheme.textStyles.footnote1,
        color = AnkioTheme.colorScheme.primary,
        modifier = modifier.padding(horizontal = 4.dp, vertical = 4.dp),
    )
}

/** UI 引擎选择：主题风格分区下的下拉 */
@Composable
internal fun UiModeSelector(
    value: String,
    onValueChange: (String) -> Unit,
    entries: List<Pair<String, String>>,
) {
    val items = entries.map { it.second }
    val selectedIndex = entries.indexOfFirst { it.first == value }.coerceIn(0, items.size - 1)

    ThemeSuperDropdown(
        items = items,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { onValueChange(entries[it].first) },
        title = stringResource(R.string.theme_ui_style),
        summary = stringResource(R.string.theme_ui_style_summary),
        startAction = {
            Icon(
                imageVector = Icons.Filled.Style,
                contentDescription = null,
                tint = AnkioTheme.colorScheme.primary,
            )
        },
        shape = SettingCardPosition.First.toShape(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 3.dp),
    )
}

/** 主题模式选择：下拉 */
@Composable
internal fun ColorModeSelector(
    value: Int,
    onValueChange: (Int) -> Unit,
    entries: List<Pair<Int, String>>,
) {
    val items = entries.map { it.second }
    val selectedIndex = entries.indexOfFirst { it.first == value }.coerceIn(0, items.size - 1)

    ThemeSuperDropdown(
        items = items,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { onValueChange(entries[it].first) },
        title = stringResource(R.string.theme_color_mode),
        summary = stringResource(R.string.theme_color_mode_summary),
        startAction = {
            Icon(
                imageVector = Icons.Filled.DarkMode,
                contentDescription = null,
                tint = AnkioTheme.colorScheme.primary,
            )
        },
        shape = SettingCardPosition.Middle.toShape(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
    )
}

/** 动态颜色开关 */
@Composable
internal fun FollowSystemAccentSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    SettingCard(
        icon = {
            Icon(
                imageVector = Icons.Filled.WaterDrop,
                contentDescription = null,
                tint = AnkioTheme.colorScheme.primary,
            )
        },
        title = stringResource(R.string.theme_follow_system_accent),
        subtitle = stringResource(R.string.theme_follow_system_accent_summary),
        onClick = { onCheckedChange(!checked) },
        trailing = {
            ThemeSwitch(
                checked = checked,
                onCheckedChange = onCheckedChange,
            )
        },
        position = SettingCardPosition.Last,
    )
}

/** 主题色设置项：下拉列表选择 */
@Composable
internal fun ThemeColorSelector(
    value: String,
    onValueChange: (String) -> Unit,
    entries: List<Pair<String, String>>,
    themeKeyOptions: List<String>,
    onThemeChanged: () -> Unit,
) {
    val keyLabels = if (entries.isEmpty()) {
        themeKeyOptions.associateWith { key ->
            stringResource(themeKeyToDisplayNameResId(key))
        }
    } else {
        entries.toMap()
    }
    val items = themeKeyOptions.map { key ->
        SpinnerEntry(
            icon = { iconModifier ->
                Box(
                    modifier = iconModifier,
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(Color(seedColorFromThemeKey(key))),
                    )
                }
            },
            title = keyLabels[key] ?: key,
        )
    }
    val selectedIndex = themeKeyOptions.indexOf(value).coerceIn(0, items.size - 1)

    ThemeSuperSpinner(
        items = items,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = {
            onValueChange(themeKeyOptions[it])
            onThemeChanged()
        },
        title = stringResource(R.string.theme_theme_color),
        summary = stringResource(R.string.theme_theme_color_summary),
        startAction = {
            Icon(
                imageVector = Icons.Filled.Palette,
                contentDescription = null,
                tint = AnkioTheme.colorScheme.primary,
            )
        },
        shape = SettingCardPosition.Single.toShape(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
    )
}
