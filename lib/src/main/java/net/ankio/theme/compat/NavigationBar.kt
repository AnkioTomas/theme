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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.ThemeSettings
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.FloatingNavigationBar as MiuixFloatingNavigationBar
import top.yukonga.miuix.kmp.basic.FloatingNavigationBarDisplayMode as MiuixFloatingNavigationBarDisplayMode
import top.yukonga.miuix.kmp.basic.NavigationBar as MiuixNavigationBar

/**
 * 标记当前 NavigationBar 是否为悬浮模式，供子项 [ThemeNavigationBarItem] 选择渲染策略。
 * 仅 lib 内部使用，调用方通过 [ThemeNavigationBar] 的 `floating` 参数控制。
 */
internal val LocalIsFloatingNavBar = staticCompositionLocalOf { false }

/**
 * 底部导航栏兼容层。
 *
 * @param floating 是否悬浮模式：
 * - Miuix 走 [MiuixFloatingNavigationBar]（胶囊浮起、自带 navigationBars inset 处理）
 * - Material 用圆角 [Surface] 包一层 [Row] 模拟胶囊，避免 Material 没有官方 floating 组件的特殊情况
 *
 * 普通模式（floating = false）保持原有 NavigationBar 行为。
 *
 * @param showDivider 仅普通模式有效；floating 模式下视觉上无分隔线概念
 * @param mode floating 模式下的子项展示策略（图标/文字/两者）；普通模式忽略
 */
@Composable
fun ThemeNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = if (LocalIsFloatingNavBar.current) {
        AnkioTheme.colorScheme.surfaceContainer
    } else {
        AnkioTheme.colorScheme.surface
    },
    contentColor: Color = AnkioTheme.colorScheme.onSurface,
    showDivider: Boolean = true,
    floating: Boolean = ThemeSettings.navigationBarFloating,
    mode: ThemeFloatingNavBarMode = ThemeFloatingNavBarMode.IconAndText,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(LocalIsFloatingNavBar provides floating) {
        when (LocalUiMode.current) {
            UiMode.Material -> {
                if (floating) {
                    MaterialFloatingBar(modifier, containerColor, content)
                } else {
                    Column(modifier = modifier) {
                        if (showDivider) {
                            ThemeHorizontalDivider()
                        }
                        NavigationBar(
                            containerColor = containerColor,
                            contentColor = contentColor,
                            tonalElevation = NavigationBarDefaults.Elevation,
                            content = content,
                        )
                    }
                }
            }

            UiMode.Miuix -> {
                if (floating) {
                    MiuixFloatingNavigationBar(
                        modifier = modifier,
                        color = containerColor,
                        mode = mode.toMiuix(),
                    ) {
                        // Miuix FloatingNavigationBar 内部已是 Row(spacedBy=12dp)，
                        // 但其 content 为 () -> Unit，user content 需要 RowScope，
                        // 这里再包一层 Row 提供 RowScope —— 视觉上嵌套但成本低。
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            content = content,
                        )
                    }
                } else {
                    MiuixNavigationBar(
                        modifier = modifier,
                        color = containerColor,
                        showDivider = showDivider,
                        content = content,
                    )
                }
            }
        }
    }
}

/** Material 没有官方 floating navigation bar，用 Surface+Row 自实现，对齐 Miuix 视觉。 */
@Composable
private fun MaterialFloatingBar(
    modifier: Modifier,
    containerColor: Color,
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp, vertical = 12.dp),
        shape = RoundedCornerShape(28.dp),
        color = containerColor,
        shadowElevation = 4.dp,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }
}

/**
 * 悬浮 NavigationBar 子项展示策略：与 Miuix [MiuixFloatingNavigationBarDisplayMode] 一对一。
 * 暴露在 lib API 层，避免调用方直接依赖 Miuix 类型。
 */
enum class ThemeFloatingNavBarMode {
    IconAndText, IconOnly, TextOnly;

    internal fun toMiuix(): MiuixFloatingNavigationBarDisplayMode = when (this) {
        IconAndText -> MiuixFloatingNavigationBarDisplayMode.IconAndText
        IconOnly -> MiuixFloatingNavigationBarDisplayMode.IconOnly
        TextOnly -> MiuixFloatingNavigationBarDisplayMode.TextOnly
    }
}
