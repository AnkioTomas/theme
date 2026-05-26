/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.compat

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.ButtonColors
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.basic.Button as MiuixButton
import top.yukonga.miuix.kmp.basic.ButtonDefaults as MiuixButtonDefaults

internal val LocalButtonContentColor = staticCompositionLocalOf { Color.Unspecified }

/** 按钮内默认文案（颜色由 [ThemePrimaryButton] / [ThemeSecondaryButton] 等注入）。 */
@Composable
fun ThemeButtonLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    val color = LocalButtonContentColor.current
    require(color != Color.Unspecified) {
        "ThemeButtonLabel 须放在 ThemePrimaryButton / ThemeSecondaryButton / ThemeCustomButton / ThemeGroupButton 内"
    }
    ThemeText(
        text = text,
        style = AnkioTheme.textStyles.button,
        color = color,
        modifier = modifier,
    )
}

/** 主按钮文案色（高级自定义内容时可选）。 */
@Composable
@ReadOnlyComposable
fun themePrimaryButtonContentColor(): Color = resolveContentColor(ThemeButtonStyle.Primary)

/** 次要 / 组内按钮文案色（高级自定义内容时可选）。 */
@Composable
@ReadOnlyComposable
fun themeSecondaryButtonContentColor(): Color = resolveContentColor(ThemeButtonStyle.Secondary)

@Composable
fun ThemePrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) = ThemeButton(
    onClick = onClick,
    style = ThemeButtonStyle.Primary,
    modifier = modifier,
    enabled = enabled,
    content = content,
)

@Composable
fun ThemePrimaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) = ThemePrimaryButton(onClick, modifier, enabled) {
    ThemeButtonLabel(text)
}

@Composable
fun ThemeSecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) = ThemeButton(
    onClick = onClick,
    style = ThemeButtonStyle.Secondary,
    modifier = modifier,
    enabled = enabled,
    content = content,
)

@Composable
fun ThemeSecondaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) = ThemeSecondaryButton(onClick, modifier, enabled) {
    ThemeButtonLabel(text)
}

/** 自定义容器色 / 文案色，不参与 Primary / Secondary 默认规则。 */
@Composable
fun ThemeCustomButton(
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    disabledContainerColor: Color = containerColor.copy(alpha = 0.38f),
    disabledContentColor: Color = contentColor.copy(alpha = 0.38f),
    content: @Composable RowScope.() -> Unit,
) = ThemeButton(
    onClick = onClick,
    style = ThemeButtonStyle.Custom(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    ),
    modifier = modifier,
    enabled = enabled,
    content = content,
)

@Composable
fun ThemeCustomButton(
    onClick: () -> Unit,
    text: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) = ThemeCustomButton(
    onClick = onClick,
    containerColor = containerColor,
    contentColor = contentColor,
    modifier = modifier,
    enabled = enabled,
) {
    ThemeButtonLabel(text)
}

@Composable
internal fun ThemeButton(
    onClick: () -> Unit,
    style: ThemeButtonStyle,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    groupPosition: ButtonGroupPosition? = null,
    bigRadius: Dp = 24.dp,
    smallRadius: Dp = 12.dp,
    content: @Composable RowScope.() -> Unit,
) {
    val contentColor = resolveContentColor(style)
    val buttonModifier = modifier.then(
        groupPosition?.let { clipGroupShape(it, bigRadius, smallRadius) } ?: Modifier,
    )

    CompositionLocalProvider(LocalButtonContentColor provides contentColor) {
        when (LocalUiMode.current) {
            UiMode.Material -> MaterialThemeButton(
                onClick = onClick,
                style = style,
                modifier = buttonModifier,
                enabled = enabled,
                groupPosition = groupPosition,
                bigRadius = bigRadius,
                smallRadius = smallRadius,
                content = content,
            )

            UiMode.Miuix -> MiuixThemeButton(
                onClick = onClick,
                style = style,
                modifier = buttonModifier,
                enabled = enabled,
                groupPosition = groupPosition,
                content = content,
            )
        }
    }
}

