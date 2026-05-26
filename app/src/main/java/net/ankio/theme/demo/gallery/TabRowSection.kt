/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeTabRow
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.demo.ui.ComponentSample
import net.ankio.theme.demo.ui.SectionCard

@Composable
fun TabRowSection() {
    SectionCard(title = "ThemeTabRow") {
        var tabIndex by remember { mutableIntStateOf(0) }
        val tabs = listOf("组件", "发现", "我的")

        ComponentSample(
            name = "标签切换",
            api = "ThemeTabRow(tabs, selectedTabIndex, onTabSelected)",
            description = "常用于页面一级分区。与 TopAppBar 组合时注意滚动区域 inset。",
        ) {
            ThemeTabRow(
                tabs = tabs,
                selectedTabIndex = tabIndex,
                onTabSelected = { tabIndex = it },
                modifier = Modifier.fillMaxWidth(),
            )
            ThemeText(
                text = "当前：${tabs[tabIndex]}",
                style = AnkioTheme.textStyles.body2,
                color = AnkioTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
