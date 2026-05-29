/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import net.ankio.theme.compat.ThemeNavigationBar
import net.ankio.theme.compat.ThemeNavigationBarItem
import net.ankio.theme.compat.ThemeTopAppBarTitleAlignment

/**
 * 把整个 App 的「外壳」包在一起，业务只负责声明有哪些页面。
 *
 * ## 你写什么
 * - 外层参数：首屏路由 [start]、TopBar 右侧 [actions]、TopBar 下方 [header]
 * - `screens { screen(...) { 页面 UI } }`：每个 [screen] 对应一条 Nav 路由
 *
 * ## 框架替你做什么（不用手写）
 * 1. 创建 [NavHost] 和返回栈（系统预测性返回靠这个）
 * 2. 根据**当前路由**切换 TopBar 标题、是否显示返回键、是否显示 BottomBar
 * 3. 把 [nestedScroll] 传给当前页，让列表滚动时 TopBar 能折叠
 *
 * ## 结构（从上到下）
 * ```
 * ThemeAppShell          ← TopBar + header 插槽 + 内容区 + BottomBar
 *   └─ NavHost           ← 中间区域，按路由显示某一个 screen { } 里的 UI
 * ```
 *
 * ## screen 里的大括号
 * 类型是 [ThemeScope].() -> Unit：列表页用 [ThemeScope.lazyList]，非列表页用 [ThemeScope.scrollColumn]。
 *
 * ## 常见参数
 * - `tab = 图标 to "文案"` → 该页是底部 Tab 之一
 * - `detail = true` → 子页，带左右滑入/滑出转场
 * - `collapse = false` → 该页不让 TopBar 随滚动折叠（默认会折叠）
 */
@Composable
fun ThemeApp(
    start: String,
    modifier: Modifier = Modifier,
    titleAlignment: ThemeTopAppBarTitleAlignment = ThemeTopAppBarTitleAlignment.Center,
    actions: @Composable RowScope.() -> Unit = {},
    header: @Composable (route: String?) -> Unit = {},
    screens: ThemeAppBuilder.() -> Unit,
) {
    // ① 先执行 screens { }，收集所有 screen() 注册信息（还不会画 UI）
    val graph = ThemeAppBuilder().apply(screens)
    val nav = rememberNavController()
    // ② 当前显示哪一页 → 决定 TopBar 标题、BottomBar 显隐
    val entry by nav.currentBackStackEntryAsState()
    val dest = entry?.let { graph.of(it) }

    ThemeAppShell(
        modifier = modifier,
        title = entry?.let { dest?.title?.invoke(it) }.orEmpty(),
        largeTitle = entry?.let { dest?.largeTitle?.invoke(it) }.orEmpty(),
        titleAlignment = titleAlignment,
        topAppBarScrollKey = entry?.let { dest?.scrollKey?.invoke(it) ?: it.id },
        collapseOnScroll = dest?.collapseOnScroll ?: false,
        navigationIcon = {
            // Tab 根页（带 tab）不显示返回键；仅 detail / 无 tab 子页在可 pop 时显示
            if (dest?.tab == null && nav.previousBackStackEntry != null) {
                ThemeShellBackButton(onClick = { nav.popBackStack() })
            }
        },
        actions = actions,
        topAccessory = { header(entry?.destination?.route) },
        showBottomBar = dest?.tab != null && graph.tabs.isNotEmpty(),
        bottomBar = {
            ThemeNavigationBar(Modifier.fillMaxWidth().navigationBarsPadding()) {
                graph.tabs.forEach { tab ->
                    ThemeNavigationBarItem(
                        selected = entry?.destination?.route == tab.route,
                        onClick = {
                            nav.navigate(tab.route) {
                                topLevelTab(nav.graph.startDestinationId)
                            }
                        },
                        icon = tab.icon,
                        label = tab.label,
                    )
                }
            }
        },
    ) { nested ->
        NavHost(nav, start, Modifier.fillMaxSize()) {
            graph.all.forEach { spec ->
                if (spec.detail) {
                    composable(
                        route = spec.route,
                        arguments = spec.args,
                        enterTransition = ThemeNavTransitions.detailEnter,
                        exitTransition = ThemeNavTransitions.detailExit,
                        popEnterTransition = ThemeNavTransitions.detailPopEnter,
                        popExitTransition = ThemeNavTransitions.detailPopExit,
                    ) { e -> spec.content(ThemeScope(nav, e, nested)) }
                } else {
                    composable(route = spec.route, arguments = spec.args) { e ->
                        spec.content(ThemeScope(nav, e, nested))
                    }
                }
            }
        }
    }
}

