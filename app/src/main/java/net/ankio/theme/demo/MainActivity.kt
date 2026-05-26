/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
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

        val catalogListState = rememberSaveable(saver = LazyListState.Saver) {
            LazyListState()
        }

        val openCategoryName = currentRoute
            ?.removePrefix("category/")
            ?.takeIf { currentRoute.startsWith("category/") }
        val openCategory = openCategoryName?.let { name ->
            DemoCategory.entries.find { it.name == name }
        }

        val onTopLevelTab = currentRoute == DemoRoutes.Catalog || currentRoute == DemoRoutes.Settings
        val showBottomNavigation = onTopLevelTab

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

        val showTitleAlignmentPicker = currentRoute == DemoRoutes.Catalog
        val collapseOnScroll = currentRoute == DemoRoutes.Catalog || openCategory != null

        DemoAppShell(
            title = topTitle,
            largeTitle = largeTitle,
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
            showBottomNavigation = showBottomNavigation,
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
                catalogListState = catalogListState,
                nestedScrollModifier = nestedScrollModifier,
                onRecreateTheme = ::recreateForThemeChange,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
