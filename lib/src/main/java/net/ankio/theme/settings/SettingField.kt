/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.TextFieldStyle
import net.ankio.theme.compat.ThemeCard
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.compat.ThemeTextField

/** 分组卡片统一外边距（与 [SettingFieldCard]、[ThemeSettingDropdown] 共用）。 */
internal fun Modifier.settingGroupSpacing(position: SettingCardPosition): Modifier {
    val (top, bottom) = position.toVerticalPadding()
    return padding(top = top, bottom = bottom)
}

/** 输入类设置项：圆角卡片 + 可选框右侧 [endAction]。 */
@Composable
internal fun SettingFieldCard(
    position: SettingCardPosition,
    modifier: Modifier = Modifier,
    endAction: @Composable (() -> Unit)? = null,
    field: @Composable () -> Unit,
) {
    ThemeCard(
        modifier = modifier
            .fillMaxWidth()
            .settingGroupSpacing(position),
        shape = position.toShape(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            if (endAction != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        field()
                    }
                    Spacer(Modifier.width(8.dp))
                    endAction()
                }
            } else {
                field()
            }
        }
    }
}

/** Filled 输入框 + 设置项通用 label / summary / 图标槽。 */
@Composable
internal fun SettingFilledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    title: String,
    startAction: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    summary: String? = null,
    placeholder: String = "",
    inputMode: SettingInputMode = SettingInputMode.Text,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val isPassword = inputMode is SettingInputMode.Password
    var passwordVisible by remember { mutableStateOf(false) }
    val isError = inputMode.isInputError(value)
    val supportingText = when {
        isError && inputMode is SettingInputMode.Pattern && inputMode.invalidMessage != null ->
            settingFieldSummary(inputMode.invalidMessage, isError = true)

        else -> settingFieldSummary(summary, isError = isError)
    }
    val resolvedTrailing: (@Composable () -> Unit)? = when {
        isPassword -> {
            {
                SettingPasswordTrailing(
                    fieldEndAction = trailingIcon,
                    passwordVisible = passwordVisible,
                    onToggleVisibility = { passwordVisible = !passwordVisible },
                    enabled = enabled,
                )
            }
        }

        else -> trailingIcon
    }
    ThemeTextField(
        value = value,
        onValueChange = { onValueChange(inputMode.filterInput(it)) },
        modifier = modifier.fillMaxWidth(),
        style = TextFieldStyle.Filled,
        label = title,
        placeholder = placeholder,
        leadingIcon = startAction,
        trailingIcon = resolvedTrailing,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = inputMode.toVisualTransformation(passwordVisible),
        keyboardOptions = inputMode.toKeyboardOptions(),
        singleLine = singleLine,
        maxLines = maxLines,
        enabled = enabled,
        readOnly = readOnly,
    )
}

/** 密码框 trailing：自定义 [fieldEndAction] + 显隐切换。 */
@Composable
internal fun SettingPasswordTrailing(
    fieldEndAction: @Composable (() -> Unit)?,
    passwordVisible: Boolean,
    onToggleVisibility: () -> Unit,
    enabled: Boolean,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        fieldEndAction?.invoke()
        ThemeIconButton(onClick = onToggleVisibility, enabled = enabled) {
            Icon(
                imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                contentDescription = if (passwordVisible) "隐藏密码" else "显示密码",
                tint = AnkioTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
internal fun SettingFieldInnerTrailing(
    fieldEndAction: @Composable (() -> Unit)?,
    showDropdownToggle: Boolean,
    expanded: Boolean,
    onDropdownToggle: () -> Unit,
    enabled: Boolean,
) {
    if (fieldEndAction == null && !showDropdownToggle) return
    Row(verticalAlignment = Alignment.CenterVertically) {
        fieldEndAction?.invoke()
        if (showDropdownToggle) {
            ThemeIconButton(onClick = onDropdownToggle, enabled = enabled) {
                Icon(
                    imageVector = Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = AnkioTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.rotate(if (expanded) 180f else 0f),
                )
            }
        }
    }
}

@Composable
internal fun SettingItemsPopup(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    items: List<String>,
    onItemSelected: (Int) -> Unit,
    positionProvider: PopupPositionProvider,
) {
    if (!expanded) return
    Popup(
        popupPositionProvider = positionProvider,
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true),
    ) {
        Surface(
            modifier = Modifier.widthIn(max = 320.dp),
            shape = MaterialTheme.shapes.medium,
            color = AnkioTheme.colorScheme.surface,
            tonalElevation = 3.dp,
            shadowElevation = 4.dp,
        ) {
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 4.dp),
            ) {
                items.forEachIndexed { index, label ->
                    DropdownMenuItem(
                        text = {
                            ThemeText(
                                text = label,
                                style = AnkioTheme.textStyles.body1,
                                color = AnkioTheme.colorScheme.onSurface,
                            )
                        },
                        onClick = { onItemSelected(index) },
                    )
                }
            }
        }
    }
}

internal fun settingFieldSummary(
    summary: String?,
    isError: Boolean = false,
): @Composable (() -> Unit)? =
    summary?.let { text ->
        {
            ThemeText(
                text = text,
                style = AnkioTheme.textStyles.footnote1,
                color = if (isError) AnkioTheme.colorScheme.error else AnkioTheme.colorScheme.onSurface,
            )
        }
    }
