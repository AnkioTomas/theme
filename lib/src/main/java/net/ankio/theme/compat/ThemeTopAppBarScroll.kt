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
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
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
 * 将 [nestedScrollConnection] 通过 [Modifier.nestedScroll] 挂到列表/滚动容器上，滚动时大标题会上移折叠。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Stable
class ThemeTopAppBarScroll internal constructor(
    internal val material: TopAppBarScrollBehavior?,
    internal val miuix: ScrollBehavior?,
) {
    val nestedScrollConnection: NestedScrollConnection
        get() = material?.nestedScrollConnection
            ?: miuix?.nestedScrollConnection
            ?: error("ThemeTopAppBarScroll: no scroll behavior")

    val isActive: Boolean = material != null || miuix != null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberThemeTopAppBarScroll(collapseOnScroll: Boolean): ThemeTopAppBarScroll? {
    if (!collapseOnScroll) return null
    return when (LocalUiMode.current) {
        UiMode.Material -> {
            val state = rememberTopAppBarState()
            ThemeTopAppBarScroll(
                material = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(state),
                miuix = null,
            )
        }

        UiMode.Miuix -> {
            val state = rememberMiuixTopAppBarState()
            ThemeTopAppBarScroll(
                material = null,
                miuix = MiuixScrollBehavior(state),
            )
        }
    }
}
