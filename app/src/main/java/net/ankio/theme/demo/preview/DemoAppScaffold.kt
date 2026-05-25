/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeCard
import net.ankio.theme.compat.ThemeButtonGroup
import net.ankio.theme.compat.ThemeGroupButton
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemeNavigationBar
import net.ankio.theme.compat.ThemeNavigationBarItem
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.compat.ThemeTopAppBar
import net.ankio.theme.compat.ThemeTopAppBarTitleAlignment
import net.ankio.theme.compat.rememberThemeTopAppBarScroll
import net.ankio.theme.compat.ButtonGroupPosition

/**
 * 标准应用布局预览：顶部栏（可折叠）+ 可滚动内容 + 底部导航。
 */
@Composable
internal fun DemoAppScaffold(
    pageTitle: String = "组件展示",
    modifier: Modifier = Modifier,
) {
    var titleAlignment by remember { mutableStateOf(ThemeTopAppBarTitleAlignment.Center) }
    var selectedTab by remember { mutableIntStateOf(0) }

    val scroll = rememberThemeTopAppBarScroll(collapseOnScroll = true)
    val nestedScrollModifier = scroll?.let { Modifier.nestedScroll(it.nestedScrollConnection) } ?: Modifier

    val navItems = listOf(
        "首页" to Icons.Filled.Home,
        "通知" to Icons.Filled.Notifications,
        "设置" to Icons.Filled.Settings,
    )

    val listData = remember { (1..24).map { "列表条目 #$it · 向下滚动时顶部大标题会上移折叠" } }

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            ThemeTopAppBar(
                title = pageTitle,
                largeTitle = pageTitle,
                titleAlignment = titleAlignment,
                scroll = scroll,
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

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .then(nestedScrollModifier),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 96.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    ThemeCard(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            ThemeText(
                                text = "标题位置",
                                style = AnkioTheme.textStyles.title4,
                                color = AnkioTheme.colorScheme.onSurface,
                            )
                            TitleAlignmentPicker(
                                value = titleAlignment,
                                onValueChange = { titleAlignment = it },
                            )
                        }
                    }
                }

                items(listData) { line ->
                    ThemeCard(modifier = Modifier.fillMaxWidth()) {
                        ThemeText(
                            text = line,
                            style = AnkioTheme.textStyles.body1,
                            color = AnkioTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                }
            }
        }

        ThemeNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            navItems.forEachIndexed { index, (label, icon) ->
                ThemeNavigationBarItem(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    icon = icon,
                    label = label,
                )
            }
        }
    }
}

@Composable
private fun TitleAlignmentPicker(
    value: ThemeTopAppBarTitleAlignment,
    onValueChange: (ThemeTopAppBarTitleAlignment) -> Unit,
) {
    val options = listOf(
        ThemeTopAppBarTitleAlignment.Start to "居左",
        ThemeTopAppBarTitleAlignment.Center to "居中",
    )
    ThemeButtonGroup(modifier = Modifier.fillMaxWidth()) {
        options.forEachIndexed { index, (alignment, label) ->
            val position = when (index) {
                0 -> ButtonGroupPosition.Start
                options.lastIndex -> ButtonGroupPosition.End
                else -> ButtonGroupPosition.Middle
            }
            ThemeGroupButton(
                onClick = { onValueChange(alignment) },
                modifier = Modifier.weight(1f),
                position = position,
            ) {
                ThemeText(
                    text = label,
                    style = AnkioTheme.textStyles.button,
                    color = if (value == alignment) {
                        AnkioTheme.colorScheme.onPrimary
                    } else {
                        AnkioTheme.colorScheme.onSecondaryContainer
                    },
                )
            }
        }
    }
}
