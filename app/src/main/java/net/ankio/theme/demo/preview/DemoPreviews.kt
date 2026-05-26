/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.preview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import net.ankio.theme.PreviewAll
import net.ankio.theme.PreviewAllThemes
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.ThemeTopAppBarTitleAlignment
import net.ankio.theme.demo.DemoAppShell
import net.ankio.theme.demo.DemoBottomTab
import net.ankio.theme.demo.catalog.CatalogScreen
import net.ankio.theme.demo.catalog.CategoryDetailScreen
import net.ankio.theme.demo.catalog.DemoCategory
import net.ankio.theme.settings.UiSettingsOptions
import net.ankio.theme.settings.UiSettingsScreen

private val demoSettingsPreviewOptions = UiSettingsOptions(
    uiModeEntries = listOf("miuix" to "Miuix", "material" to "Material"),
    colorModeEntries = listOf(
        0 to "Follow system (MIUI)",
        1 to "Light",
        2 to "Dark",
        3 to "Follow system (Monet)",
        4 to "Light (Monet)",
        5 to "Dark (Monet)",
    ),
    themeColorEntries = emptyList(),
)

private class DemoCategoryPreviewProvider : PreviewParameterProvider<DemoCategory> {
    override val values: Sequence<DemoCategory> = DemoCategory.entries.asSequence()

    override fun getDisplayName(index: Int): String? =
        DemoCategory.entries.getOrNull(index)?.title
}

/** 主界面：组件目录 + 可折叠 TopBar + 底栏 */
@PreviewAll
@Composable
private fun DemoCatalogShellPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewAllThemes(config) {
        var titleAlignment by remember {
            mutableStateOf(ThemeTopAppBarTitleAlignment.Center)
        }
        val listState = rememberLazyListState()

        DemoAppShell(
            title = "Theme Demo",
            largeTitle = "Theme 组件目录",
            showBack = false,
            onBack = {},
            onRecreateTheme = {},
            titleAlignment = titleAlignment,
            onTitleAlignmentChange = { titleAlignment = it },
            collapseOnScroll = true,
            selectedTab = DemoBottomTab.Catalog,
            onTabSelected = {},
        ) { nestedScrollModifier ->
            CatalogScreen(
                listState = listState,
                nestedScrollModifier = nestedScrollModifier,
                onOpenCategory = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

/** 主界面：设置 Tab + 底栏 */
@PreviewAll
@Composable
private fun DemoSettingsShellPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewAllThemes(config) {
        DemoAppShell(
            title = "主题设置",
            largeTitle = "主题设置",
            showBack = false,
            onBack = {},
            onRecreateTheme = {},
            titleAlignment = ThemeTopAppBarTitleAlignment.Center,
            onTitleAlignmentChange = null,
            collapseOnScroll = false,
            selectedTab = DemoBottomTab.Settings,
            onTabSelected = {},
        ) { _ ->
            UiSettingsScreen(
                options = demoSettingsPreviewOptions,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
            )
        }
    }
}

/** 分类详情（含 TopBar 返回 + 滚动折叠） */
@PreviewAll
@Composable
private fun DemoCategoryShellPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
    @PreviewParameter(DemoCategoryPreviewProvider::class) category: DemoCategory,
) {
    PreviewAllThemes(config) {
        DemoAppShell(
            title = category.title,
            largeTitle = category.title,
            showBack = true,
            onBack = {},
            onRecreateTheme = {},
            titleAlignment = ThemeTopAppBarTitleAlignment.Center,
            onTitleAlignmentChange = null,
            collapseOnScroll = true,
            showBottomNavigation = false,
            selectedTab = DemoBottomTab.Catalog,
            onTabSelected = {},
        ) { nestedScrollModifier ->
            CategoryDetailScreen(
                category = category,
                nestedScrollModifier = nestedScrollModifier,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

/** 仅内容区（无壳），便于快速查看某一分类示例 */
@PreviewAll
@Composable
private fun DemoCategoryContentPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
    @PreviewParameter(DemoCategoryPreviewProvider::class) category: DemoCategory,
) {
    PreviewAllThemes(config) {
        CategoryDetailScreen(
            category = category,
            nestedScrollModifier = Modifier,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
