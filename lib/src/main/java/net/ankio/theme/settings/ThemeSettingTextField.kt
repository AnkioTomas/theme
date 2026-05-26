/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeCard
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.compat.ThemeTextField

/**
 * 设置项文本输入：标题行 + [ThemeTextField]，支持分组 [SettingCardPosition]。
 */
@Composable
fun ThemeSettingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    title: String,
    startAction: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    summary: String? = null,
    label: String? = null,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.size(40.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    startAction()
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                ) {
                    ThemeText(
                        text = title,
                        style = AnkioTheme.textStyles.title4,
                        color = AnkioTheme.colorScheme.onSurface,
                    )
                    if (summary != null) {
                        Spacer(Modifier.height(2.dp))
                        ThemeText(
                            text = summary,
                            style = AnkioTheme.textStyles.footnote1,
                            color = AnkioTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            ThemeTextField(
                value = value,
                onValueChange = onValueChange,
                label = label ?: title,
                modifier = Modifier.fillMaxWidth(),
                singleLine = singleLine,
                maxLines = maxLines,
            )
        }
    }
}
