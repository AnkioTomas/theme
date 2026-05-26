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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.FloatingToolbar as MiuixFloatingToolbar
import top.yukonga.miuix.kmp.basic.FloatingToolbarDefaults

/** [ThemeFloatingToolbar] 默认尺寸与间距。 */
object ThemeFloatingToolbarDefaults {
    val CornerRadius: Dp = FloatingToolbarDefaults.CornerRadius
    val ContentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
    val ItemSpacing: Dp = 8.dp
    val ShadowElevation: Dp = 4.dp

    val ContentArrangement: Arrangement.Horizontal = Arrangement.spacedBy(
        space = ItemSpacing,
        alignment = Alignment.CenterHorizontally,
    )
}

/**
 * 浮动工具栏兼容层。
 * Material 使用 Card 模拟，Miuix 使用 Miuix FloatingToolbar。
 *
 * 内容默认在工具栏内 **水平居中** 排列（[ThemeFloatingToolbarDefaults.ContentArrangement]）；
 * 子项直接写在 lambda 内即可，无需再包一层 `Row`。
 *
 * 若需占满宽度并 `SpaceEvenly` 等布局，对 [modifier] 使用 `fillMaxWidth()`，
 * 并传入自定义 [horizontalArrangement]。
 */
@Composable
fun ThemeFloatingToolbar(
    modifier: Modifier = Modifier,
    color: Color = AnkioTheme.colorScheme.surfaceContainer,
    cornerRadius: Dp = ThemeFloatingToolbarDefaults.CornerRadius,
    shadowElevation: Dp = ThemeFloatingToolbarDefaults.ShadowElevation,
    showDivider: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal = ThemeFloatingToolbarDefaults.ContentArrangement,
    content: @Composable RowScope.() -> Unit,
) {
    val toolbarBody: @Composable () -> Unit = {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ThemeFloatingToolbarDefaults.ContentPadding),
                horizontalArrangement = horizontalArrangement,
                verticalAlignment = Alignment.CenterVertically,
                content = content,
            )
        }
    }

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
                    toolbarBody()
                }
            },
        )

        UiMode.Miuix -> MiuixFloatingToolbar(
            modifier = modifier,
            color = color,
            cornerRadius = cornerRadius,
            shadowElevation = shadowElevation,
            showDivider = showDivider,
            outSidePadding = PaddingValues(),
            content = toolbarBody,
        )
    }
}
