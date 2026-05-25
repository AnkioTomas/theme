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
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.NavigationBarItem as MiuixNavigationBarItem

/** 底部导航栏 Item 兼容层。Material 使用 [NavigationBarItem]，Miuix 使用 [MiuixNavigationBarItem]。 */
@Composable
fun RowScope.ThemeNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
) {
    when (LocalUiMode.current) {
        UiMode.Material -> {
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

        UiMode.Miuix -> MiuixNavigationBarItem(
            selected = selected,
            onClick = onClick,
            modifier = modifier,
            icon = icon,
            label = label,
        )
    }
}
