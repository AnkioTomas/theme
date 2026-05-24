/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Translate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeLazyListPopupColumn
import net.ankio.theme.compat.ThemeListPopupColumn
import net.ankio.theme.compat.ThemeListPopupItem
import net.ankio.theme.compat.ThemeSearchBar
import net.ankio.theme.compat.ThemeSecondaryButton
import net.ankio.theme.compat.ThemeSuperDropdown
import net.ankio.theme.compat.ThemeSuperListPopup
import net.ankio.theme.compat.ThemeSuperSpinner
import net.ankio.theme.compat.ThemeText
import top.yukonga.miuix.kmp.basic.SpinnerEntry

/** 弹窗类组件：SuperSpinner/SuperDropdown/SuperListPopup/SearchBar */
@Composable
internal fun PopupSection() {
    SectionCard(title = "弹窗与下拉 / Popup") {

        Caption("ThemeSuperDropdown（纯文本下拉）")
        var dropdownIndex by remember { mutableIntStateOf(0) }
        ThemeSuperDropdown(
            items = listOf("简体中文", "English", "日本語", "한국어"),
            selectedIndex = dropdownIndex,
            onSelectedIndexChange = { dropdownIndex = it },
            title = "语言",
            summary = "选择应用语言",
            modifier = Modifier.fillMaxWidth(),
            startAction = {
                ThemeIcon(
                    imageVector = Icons.Filled.Translate,
                    contentDescription = null,
                    tint = AnkioTheme.colorScheme.primary,
                )
            },
        )

        Caption("ThemeSuperSpinner（带图标）")
        var spinnerIndex by remember { mutableIntStateOf(0) }
        val themes = listOf(
            SpinnerEntry(title = "默认", summary = "Material You 紫色"),
            SpinnerEntry(title = "樱花", summary = "粉色基调"),
            SpinnerEntry(title = "森林", summary = "绿色基调"),
        )
        ThemeSuperSpinner(
            items = themes,
            selectedIndex = spinnerIndex,
            onSelectedIndexChange = { spinnerIndex = it },
            title = "主题预设",
            summary = "影响主色与表面色",
            modifier = Modifier.fillMaxWidth(),
            startAction = {
                ThemeIcon(
                    imageVector = Icons.Filled.ColorLens,
                    contentDescription = null,
                    tint = AnkioTheme.colorScheme.primary,
                )
            },
        )

        Caption("ThemeSuperListPopup + ThemeListPopupColumn + ThemeListPopupItem · 静态短列表")
        var popupShown by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxWidth()) {
            ThemeSecondaryButton(onClick = { popupShown = true }) {
                ThemeText(
                    text = "打开列表弹窗",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onSecondaryContainer,
                )
            }
            ThemeSuperListPopup(
                show = popupShown,
                onDismissRequest = { popupShown = false },
                offset = IntOffset(0, 120),
                minWidth = 220.dp,
            ) {
                ThemeListPopupColumn {
                    listOf("第一项", "第二项", "第三项", "第四项").forEach { label ->
                        ThemeListPopupItem(
                            text = label,
                            onClick = { popupShown = false },
                        )
                    }
                }
            }
        }

        Caption("ThemeSuperListPopup + ThemeLazyListPopupColumn · 大数据量懒加载")
        var lazyPopupShown by remember { mutableStateOf(false) }
        val largeItems = remember { (1..100).map { "选项 $it" } }
        Box(modifier = Modifier.fillMaxWidth()) {
            ThemeSecondaryButton(onClick = { lazyPopupShown = true }) {
                ThemeText(
                    text = "打开 100 项懒加载弹窗",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onSecondaryContainer,
                )
            }
            ThemeSuperListPopup(
                show = lazyPopupShown,
                onDismissRequest = { lazyPopupShown = false },
                offset = IntOffset(0, 120),
                minWidth = 240.dp,
                maxHeight = 320.dp,
                enableContainerScroll = false,
            ) {
                ThemeLazyListPopupColumn(
                    items = largeItems,
                    key = { _, item -> item },
                ) { item ->
                    ThemeListPopupItem(
                        text = item,
                        onClick = { lazyPopupShown = false },
                    )
                }
            }
        }

        Caption("ThemeSearchBar")
        var query by remember { mutableStateOf("") }
        var searchExpanded by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxWidth().height(72.dp)) {
            ThemeSearchBar(
                query = query,
                onQueryChange = { query = it },
                expanded = searchExpanded,
                onExpandedChange = { searchExpanded = it },
                modifier = Modifier.fillMaxWidth(),
                onSearch = { searchExpanded = false },
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ThemeText(
                        text = "搜索结果占位",
                        style = AnkioTheme.textStyles.body1,
                        color = AnkioTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}
