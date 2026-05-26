/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * 设置项点击行：左侧图标 + 标题/副标题，整卡可点（如跳转、外链）。
 */
@Composable
fun ThemeSettingClick(
    title: String,
    onClick: () -> Unit,
    startAction: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    summary: String? = null,
    position: SettingCardPosition = SettingCardPosition.Single,
) {
    SettingCard(
        icon = startAction,
        title = title,
        subtitle = summary,
        onClick = onClick,
        modifier = modifier,
        position = position,
    )
}
