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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.FloatingToolbar as MiuixFloatingToolbar
import top.yukonga.miuix.kmp.basic.FloatingToolbarDefaults

/**
 * 浮动工具栏兼容层。
 * Material 使用 Card 模拟，Miuix 使用 Miuix FloatingToolbar。
 */
@Composable
fun ThemeFloatingToolbar(
    modifier: Modifier = Modifier,
    color: Color = AnkioTheme.colorScheme.surfaceContainer,
    cornerRadius: Dp = FloatingToolbarDefaults.CornerRadius,
    shadowElevation: Dp = 4.dp,
    showDivider: Boolean = false,
    content: @Composable () -> Unit,
) {
    when (LocalUiMode.current) {
        UiMode.Material -> Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = color),
            shape = RoundedCornerShape(cornerRadius),
            elevation = CardDefaults.cardElevation(defaultElevation = shadowElevation),
            content = {
                Column {
                    if (showDivider) {
                        ThemeHorizontalDivider()
                    }
                    content()
                }
            },
        )

        UiMode.Miuix -> MiuixFloatingToolbar(
            modifier = modifier,
            color = color,
            cornerRadius = cornerRadius,
            shadowElevation = shadowElevation,
            showDivider = showDivider,
            content = content,
        )
    }
}
