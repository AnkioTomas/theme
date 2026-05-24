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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.FloatingNavigationBarItem as MiuixFloatingNavigationBarItem
import top.yukonga.miuix.kmp.basic.NavigationBarItem as MiuixNavigationBarItem

/**
 * 底部导航栏 Item 兼容层。
 *
 * 根据外层 [ThemeNavigationBar] 的 `floating` 参数（通过 [LocalIsFloatingNavBar] 传递）
 * 自动选择普通 / 悬浮渲染：
 * - 普通 + Material → [NavigationBarItem]
 * - 普通 + Miuix → [MiuixNavigationBarItem]
 * - 悬浮 + Material → 自实现紧凑 Column（对齐 Miuix Floating 视觉）
 * - 悬浮 + Miuix → [MiuixFloatingNavigationBarItem]
 *
 * @param selected 是否选中
 * @param onClick 点击回调
 * @param icon 图标
 * @param label 标签文本
 */
@Composable
fun RowScope.ThemeNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
) {
    val floating = LocalIsFloatingNavBar.current
    when (LocalUiMode.current) {
        UiMode.Material -> if (floating) {
            MaterialFloatingItem(selected, onClick, icon, label, modifier)
        } else {
            val colors = AnkioTheme.colorScheme
            NavigationBarItem(
                selected = selected,
                onClick = onClick,
                modifier = modifier,
                icon = {
                    ThemeIcon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = if (selected) colors.primary else colors.onSurfaceVariant,
                    )
                },
                label = {
                    ThemeText(
                        text = label,
                        style = AnkioTheme.textStyles.footnote1,
                        color = if (selected) colors.primary else colors.onSurfaceVariant,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = colors.primaryContainer,
                ),
            )
        }

        UiMode.Miuix -> if (floating) {
            MiuixFloatingNavigationBarItem(
                selected = selected,
                onClick = onClick,
                modifier = modifier,
                icon = icon,
                label = label,
            )
        } else {
            MiuixNavigationBarItem(
                selected = selected,
                onClick = onClick,
                modifier = modifier,
                icon = icon,
                label = label,
            )
        }
    }
}

/**
 * Material floating 模式下的紧凑 item，对齐 Miuix [MiuixFloatingNavigationBarItem]
 * 的"图标 + 标签"上下排版与色调（选中态走 primary、未选中态降透明度）。
 */
@Composable
private fun MaterialFloatingItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String,
    modifier: Modifier,
) {
    val colors = AnkioTheme.colorScheme
    val tint = if (selected) colors.primary else colors.onSurfaceVariant
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ThemeIcon(
            imageVector = icon,
            contentDescription = label,
            tint = tint,
        )
        Spacer(Modifier.height(2.dp))
        ThemeText(
            text = label,
            style = AnkioTheme.textStyles.footnote2,
            color = tint,
        )
    }
}
