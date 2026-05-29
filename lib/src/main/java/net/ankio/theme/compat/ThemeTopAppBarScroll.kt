/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.compat

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.basic.rememberTopAppBarState as rememberMiuixTopAppBarState

/** 顶部栏标题水平对齐 */
enum class ThemeTopAppBarTitleAlignment {
    Start, Center,
}

/**
 * 与 [ThemeTopAppBar] 配套的滚动折叠句柄。
 *
 * 用法：顶栏传 `scroll = scroll`；列表用 `Modifier.then(scroll.contentModifier)`。
 */
@Stable
sealed class ThemeTopAppBarScroll {
    abstract val nestedScrollConnection: NestedScrollConnection

    /** 挂到 LazyColumn / verticalScroll，与顶栏 [scrollBehavior] 联动折叠。 */
    val contentModifier: Modifier
        get() = Modifier.nestedScroll(nestedScrollConnection)

    @OptIn(ExperimentalMaterial3Api::class)
    internal class Material(
        internal val behavior: TopAppBarScrollBehavior,
    ) : ThemeTopAppBarScroll() {
        override val nestedScrollConnection: NestedScrollConnection
            get() = behavior.nestedScrollConnection
    }

    internal class Miuix(
        internal val behavior: ScrollBehavior,
    ) : ThemeTopAppBarScroll() {
        override val nestedScrollConnection: NestedScrollConnection
            get() = behavior.nestedScrollConnection
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberThemeTopAppBarScroll(collapseOnScroll: Boolean): ThemeTopAppBarScroll? {
    if (!collapseOnScroll) return null
    return when (LocalUiMode.current) {
        UiMode.Material -> {
            val state = rememberTopAppBarState()
            ThemeTopAppBarScroll.Material(
                TopAppBarDefaults.exitUntilCollapsedScrollBehavior(state),
            )
        }

        UiMode.Miuix -> {
            val state = rememberMiuixTopAppBarState()
            ThemeTopAppBarScroll.Miuix(MiuixScrollBehavior(state))
        }
    }
}
