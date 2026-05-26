/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import net.ankio.theme.BaseComposeActivity
import net.ankio.theme.compat.ThemeTopAppBarTitleAlignment
import net.ankio.theme.demo.catalog.CatalogScreen
import net.ankio.theme.demo.catalog.CategoryDetailScreen
import net.ankio.theme.demo.catalog.DemoCategory
import net.ankio.theme.settings.UiSettingsScreen

class MainActivity : BaseComposeActivity() {

    @Composable
    override fun Content() {
        var bottomTab by rememberSaveable { mutableStateOf(DemoBottomTab.Catalog) }
        var openCategory by rememberSaveable { mutableStateOf<DemoCategory?>(null) }
        var titleAlignment by rememberSaveable {
            mutableStateOf(ThemeTopAppBarTitleAlignment.Center)
        }

        val catalogListState = rememberSaveable(saver = LazyListState.Saver) {
            LazyListState()
        }

        DemoPredictiveBackNavigation(
            openCategory = openCategory != null,
            onSettingsTab = bottomTab == DemoBottomTab.Settings,
            onPopCategory = { openCategory = null },
            onLeaveSettingsTab = { bottomTab = DemoBottomTab.Catalog },
        ) { categoryBackProgress ->
            val topTitle = when {
                openCategory != null -> openCategory!!.title
                bottomTab == DemoBottomTab.Settings -> "主题设置"
                else -> "Theme Demo"
            }

            val largeTitle = when {
                openCategory != null -> openCategory!!.title
                bottomTab == DemoBottomTab.Settings -> "主题设置"
                else -> "Theme 组件目录"
            }

            val showTitleAlignmentPicker = openCategory == null && bottomTab == DemoBottomTab.Catalog
            val collapseOnScroll = openCategory != null || bottomTab == DemoBottomTab.Catalog

            DemoAppShell(
                title = topTitle,
                largeTitle = largeTitle,
                showBack = openCategory != null,
                onBack = { openCategory = null },
                onRecreateTheme = ::recreateForThemeChange,
                titleAlignment = titleAlignment,
                onTitleAlignmentChange = if (showTitleAlignmentPicker) {
                    { titleAlignment = it }
                } else {
                    null
                },
                collapseOnScroll = collapseOnScroll,
                showBottomNavigation = openCategory == null,
                selectedTab = bottomTab,
                onTabSelected = { tab ->
                    bottomTab = tab
                    openCategory = null
                },
            ) { nestedScrollModifier ->
                val predictiveModifier = if (openCategory != null) {
                    Modifier.graphicsLayer {
                        translationX = size.width * categoryBackProgress * 0.35f
                        alpha = 1f - categoryBackProgress * 0.12f
                    }
                } else {
                    Modifier
                }

                when {
                    openCategory != null -> CategoryDetailScreen(
                        category = openCategory!!,
                        nestedScrollModifier = nestedScrollModifier,
                        modifier = Modifier
                            .fillMaxSize()
                            .then(predictiveModifier),
                    )

                    bottomTab == DemoBottomTab.Settings -> UiSettingsScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        onThemeChanged = ::recreateForThemeChange,
                    )

                    else -> CatalogScreen(
                        listState = catalogListState,
                        nestedScrollModifier = nestedScrollModifier,
                        onOpenCategory = { openCategory = it },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}
