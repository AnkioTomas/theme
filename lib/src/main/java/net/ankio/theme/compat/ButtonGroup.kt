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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.Button as MiuixButton
import top.yukonga.miuix.kmp.basic.ButtonDefaults as MiuixButtonDefaults

/**
 * 连体按钮组容器。
 *
 * Material3 1.4.0 仅有 ButtonGroup 的 design tokens，公开 Composable API 尚未 expose。
 * Miuix 同样无原生 ButtonGroup 组件。
 * 这里统一以 Row 实现，内部 [ThemeGroupButton] 按位置自动处理首/中/尾圆角，
 * 视觉上与 Material XML 的 Connected ButtonGroup 一致。
 */
@Composable
fun ThemeButtonGroup(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = content,
    )
}

/** 按钮在组内的位置 */
enum class ButtonGroupPosition { Start, Middle, End, Solo }

/**
 * 组内按钮。根据 [position] 自动调整圆角，实现连体外观。
 * - Start  → 左边大圆角，右边小圆角
 * - Middle → 两边均为小圆角
 * - End    → 左边小圆角，右边大圆角
 * - Solo   → 两边均为大圆角（单独使用时）
 */
@Composable
fun ThemeGroupButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    position: ButtonGroupPosition = ButtonGroupPosition.Solo,
    bigRadius: Dp = 24.dp,
    smallRadius: Dp = 12.dp,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    val shape = when (position) {
        ButtonGroupPosition.Start -> RoundedCornerShape(
            topStart = bigRadius, bottomStart = bigRadius,
            topEnd = smallRadius, bottomEnd = smallRadius,
        )
        ButtonGroupPosition.Middle -> RoundedCornerShape(smallRadius)
        ButtonGroupPosition.End -> RoundedCornerShape(
            topStart = smallRadius, bottomStart = smallRadius,
            topEnd = bigRadius, bottomEnd = bigRadius,
        )
        ButtonGroupPosition.Solo -> RoundedCornerShape(bigRadius)
    }
    val colors = AnkioTheme.colorScheme

    when (LocalUiMode.current) {
        UiMode.Material -> FilledTonalButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            shape = shape,
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = colors.secondaryContainer,
                contentColor = colors.onSecondaryContainer,
            ),
            content = content,
        )
        // Miuix ButtonColors only exposes background color; text color is determined internally
        UiMode.Miuix -> MiuixButton(
            onClick = onClick,
            // Miuix Button only accepts a uniform corner radius; clip enforces per-corner group shape.
            modifier = modifier.clip(shape),
            enabled = enabled,
            cornerRadius = 0.dp,
            colors = MiuixButtonDefaults.buttonColors(
                color = colors.secondaryContainer,
                disabledColor = colors.secondaryContainer.copy(alpha = 0.38f),
            ),
            content = content,
        )
    }
}
