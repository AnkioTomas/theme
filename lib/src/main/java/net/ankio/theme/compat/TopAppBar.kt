/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-3.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package net.ankio.theme.compat

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.core.view.WindowCompat
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.SmallTopAppBar as MiuixSmallTopAppBar
import top.yukonga.miuix.kmp.basic.TopAppBar as MiuixTopAppBar

/**
 * 顶部应用栏兼容层。
 *
 * - **Miuix**：支持大标题滚动折叠（[ThemeTopAppBarScroll] + [rememberThemeTopAppBarScroll]）
 * - **Material**：折叠时用 [LargeTopAppBar]；不折叠时按 [titleAlignment] 选 Top / CenterAligned
 *
 * @param largeTitle 大标题文案；为 null 且开启折叠时与 [title] 相同
 * @param scroll 由 [rememberThemeTopAppBarScroll] 创建；为 null 时不折叠
 * @param titleAlignment 折叠收起后的小标题对齐；大标题区在 Miuix / Material Large 下为起始对齐
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    titleAlignment: ThemeTopAppBarTitleAlignment = ThemeTopAppBarTitleAlignment.Center,
    largeTitle: String? = null,
    scroll: ThemeTopAppBarScroll? = null,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    val colors = AnkioTheme.colorScheme
    val barColors = TopAppBarDefaults.topAppBarColors(
        containerColor = colors.surface,
        scrolledContainerColor = colors.surfaceContainer,
        titleContentColor = colors.onSurface,
        navigationIconContentColor = colors.onSurface,
        actionIconContentColor = colors.onSurface,
    )

    when (LocalUiMode.current) {
        UiMode.Material -> {
            val materialScroll = scroll?.material
            SyncMaterialStatusBar(
                containerColor = colors.surface,
                scrolledContainerColor = colors.surfaceContainer,
                scrollBehavior = materialScroll,
            )
            if (materialScroll != null) {
                LargeTopAppBar(
                    title = {
                        MaterialCollapsedTitle(
                            text = title,
                            alignment = titleAlignment,
                            color = colors.onSurface,
                        )
                    },
                    modifier = modifier,
                    navigationIcon = navigationIcon,
                    actions = actions,
                    scrollBehavior = materialScroll,
                    colors = barColors,
                )
            } else when (titleAlignment) {
                ThemeTopAppBarTitleAlignment.Start -> TopAppBar(
                    title = { Text(title) },
                    modifier = modifier,
                    navigationIcon = navigationIcon,
                    actions = actions,
                    colors = barColors,
                )

                ThemeTopAppBarTitleAlignment.Center -> CenterAlignedTopAppBar(
                    title = { Text(title) },
                    modifier = modifier,
                    navigationIcon = navigationIcon,
                    actions = actions,
                    colors = barColors,
                )
            }
        }

        UiMode.Miuix -> {
            val resolvedLargeTitle = largeTitle ?: title
            val miuixScroll = scroll?.miuix
            if (miuixScroll != null) {
                MiuixTopAppBar(
                    title = title,
                    modifier = modifier,
                    color = colors.surface,
                    largeTitle = resolvedLargeTitle,
                    navigationIcon = navigationIcon,
                    actions = actions,
                    scrollBehavior = miuixScroll,
                )
            } else when (titleAlignment) {
                ThemeTopAppBarTitleAlignment.Center -> MiuixSmallTopAppBar(
                    title = title,
                    modifier = modifier,
                    color = colors.surface,
                    navigationIcon = navigationIcon,
                    actions = actions,
                )

                ThemeTopAppBarTitleAlignment.Start -> MiuixTopAppBar(
                    title = title,
                    modifier = modifier,
                    color = colors.surface,
                    largeTitle = null,
                    navigationIcon = navigationIcon,
                    actions = actions,
                    scrollBehavior = null,
                )
            }
        }
    }
}

/**
 * Material 顶栏与系统状态栏颜色同步（含 [LargeTopAppBar] 折叠渐变）。
 * 通过 [snapshotFlow] 监听滚动，避免仅 [SideEffect] 时折叠过程不触发重组。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SyncMaterialStatusBar(
    containerColor: Color,
    scrolledContainerColor: Color,
    scrollBehavior: TopAppBarScrollBehavior?,
) {
    val view = LocalView.current
    if (scrollBehavior != null) {
        LaunchedEffect(scrollBehavior, containerColor, scrolledContainerColor, view) {
            snapshotFlow { scrollBehavior.state.overlappedFraction }
                .collect { fraction ->
                    applyMaterialStatusBarColor(
                        view = view,
                        color = lerp(
                            containerColor,
                            scrolledContainerColor,
                            fraction.coerceIn(0f, 1f),
                        ),
                    )
                }
        }
    } else {
        SideEffect {
            applyMaterialStatusBarColor(view, containerColor)
        }
    }
}

private fun applyMaterialStatusBarColor(view: android.view.View, color: Color) {
    runCatching {
        val window = (view.context as android.app.Activity).window
        window.statusBarColor = color.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
            color.luminance() > 0.5f
    }
}

@Composable
private fun MaterialCollapsedTitle(
    text: String,
    alignment: ThemeTopAppBarTitleAlignment,
    color: Color,
) {
    when (alignment) {
        ThemeTopAppBarTitleAlignment.Start -> Text(text = text, color = color)
        ThemeTopAppBarTitleAlignment.Center -> Text(
            text = text,
            color = color,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}
