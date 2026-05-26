/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.ankio.theme.BaseComposeActivity
import net.ankio.theme.compat.ThemeTopAppBarTitleAlignment
import net.ankio.theme.demo.catalog.DemoCategory

class MainActivity : BaseComposeActivity() {

    @Composable
    override fun Content() {
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        var titleAlignment by rememberSaveable {
            mutableStateOf(ThemeTopAppBarTitleAlignment.Center)
        }

        // 带参路由的 destination.route 是模板（category/{categoryName}），须从 arguments 取实参
        val openCategory = backStackEntry?.arguments
            ?.getString("categoryName")
            ?.let { name -> DemoCategory.entries.find { it.name == name } }

        val onTopLevelTab = openCategory == null &&
            (currentRoute == DemoRoutes.Catalog || currentRoute == DemoRoutes.Settings)

        val topTitle = when {
            openCategory != null -> openCategory.title
            currentRoute == DemoRoutes.Settings -> "主题设置"
            else -> "Theme Demo"
        }

        val largeTitle = when {
            openCategory != null -> openCategory.title
            currentRoute == DemoRoutes.Settings -> "主题设置"
            else -> "Theme 组件目录"
        }

        val showTitleAlignmentPicker = openCategory == null && currentRoute == DemoRoutes.Catalog
        val collapseOnScroll = currentRoute == DemoRoutes.Catalog || openCategory != null
        val topAppBarScrollKey = when {
            openCategory != null -> "category:${openCategory.name}"
            currentRoute == DemoRoutes.Settings -> DemoRoutes.Settings
            else -> DemoRoutes.Catalog
        }

        DemoAppShell(
            title = topTitle,
            largeTitle = largeTitle,
            topAppBarScrollKey = topAppBarScrollKey,
            showBack = navController.previousBackStackEntry != null,
            onBack = { navController.popBackStack() },
            onRecreateTheme = ::recreateForThemeChange,
            titleAlignment = titleAlignment,
            onTitleAlignmentChange = if (showTitleAlignmentPicker) {
                { titleAlignment = it }
            } else {
                null
            },
            collapseOnScroll = collapseOnScroll,
            showBottomNavigation = onTopLevelTab,
            selectedTab = when (currentRoute) {
                DemoRoutes.Settings -> DemoBottomTab.Settings
                else -> DemoBottomTab.Catalog
            },
            onTabSelected = { tab ->
                val route = when (tab) {
                    DemoBottomTab.Catalog -> DemoRoutes.Catalog
                    DemoBottomTab.Settings -> DemoRoutes.Settings
                }
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        ) { nestedScrollModifier ->
            DemoNavHost(
                navController = navController,
                nestedScrollModifier = nestedScrollModifier,
                onRecreateTheme = ::recreateForThemeChange,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
