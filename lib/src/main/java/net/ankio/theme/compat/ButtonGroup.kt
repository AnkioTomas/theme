/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.compat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 连体按钮组容器。
 *
 * 组内按钮使用 [ThemeGroupButton]，通过 [ThemeButtonStyle] 选择 Primary / Secondary / [ThemeButtonStyle.Custom]。
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
 * 组内按钮。根据 [position] 自动调整圆角；[style] 默认 [ThemeButtonStyle.Secondary]。
 */
@Composable
fun ThemeGroupButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    position: ButtonGroupPosition = ButtonGroupPosition.Solo,
    style: ThemeButtonStyle = ThemeButtonStyle.Secondary,
    bigRadius: Dp = 24.dp,
    smallRadius: Dp = 12.dp,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    ThemeButton(
        onClick = onClick,
        style = style,
        modifier = modifier,
        enabled = enabled,
        groupPosition = position,
        bigRadius = bigRadius,
        smallRadius = smallRadius,
        content = content,
    )
}

@Composable
fun ThemeGroupButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    position: ButtonGroupPosition = ButtonGroupPosition.Solo,
    style: ThemeButtonStyle = ThemeButtonStyle.Secondary,
    bigRadius: Dp = 24.dp,
    smallRadius: Dp = 12.dp,
    enabled: Boolean = true,
) = ThemeGroupButton(
    onClick = onClick,
    modifier = modifier,
    position = position,
    style = style,
    bigRadius = bigRadius,
    smallRadius = smallRadius,
    enabled = enabled,
) {
    ThemeButtonLabel(text)
}

/** 组内自定义色按钮（[ThemeButtonStyle.Custom] 的便捷入口）。 */
@Composable
fun ThemeGroupCustomButton(
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    position: ButtonGroupPosition = ButtonGroupPosition.Solo,
    bigRadius: Dp = 24.dp,
    smallRadius: Dp = 12.dp,
    enabled: Boolean = true,
    disabledContainerColor: Color = containerColor.copy(alpha = 0.38f),
    disabledContentColor: Color = contentColor.copy(alpha = 0.38f),
    content: @Composable RowScope.() -> Unit,
) = ThemeGroupButton(
    onClick = onClick,
    modifier = modifier,
    position = position,
    style = ThemeButtonStyle.Custom(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    ),
    bigRadius = bigRadius,
    smallRadius = smallRadius,
    enabled = enabled,
    content = content,
)
