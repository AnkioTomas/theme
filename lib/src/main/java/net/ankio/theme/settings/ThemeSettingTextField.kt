/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * 设置项文本输入：图标与标题在输入框内部；[fieldEndAction] 框内、[endAction] 框右侧（均在卡片内）。
 */
@Composable
fun ThemeSettingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    title: String,
    startAction: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    summary: String? = null,
    placeholder: String = "",
    fieldEndAction: @Composable (() -> Unit)? = null,
    endAction: @Composable (() -> Unit)? = null,
    position: SettingCardPosition = SettingCardPosition.Single,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
) {
    SettingFieldCard(position = position, modifier = modifier, endAction = endAction) {
        SettingFilledTextField(
            value = value,
            onValueChange = onValueChange,
            title = title,
            startAction = startAction,
            summary = summary,
            placeholder = placeholder,
            enabled = enabled,
            singleLine = singleLine,
            maxLines = maxLines,
            trailingIcon = fieldEndAction,
        )
    }
}
