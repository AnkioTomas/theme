/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeButtonGroup
import net.ankio.theme.compat.ThemeGroupButton
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.compat.ThemeTopAppBar
import net.ankio.theme.compat.ThemeTopAppBarTitleAlignment
import net.ankio.theme.compat.rememberThemeTopAppBarScroll
import net.ankio.theme.compat.ButtonGroupPosition
import net.ankio.theme.demo.ui.ComponentSample
import net.ankio.theme.demo.ui.SectionCard

@Composable
fun TopAppBarSection() {
    SectionCard(title = "固定顶部栏") {
        ComponentSample(
            name = "基础 TopAppBar",
            api = "ThemeTopAppBar(title, navigationIcon, actions)",
            description = "不折叠。Material 用 CenterAlignedTopAppBar / TopAppBar；Miuix 用 SmallTopAppBar。",
        ) {
            ThemeTopAppBar(
                title = "固定标题",
                modifier = Modifier.fillMaxWidth(),
                titleAlignment = ThemeTopAppBarTitleAlignment.Center,
                navigationIcon = {
                    ThemeIconButton(onClick = {}) {
                        ThemeIcon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = AnkioTheme.colorScheme.onSurface,
                        )
                    }
                },
                actions = {
                    ThemeIconButton(onClick = {}) {
                        ThemeIcon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            tint = AnkioTheme.colorScheme.onSurface,
                        )
                    }
                },
            )
        }
    }

    SectionCard(title = "可折叠大标题") {
        var titleAlignment by remember { mutableStateOf(ThemeTopAppBarTitleAlignment.Center) }
        val scroll = rememberThemeTopAppBarScroll(collapseOnScroll = true)

        ComponentSample(
            name = "滚动折叠",
            api = "val scroll = rememberThemeTopAppBarScroll(true)!!\nThemeTopAppBar(..., scroll = scroll)\nLazyColumn(Modifier.then(scroll.contentModifier))",
            description = "列表上滑时大标题收起；列表需 `.then(scroll.contentModifier)`。",
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                ThemeTopAppBar(
                    title = "折叠标题",
                    largeTitle = "折叠大标题",
                    titleAlignment = titleAlignment,
                    scroll = scroll,
                    modifier = Modifier.fillMaxWidth(),
                )
                ThemeButtonGroup(modifier = Modifier.fillMaxWidth()) {
                    listOf(
                        ThemeTopAppBarTitleAlignment.Start to "居左",
                        ThemeTopAppBarTitleAlignment.Center to "居中",
                    ).forEachIndexed { index, (align, label) ->
                        val pos = when (index) {
                            0 -> ButtonGroupPosition.Start
                            else -> ButtonGroupPosition.End
                        }
                        ThemeGroupButton(
                            onClick = { titleAlignment = align },
                            modifier = Modifier.weight(1f),
                            position = pos,
                        ) {
                            ThemeText(
                                text = label,
                                style = AnkioTheme.textStyles.button,
                                color = AnkioTheme.colorScheme.onSecondaryContainer,
                            )
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .then(scroll!!.contentModifier),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items((1..12).map { "滚动条目 #$it" }) { line ->
                        ThemeText(
                            text = line,
                            style = AnkioTheme.textStyles.body2,
                            color = AnkioTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }
    }
}
