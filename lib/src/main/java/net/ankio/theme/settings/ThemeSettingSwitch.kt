/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.ankio.theme.compat.ThemeSwitch

/**
 * 设置项开关：与 [UiSettingsScreen] 分组卡片风格一致。
 */
@Composable
fun ThemeSettingSwitch(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    startAction: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    summary: String? = null,
    position: SettingCardPosition = SettingCardPosition.Single,
) {
    SettingCard(
        icon = startAction,
        title = title,
        subtitle = summary,
        onClick = { onCheckedChange(!checked) },
        trailing = {
            ThemeSwitch(
                checked = checked,
                onCheckedChange = onCheckedChange,
            )
        },
        modifier = modifier,
        position = position,
    )
}
