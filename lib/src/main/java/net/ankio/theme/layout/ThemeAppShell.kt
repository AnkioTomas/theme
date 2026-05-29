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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemeTopAppBar
import net.ankio.theme.compat.ThemeTopAppBarTitleAlignment
import net.ankio.theme.compat.ThemeTopAppBarScroll
import net.ankio.theme.compat.rememberThemeTopAppBarScroll

/**
 * 应用主布局壳：可折叠 [ThemeTopAppBar] + 可选底部栏 + 可嵌套滚动的内容区。
 *
 * 内容区通过 [content] 接收 [Modifier]，须挂到 LazyColumn / verticalScroll 等滚动容器上，
 * 以便 TopBar 折叠与颜色过渡与列表联动。
 *
 * 配合 Navigation Compose 与 `android:enableOnBackInvokedCallback="true"` 使用系统预测性返回。
 */
@Composable
fun ThemeAppShell(
    title: String,
    modifier: Modifier = Modifier,
    largeTitle: String? = null,
    titleAlignment: ThemeTopAppBarTitleAlignment = ThemeTopAppBarTitleAlignment.Center,
    topAppBarScrollKey: Any? = null,
    collapseOnScroll: Boolean = true,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    topAccessory: @Composable () -> Unit = {},
    showBottomBar: Boolean = false,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (nestedScrollModifier: Modifier) -> Unit,
) {
    val nestedScrollModifier = rememberThemeAppShellNestedScroll(
        collapseOnScroll = collapseOnScroll,
        scrollKey = topAppBarScrollKey,
    )

    Column(modifier = modifier.fillMaxSize()) {
        ThemeTopAppBar(
            title = title,
            largeTitle = largeTitle ?: title,
            titleAlignment = titleAlignment,
            scroll = nestedScrollModifier.scroll,
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = navigationIcon,
            actions = actions,
        )

        topAccessory()

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .then(
                    if (!showBottomBar) {
                        Modifier.navigationBarsPadding()
                    } else {
                        Modifier
                    },
                ),
        ) {
            content(nestedScrollModifier.modifier)
        }

        if (showBottomBar) {
            bottomBar()
        }
    }
}

/**
 * TopBar 折叠所需的 nestedScroll [Modifier]。
 * [scrollKey] 变化时重建 scroll behavior（例如 Nav 目的地切换）。
 */
@Composable
fun rememberThemeAppShellNestedScroll(
    collapseOnScroll: Boolean,
    scrollKey: Any? = null,
): ThemeAppShellNestedScroll {
    val scroll = if (collapseOnScroll) {
        key(scrollKey) {
            rememberThemeTopAppBarScroll(collapseOnScroll = true)
        }
    } else {
        null
    }
    val modifier = scroll?.contentModifier ?: Modifier
    return ThemeAppShellNestedScroll(scroll = scroll, modifier = modifier)
}

/** [rememberThemeAppShellNestedScroll] 的返回值。 */
data class ThemeAppShellNestedScroll(
    val scroll: ThemeTopAppBarScroll?,
    val modifier: Modifier,
)

/** 壳层 TopBar 常用返回按钮。 */
@Composable
fun ThemeShellBackButton(
    onClick: () -> Unit,
    contentDescription: String = "返回",
) {
    ThemeIconButton(onClick = onClick) {
        ThemeIcon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = contentDescription,
            tint = AnkioTheme.colorScheme.onSurface,
        )
    }
}
