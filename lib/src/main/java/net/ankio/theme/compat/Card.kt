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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.Card as MiuixCard
import top.yukonga.miuix.kmp.basic.CardDefaults as MiuixCardDefaults
import top.yukonga.miuix.kmp.utils.PressFeedbackType

/** Material `shapes.medium` 默认 18dp，与之保持视觉一致 */
private val DEFAULT_CARD_CORNER = 18.dp

/**
 * Card：Miuix 用 Miuix Card，Material 用 Material Card。
 * 统一暴露平台无关的语义参数，内部映射到不同实现。
 *
 * @param shape Material 模式使用；默认 `MaterialTheme.shapes.medium`
 * @param cornerRadius Miuix 模式使用；默认 18dp，与 [shape] 默认值视觉一致
 * @param onClick 点击回调，null 时卡片不可点击
 */
@Composable
fun ThemeCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    containerColor: Color = AnkioTheme.colorScheme.surfaceContainerLow,
    contentColor: Color = AnkioTheme.colorScheme.onSurface,
    elevation: Dp = 0.dp,
    border: BorderStroke? = null,
    cornerRadius: Dp = DEFAULT_CARD_CORNER,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    when (LocalUiMode.current) {
        UiMode.Material -> MaterialCard(
            modifier = modifier,
            shape = shape,
            containerColor = containerColor,
            contentColor = contentColor,
            elevation = elevation,
            border = border,
            onClick = onClick,
            content = content,
        )

        UiMode.Miuix -> MiuixCard(
            modifier = modifier,
            cornerRadius = cornerRadius,
            colors = MiuixCardDefaults.defaultColors(
                color = containerColor,
                contentColor = contentColor,
            ),
            pressFeedbackType = if (onClick != null) PressFeedbackType.Sink else PressFeedbackType.None,
            showIndication = onClick != null,
            onClick = onClick,
            content = content,
        )
    }
}

/**
 * Material `Card` 有两个重载（带/不带 `onClick`），只能在此处分两路。
 * Miuix 的 Card 接受 `onClick: (() -> Unit)?`，无需此处理。
 */
@Composable
private fun MaterialCard(
    modifier: Modifier,
    shape: Shape,
    containerColor: Color,
    contentColor: Color,
    elevation: Dp,
    border: BorderStroke?,
    onClick: (() -> Unit)?,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = CardDefaults.cardColors(
        containerColor = containerColor,
        contentColor = contentColor,
    )
    val cardElevation = CardDefaults.cardElevation(defaultElevation = elevation)

    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            colors = colors,
            elevation = cardElevation,
            border = border,
            content = content,
        )
    } else {
        Card(
            modifier = modifier,
            shape = shape,
            colors = colors,
            elevation = cardElevation,
            border = border,
            content = content,
        )
    }
}
