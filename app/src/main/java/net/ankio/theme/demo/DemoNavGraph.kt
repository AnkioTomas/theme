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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
 * 官方推荐：Navigation Compose + [NavHost] 与系统预测性返回联动（Activity 1.6+ / enableOnBackInvokedCallback）。
 * 根目的地无 [BackHandler] 时，返回手势由系统播放「返回主屏幕」等动画。
 */
@Composable
fun DemoNavHost(
    navController: NavHostController,
    catalogListState: LazyListState,
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
            catalogListState = catalogListState,
            nestedScrollModifier = nestedScrollModifier,
            onRecreateTheme = onRecreateTheme,
            onOpenCategory = { category ->
                navController.navigate(DemoRoutes.category(category))
            },
        )
    }
}

private fun NavGraphBuilder.demoDestination(
    catalogListState: LazyListState,
    nestedScrollModifier: Modifier,
    onRecreateTheme: () -> Unit,
    onOpenCategory: (DemoCategory) -> Unit,
) {
    composable(DemoRoutes.Catalog) {
        CatalogScreen(
            listState = catalogListState,
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
        CategoryDetailScreen(
            category = category,
            nestedScrollModifier = nestedScrollModifier,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
