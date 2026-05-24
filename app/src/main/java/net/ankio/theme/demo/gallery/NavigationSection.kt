/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemeFloatingNavBarMode
import net.ankio.theme.compat.ThemeNavigationBar
import net.ankio.theme.compat.ThemeNavigationBarItem
import net.ankio.theme.compat.ThemeNavigationRail
import net.ankio.theme.compat.ThemePullToRefresh
import net.ankio.theme.compat.ThemeTabRow
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.compat.ThemeTopAppBar

/** 导航类组件：TopAppBar/NavigationBar/NavigationRail/TabRow/PullToRefresh */
@Composable
internal fun NavigationSection() {
    SectionCard(title = "导航 / Navigation") {

        Caption("ThemeTopAppBar · navigationIcon + actions")
        ThemeTopAppBar(
            title = "标题示例",
            modifier = Modifier.fillMaxWidth(),
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
                ThemeIconButton(onClick = {}) {
                    ThemeIcon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = null,
                        tint = AnkioTheme.colorScheme.onSurface,
                    )
                }
            },
        )

        Caption("ThemeTabRow")
        var tabIndex by remember { mutableIntStateOf(0) }
        ThemeTabRow(
            tabs = listOf("首页", "发现", "我的"),
            selectedTabIndex = tabIndex,
            onTabSelected = { tabIndex = it },
            modifier = Modifier.fillMaxWidth(),
        )

        Caption("ThemeNavigationBar + ThemeNavigationBarItem · 底部导航")
        var navIndex by remember { mutableIntStateOf(0) }
        val items = listOf(
            "首页" to Icons.Filled.Home,
            "通知" to Icons.Filled.Notifications,
            "收藏" to Icons.Filled.Favorite,
            "设置" to Icons.Filled.Settings,
        )
        ThemeNavigationBar(modifier = Modifier.fillMaxWidth()) {
            items.forEachIndexed { index, (label, icon) ->
                ThemeNavigationBarItem(
                    selected = navIndex == index,
                    onClick = { navIndex = index },
                    icon = icon,
                    label = label,
                )
            }
        }

        Caption("ThemeNavigationBar · floating 悬浮模式（Miuix 用 FloatingNavigationBar，Material 模拟胶囊）")
        var floatingIndex by remember { mutableIntStateOf(0) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(AnkioTheme.colorScheme.surfaceContainer),
            contentAlignment = Alignment.Center,
        ) {
            ThemeNavigationBar(
                modifier = Modifier.fillMaxWidth(),
                floating = true,
                mode = ThemeFloatingNavBarMode.IconAndText,
            ) {
                items.forEachIndexed { index, (label, icon) ->
                    ThemeNavigationBarItem(
                        selected = floatingIndex == index,
                        onClick = { floatingIndex = index },
                        icon = icon,
                        label = label,
                    )
                }
            }
        }

        Caption("ThemeNavigationRail · 侧边导航（横屏 / 大屏）")
        var railIndex by remember { mutableIntStateOf(0) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(AnkioTheme.colorScheme.surfaceContainer),
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                ThemeNavigationRail(
                    modifier = Modifier.fillMaxHeight(),
                    containerColor = AnkioTheme.colorScheme.surface,
                    header = {
                        ThemeIconButton(onClick = {}) {
                            ThemeIcon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = null,
                                tint = AnkioTheme.colorScheme.primary,
                            )
                        }
                    },
                ) {
                    listOf(
                        Icons.Filled.Home,
                        Icons.Filled.Person,
                        Icons.Filled.Settings,
                    ).forEachIndexed { index, icon ->
                        ThemeIconButton(onClick = { railIndex = index }) {
                            ThemeIcon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (railIndex == index)
                                    AnkioTheme.colorScheme.primary
                                else
                                    AnkioTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    ThemeText(
                        text = "Rail 第 ${railIndex + 1} 项内容",
                        style = AnkioTheme.textStyles.body1,
                        color = AnkioTheme.colorScheme.onSurface,
                    )
                }
            }
        }

        Caption("ThemePullToRefresh · 向下拉触发")
        var refreshing by remember { mutableStateOf(false) }
        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(1500)
                refreshing = false
            }
        }
        Box(modifier = Modifier.fillMaxWidth().height(120.dp)) {
            ThemePullToRefresh(
                isRefreshing = refreshing,
                onRefresh = { refreshing = true },
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    ThemeIcon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = null,
                        tint = AnkioTheme.colorScheme.primary,
                    )
                    ThemeText(
                        text = if (refreshing) "刷新中..." else "下拉刷新",
                        style = AnkioTheme.textStyles.body1,
                        color = AnkioTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}
