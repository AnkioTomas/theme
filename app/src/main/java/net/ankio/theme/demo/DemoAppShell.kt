/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
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

enum class DemoBottomTab(val label: String) {
    Catalog("组件"),
    Settings("设置"),
}

/**
 * Demo 主壳：可折叠 [ThemeTopAppBar] + 全宽 [ThemeNavigationBar] + 可嵌套滚动的内容区。
 */
@Composable
fun DemoAppShell(
    title: String,
    largeTitle: String,
    showBack: Boolean,
    onBack: () -> Unit,
    onRecreateTheme: () -> Unit,
    titleAlignment: ThemeTopAppBarTitleAlignment,
    onTitleAlignmentChange: ((ThemeTopAppBarTitleAlignment) -> Unit)?,
    collapseOnScroll: Boolean,
    showBottomNavigation: Boolean = true,
    selectedTab: DemoBottomTab,
    onTabSelected: (DemoBottomTab) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (nestedScrollModifier: Modifier) -> Unit,
) {
    val scroll = if (collapseOnScroll) rememberThemeTopAppBarScroll(collapseOnScroll = true) else null
    val nestedScrollModifier = scroll?.nestedScrollConnection?.let { Modifier.nestedScroll(it) }
        ?: Modifier

    Column(modifier = modifier.fillMaxSize()) {
        ThemeTopAppBar(
            title = title,
            largeTitle = largeTitle,
            titleAlignment = titleAlignment,
            scroll = scroll,
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = {
                if (showBack) {
                    ThemeIconButton(onClick = onBack) {
                        ThemeIcon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回",
                            tint = AnkioTheme.colorScheme.onSurface,
                        )
                    }
                }
            },
            actions = {
                ThemeIconButton(onClick = onRecreateTheme) {
                    ThemeIcon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "应用主题",
                        tint = AnkioTheme.colorScheme.onSurface,
                    )
                }
            },
        )

        if (onTitleAlignmentChange != null) {
            TitleAlignmentBar(
                value = titleAlignment,
                onValueChange = onTitleAlignmentChange,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .then(
                    if (!showBottomNavigation) {
                        Modifier.navigationBarsPadding()
                    } else {
                        Modifier
                    },
                ),
        ) {
            content(nestedScrollModifier)
        }

        if (showBottomNavigation) {
            ThemeNavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
            ) {
                ThemeNavigationBarItem(
                    selected = selectedTab == DemoBottomTab.Catalog,
                    onClick = { onTabSelected(DemoBottomTab.Catalog) },
                    icon = Icons.Filled.Apps,
                    label = DemoBottomTab.Catalog.label,
                )
                ThemeNavigationBarItem(
                    selected = selectedTab == DemoBottomTab.Settings,
                    onClick = { onTabSelected(DemoBottomTab.Settings) },
                    icon = Icons.Filled.Settings,
                    label = DemoBottomTab.Settings.label,
                )
            }
        }
    }
}

@Composable
private fun TitleAlignmentBar(
    value: ThemeTopAppBarTitleAlignment,
    onValueChange: (ThemeTopAppBarTitleAlignment) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
        ThemeText(
            text = "标题",
            style = AnkioTheme.textStyles.footnote1,
            color = AnkioTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f, fill = false),
        )
        ThemeButtonGroup {
            listOf(
                ThemeTopAppBarTitleAlignment.Start to "居左",
                ThemeTopAppBarTitleAlignment.Center to "居中",
            ).forEachIndexed { index, (align, label) ->
                val pos = when (index) {
                    0 -> ButtonGroupPosition.Start
                    else -> ButtonGroupPosition.End
                }
                ThemeGroupButton(
                    onClick = { onValueChange(align) },
                    position = pos,
                ) {
                    ThemeText(
                        text = label,
                        style = AnkioTheme.textStyles.button,
                        color = if (value == align) {
                            AnkioTheme.colorScheme.onSurface
                        } else {
                            AnkioTheme.colorScheme.onSecondaryContainer
                        },
                    )
                }
            }
        }
    }
}
