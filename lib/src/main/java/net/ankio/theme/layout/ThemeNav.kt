/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.layout

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptionsBuilder

/**
 * Navigation Compose 辅助：预测性返回、列表状态保留、详情页转场。
 *
 * 使用方式：
 * 1. Manifest / Activity 开启 `android:enableOnBackInvokedCallback="true"`
 * 2. [androidx.navigation.compose.NavHost] 管理返回栈，勿手写位移拦截系统返回
 * 3. 列表页用 [rememberNavDestinationLazyListState] 绑定 [NavBackStackEntry]
 * 4. 详情路由在 `composable(...)` 上挂 [ThemeNavTransitions]
 */
object ThemeNavTransitions {
    /** 进入详情：自右滑入 */
    val detailEnter: AnimatedContentTransitionScope<*>.() -> EnterTransition = {
        slideInHorizontally(animationSpec = tween()) { width -> width }
    }

    /** 离开详情（进入子页时）：向左轻移 */
    val detailExit: AnimatedContentTransitionScope<*>.() -> ExitTransition = {
        slideOutHorizontally(animationSpec = tween()) { width -> -width / 3 }
    }

    /** 从详情返回：自左滑入 */
    val detailPopEnter: AnimatedContentTransitionScope<*>.() -> EnterTransition = {
        slideInHorizontally(animationSpec = tween()) { width -> -width / 3 }
    }

    /** 弹出详情：向右滑出 */
    val detailPopExit: AnimatedContentTransitionScope<*>.() -> ExitTransition = {
        slideOutHorizontally(animationSpec = tween()) { width -> width }
    }
}

/**
 * 将 [LazyListState] 绑定到 Nav 返回栈条目，切换 Tab / 返回时恢复滚动位置。
 */
@Composable
fun rememberNavDestinationLazyListState(entry: NavBackStackEntry): LazyListState =
    rememberSaveable(entry, saver = LazyListState.Saver) {
        LazyListState()
    }

/**
 * 顶层 Tab 切换：清空到 startDestination 并保留/恢复各 Tab 状态。
 */
fun NavOptionsBuilder.topLevelTab(
    startDestinationId: Int,
) {
    popUpTo(startDestinationId) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}
