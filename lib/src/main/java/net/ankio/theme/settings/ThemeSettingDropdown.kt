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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.ankio.theme.compat.ThemeSuperDropdown

/** 设置页分区小标题（主题色 footnote） */
@Composable
fun ThemeSectionHeader(
    text: String,
    modifier: Modifier = Modifier,
) {
    SectionHeader(text = text, modifier = modifier)
}

/**
 * 设置项下拉：与 [UiSettingsScreen] 内分组卡片一致，支持 [SettingCardPosition] 首尾圆角拼接。
 */
@Composable
fun ThemeSettingDropdown(
    items: List<String>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    startAction: @Composable (() -> Unit)? = null,
    position: SettingCardPosition = SettingCardPosition.Single,
    enabled: Boolean = true,
) {
    val (topPad, bottomPad) = position.toVerticalPadding()
    ThemeSuperDropdown(
        items = items,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = onSelectedIndexChange,
        title = title,
        summary = summary,
        startAction = startAction,
        shape = position.toShape(),
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = topPad, bottom = bottomPad),
    )
}