/**
 * 某个 [screen] 正在显示时，其 `{ }` 内部的「this」。
 * 提供导航和滚动相关能力，避免业务直接操作 NavController。
 */
class ThemeScope internal constructor(
    private val nav: NavHostController,
    val entry: NavBackStackEntry,
    private val nestedScroll: Modifier,
) {
    fun arg(key: String): String? = entry.arguments?.getString(key)

    fun go(route: String, block: NavOptionsBuilder.() -> Unit = {}) = nav.navigate(route, block)

    fun back() = nav.popBackStack()

    /**
     * 给内部使用 [androidx.compose.foundation.lazy.LazyColumn] 的页面用：
     * 一次拿到列表状态 + 已挂 TopBar 联动的 [Modifier]。
     */
    @Composable
    fun lazyList(modifier: Modifier = Modifier.fillMaxSize()): ThemeLazyListScroll =
        ThemeLazyListScroll(
            state = rememberNavDestinationLazyListState(entry),
            modifier = modifier.then(nestedScroll),
        )

    /**
     * 给**内部没有滚动**的内容包一层 `verticalScroll`，并挂上 TopBar 联动。
     */
    @Composable
    fun scrollColumn(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
        val scrollState = rememberSaveable(entry.id, saver = ScrollState.Saver) {
            ScrollState(0)
        }
        Column(
            modifier
                .fillMaxSize()
                .then(nestedScroll)
                .verticalScroll(scrollState),
        ) {
            content()
        }
    }
}

/**
 * [ThemeScope.lazyList] 的返回值：`LazyColumn` 的 state 与 modifier 成对使用，勿拆开传参。
 */
@Stable
class ThemeLazyListScroll internal constructor(
    val state: LazyListState,
    val modifier: Modifier,
)

@DslMarker
private annotation class ThemeAppDsl

/** 仅用于在 `screens { }` 里调用 [screen]，收集路由表。 */
@ThemeAppDsl
class ThemeAppBuilder {
    internal val all = mutableListOf<ScreenSpec>()
    internal val tabs = mutableListOf<TabSpec>()

    fun screen(
        route: String,
        title: String,
        largeTitle: String = title,
        tab: Pair<ImageVector, String>? = null,
        detail: Boolean = false,
        collapse: Boolean = true,
        args: List<NamedNavArgument> = emptyList(),
        content: @Composable ThemeScope.() -> Unit,
    ) = add(
        ScreenSpec(route, { title }, { largeTitle }, tab, detail, collapse, { route }, args, content),
    )

    fun screen(
        route: String,
        title: (NavBackStackEntry) -> String,
        largeTitle: (NavBackStackEntry) -> String = title,
        tab: Pair<ImageVector, String>? = null,
        detail: Boolean = false,
        collapse: Boolean = true,
        scrollKey: (NavBackStackEntry) -> Any? = { it.id },
        args: List<NamedNavArgument> = emptyList(),
        content: @Composable ThemeScope.() -> Unit,
    ) = add(ScreenSpec(route, title, largeTitle, tab, detail, collapse, scrollKey, args, content))

    private fun add(spec: ScreenSpec) {
        all += spec
        spec.tab?.let { tabs += TabSpec(spec.route, it.first, it.second) }
    }

    internal fun of(entry: NavBackStackEntry): ScreenSpec? =
        all.find { it.route == entry.destination.route }
}

internal data class ScreenSpec(
    val route: String,
    val title: (NavBackStackEntry) -> String,
    val largeTitle: (NavBackStackEntry) -> String,
    val tab: Pair<ImageVector, String>?,
    val detail: Boolean,
    val collapseOnScroll: Boolean,
    val scrollKey: (NavBackStackEntry) -> Any?,
    val args: List<NamedNavArgument>,
    val content: @Composable ThemeScope.() -> Unit,
)

internal data class TabSpec(val route: String, val icon: ImageVector, val label: String)

fun navArgs(block: NavArgsBuilder.() -> Unit): List<NamedNavArgument> =
    NavArgsBuilder().apply(block).build()

class NavArgsBuilder {
    private val list = mutableListOf<NamedNavArgument>()
    fun string(name: String) {
        list += navArgument(name) { type = NavType.StringType }
    }
    fun build() = list
}
