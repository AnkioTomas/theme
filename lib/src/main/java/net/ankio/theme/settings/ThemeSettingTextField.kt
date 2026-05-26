/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.compat.TextFieldStyle
import net.ankio.theme.compat.ThemeCard
import net.ankio.theme.compat.ThemeTextField

/**
 * 设置项文本输入：图标与标题在 [ThemeTextField] 内部（leadingIcon + label），
 * 副标题显示在输入框下方（supportingText）。
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
    position: SettingCardPosition = SettingCardPosition.Single,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
) {
    val (topPad, bottomPad) = position.toVerticalPadding()
    ThemeCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = topPad, bottom = bottomPad),
        shape = position.toShape(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            ThemeTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                style = TextFieldStyle.Filled,
                label = title,
                placeholder = placeholder,
                leadingIcon = startAction,
                supportingText = summary?.let { text ->
                    {
                        Text(text)
                    }
                },
                singleLine = singleLine,
                maxLines = maxLines,
            )
        }
    }
}
