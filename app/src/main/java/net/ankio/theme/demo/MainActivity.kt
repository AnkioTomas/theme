/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import net.ankio.theme.AnkioTheme
import net.ankio.theme.BaseComposeActivity
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemeTopAppBarTitleAlignment
import net.ankio.theme.demo.catalog.CatalogScreen
import net.ankio.theme.demo.catalog.CategoryDetailScreen
import net.ankio.theme.demo.catalog.DemoCategory
import net.ankio.theme.layout.ThemeApp
import net.ankio.theme.layout.ThemeScope
import net.ankio.theme.layout.navArgs
import net.ankio.theme.settings.UiSettingsScreen

object DemoRoutes {
    const val Catalog = "catalog"
    const val Settings = "settings"
    const val Category = "category/{categoryName}"

    fun category(c: DemoCategory) = "category/${c.name}"
}

private fun NavBackStackEntry.category() =
    arguments?.getString("categoryName")?.let { n -> DemoCategory.entries.find { it.name == n } }

private fun ThemeScope.category() = entry.category()

class MainActivity : BaseComposeActivity() {

    @Composable
    override fun Content() {
        var align by rememberSaveable { mutableStateOf(ThemeTopAppBarTitleAlignment.Center) }

        ThemeApp(
            start = DemoRoutes.Catalog,
            titleAlignment = align,
            actions = {
                ThemeIconButton(onClick = ::recreateForThemeChange) {
                    ThemeIcon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "应用主题",
                        tint = AnkioTheme.colorScheme.onSurface,
                    )
                }
            },
            header = { route ->
                if (route == DemoRoutes.Catalog) {
                    DemoTitleAlignmentBar(
                        value = align,
                        onValueChange = { align = it },
                    )
                }
            },
        ) {
            screen(
                DemoRoutes.Catalog,
                "Theme Demo",
                "Theme 组件目录",
                Icons.Filled.Apps to "组件",
            ) {
                CatalogScreen(
                    list = lazyList(),
                    onOpenCategory = { go(DemoRoutes.category(it)) },
                )
            }
            screen(DemoRoutes.Settings, "主题设置", tab = Icons.Filled.Settings to "设置", collapse = true) {
                scrollColumn {
                    UiSettingsScreen(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        onThemeChanged = ::recreateForThemeChange,
                    )
                }
            }
            screen(
                route = DemoRoutes.Category,
                title = { it.category()?.title.orEmpty() },
                detail = true,
                args = navArgs { string("categoryName") },
            ) {
                val c = category() ?: return@screen
                CategoryDetailScreen(category = c, list = lazyList())
            }
        }
    }
}
