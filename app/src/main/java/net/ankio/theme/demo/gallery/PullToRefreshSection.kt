/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemePullToRefresh
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.demo.ui.ComponentSample
import net.ankio.theme.demo.ui.SectionCard

@Composable
fun PullToRefreshSection() {
    SectionCard(title = "ThemePullToRefresh") {
        var refreshing by remember { mutableStateOf(false) }
        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(1500)
                refreshing = false
            }
        }

        ComponentSample(
            name = "下拉刷新",
            api = "ThemePullToRefresh(isRefreshing, onRefresh) { content() }",
            description = "包裹可滚动内容。onRefresh 内启动协程加载数据，完成后设 isRefreshing = false。",
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
            ) {
                ThemePullToRefresh(
                    isRefreshing = refreshing,
                    onRefresh = { refreshing = true },
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        ThemeIcon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = null,
                            tint = AnkioTheme.colorScheme.primary,
                        )
                        ThemeText(
                            text = if (refreshing) "刷新中…" else "下拉刷新",
                            style = AnkioTheme.textStyles.body1,
                            color = AnkioTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }
    }
}
