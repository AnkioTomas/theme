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
import kotlin.ranges.ClosedFloatingPointRange
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeCard
import net.ankio.theme.compat.ThemeSlider
import net.ankio.theme.compat.ThemeText

/**
 * 设置项滑块：与 [ThemeSettingSwitch] 相同的分组卡片风格。
 *
 * @param title 主标题。
 * @param value 当前滑块值。
 * @param onValueChange 拖动过程中回调。
 * @param startAction 左侧 40dp 图标槽。
 * @param valueLabel 右侧展示当前值（如 `85%`、`0.7`）。
 * @param valueRange 取值范围。
 * @param steps 离散档位数，与 Material [androidx.compose.material3.Slider] 一致。
 * @param onValueChangeFinished 手指抬起时回调（适合延迟持久化）。
 */
@Composable
fun ThemeSettingSlider(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    startAction: @Composable () -> Unit,
    valueLabel: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    position: SettingCardPosition = SettingCardPosition.Single,
    enabled: Boolean = true,
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
                ThemeText(
                    text = valueLabel,
                    style = AnkioTheme.textStyles.body2,
                    color = AnkioTheme.colorScheme.onSurfaceVariant,
                )
            }
            Spacer(Modifier.height(8.dp))
            ThemeSlider(
                value = value,
                onValueChange = onValueChange,
                onValueChangeFinished = onValueChangeFinished,
                valueRange = valueRange,
                steps = steps,
                enabled = enabled,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
