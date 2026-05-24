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

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.NavigationRail
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.NavigationRail as MiuixNavigationRail

/**
 * 侧边导航栏兼容层，适用于宽屏。
 * Material 使用 NavigationRail，Miuix 使用 Miuix NavigationRail。
 */
@Composable
fun ThemeNavigationRail(
    modifier: Modifier = Modifier,
    containerColor: Color = AnkioTheme.colorScheme.surface,
    contentColor: Color = AnkioTheme.colorScheme.onSurface,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    showDivider: Boolean = true,
    minWidth: Dp = 80.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    when (LocalUiMode.current) {
        UiMode.Material -> Row(modifier = modifier) {
            NavigationRail(
                modifier = Modifier.widthIn(min = minWidth),
                containerColor = containerColor,
                contentColor = contentColor,
                header = header,
                content = content,
            )
            if (showDivider) {
                ThemeVerticalDivider(modifier = Modifier.fillMaxHeight())
            }
        }

        UiMode.Miuix -> MiuixNavigationRail(
            modifier = modifier,
            header = header,
            color = containerColor,
            showDivider = showDivider,
            minWidth = minWidth,
            content = content,
        )
    }
}