@Composable
@ReadOnlyComposable
private fun resolveContentColor(style: ThemeButtonStyle): Color = when (style) {
    ThemeButtonStyle.Primary -> when (LocalUiMode.current) {
        UiMode.Material -> AnkioTheme.colorScheme.onPrimary
        UiMode.Miuix -> MiuixTheme.colorScheme.onPrimary
    }

    ThemeButtonStyle.Secondary -> when (LocalUiMode.current) {
        UiMode.Material -> AnkioTheme.colorScheme.onSecondaryContainer
        UiMode.Miuix -> MiuixTheme.colorScheme.onSurfaceContainer
    }

    is ThemeButtonStyle.Custom -> style.contentColor
}

@Composable
private fun MaterialThemeButton(
    onClick: () -> Unit,
    style: ThemeButtonStyle,
    modifier: Modifier,
    enabled: Boolean,
    groupPosition: ButtonGroupPosition?,
    bigRadius: Dp,
    smallRadius: Dp,
    content: @Composable RowScope.() -> Unit,
) {
    val shape = groupPosition?.let { groupShape(it, bigRadius, smallRadius) }
        ?: RoundedCornerShape(24.dp)
    val colors = AnkioTheme.colorScheme

    when (style) {
        ThemeButtonStyle.Primary -> Button(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            shape = shape,
            content = content,
        )

        ThemeButtonStyle.Secondary -> FilledTonalButton(
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

        is ThemeButtonStyle.Custom -> Button(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = style.containerColor,
                contentColor = style.contentColor,
                disabledContainerColor = style.disabledContainerColor,
                disabledContentColor = style.disabledContentColor,
            ),
            content = content,
        )
    }
}

@Composable
private fun MiuixThemeButton(
    onClick: () -> Unit,
    style: ThemeButtonStyle,
    modifier: Modifier,
    enabled: Boolean,
    groupPosition: ButtonGroupPosition?,
    content: @Composable RowScope.() -> Unit,
) {
    val scheme = MiuixTheme.colorScheme
    val buttonColors: ButtonColors = when (style) {
        ThemeButtonStyle.Primary -> MiuixButtonDefaults.buttonColorsPrimary()
        ThemeButtonStyle.Secondary -> MiuixButtonDefaults.buttonColors(
            color = scheme.secondaryVariant,
            disabledColor = scheme.disabledSecondaryVariant,
        )
        is ThemeButtonStyle.Custom -> MiuixButtonDefaults.buttonColors(
            color = style.containerColor,
            disabledColor = style.disabledContainerColor,
        )
    }

    MiuixButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        cornerRadius = if (groupPosition != null) 0.dp else MiuixButtonDefaults.CornerRadius,
        colors = buttonColors,
        content = content,
    )
}

private fun clipGroupShape(
    position: ButtonGroupPosition,
    bigRadius: Dp,
    smallRadius: Dp,
): Modifier = Modifier.clip(groupShape(position, bigRadius, smallRadius))

internal fun groupShape(
    position: ButtonGroupPosition,
    bigRadius: Dp,
    smallRadius: Dp,
): RoundedCornerShape = when (position) {
    ButtonGroupPosition.Start -> RoundedCornerShape(
        topStart = bigRadius,
        bottomStart = bigRadius,
        topEnd = smallRadius,
        bottomEnd = smallRadius,
    )

    ButtonGroupPosition.Middle -> RoundedCornerShape(smallRadius)
    ButtonGroupPosition.End -> RoundedCornerShape(
        topStart = smallRadius,
        bottomStart = smallRadius,
        topEnd = bigRadius,
        bottomEnd = bigRadius,
    )

    ButtonGroupPosition.Solo -> RoundedCornerShape(bigRadius)
}
