/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import net.ankio.theme.demo.catalog.CatalogScreen
import net.ankio.theme.demo.catalog.CategoryDetailScreen
import net.ankio.theme.demo.catalog.DemoCategory
import net.ankio.theme.settings.UiSettingsScreen

internal object DemoRoutes {
    const val Catalog = "catalog"
    const val Settings = "settings"
    const val Category = "category/{categoryName}"

    fun category(category: DemoCategory) = "category/${category.name}"
}

/**
 * 官方推荐：Navigation Compose + [NavHost] 与系统预测性返回联动。
 * 列表滚动等状态须绑定 [NavBackStackEntry]（见 [rememberDestinationListState]）。
 */
@Composable
fun DemoNavHost(
    navController: NavHostController,
    nestedScrollModifier: Modifier,
    onRecreateTheme: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = DemoRoutes.Catalog,
        modifier = modifier,
    ) {
        demoDestination(
            nestedScrollModifier = nestedScrollModifier,
            onRecreateTheme = onRecreateTheme,
            onOpenCategory = { category ->
                navController.navigate(DemoRoutes.category(category))
            },
        )
    }
}

@Composable
private fun rememberDestinationListState(entry: NavBackStackEntry): LazyListState =
    rememberSaveable(entry, saver = LazyListState.Saver) {
        LazyListState()
    }

private fun NavGraphBuilder.demoDestination(
    nestedScrollModifier: Modifier,
    onRecreateTheme: () -> Unit,
    onOpenCategory: (DemoCategory) -> Unit,
) {
    composable(DemoRoutes.Catalog) { entry ->
        val listState = rememberDestinationListState(entry)
        CatalogScreen(
            listState = listState,
            nestedScrollModifier = nestedScrollModifier,
            onOpenCategory = onOpenCategory,
            modifier = Modifier.fillMaxSize(),
        )
    }

    composable(DemoRoutes.Settings) {
        UiSettingsScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            onThemeChanged = onRecreateTheme,
        )
    }

    composable(
        route = DemoRoutes.Category,
        arguments = listOf(navArgument("categoryName") { type = NavType.StringType }),
        enterTransition = { slideInHorizontally { width -> width } },
        exitTransition = { slideOutHorizontally { width -> -width / 3 } },
        popEnterTransition = { slideInHorizontally { width -> -width / 3 } },
        popExitTransition = { slideOutHorizontally { width -> width } },
    ) { entry ->
        val name = entry.arguments?.getString("categoryName")
        val category = DemoCategory.entries.find { it.name == name } ?: return@composable
        val listState = rememberDestinationListState(entry)
        CategoryDetailScreen(
            category = category,
            listState = listState,
            nestedScrollModifier = nestedScrollModifier,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
