/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalColorMode
import net.ankio.theme.LocalUiMode
import net.ankio.theme.ThemeSettings
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.isInDarkTheme

/**
 * 主题状态展示。
 * 演示 lib 顶层 API：[LocalUiMode]、[LocalColorMode]、[isInDarkTheme]、[ThemeSettings]。
 * 该 section 数据全部来自 CompositionLocal/单例，切换设置后立刻同步刷新。
 */
@Composable
internal fun StatusSection() {
    SectionCard(title = "状态 / Status") {
        Caption("CompositionLocals + ThemeSettings 实时数据")
        StatusRow("LocalUiMode", LocalUiMode.current.name)
        StatusRow("LocalColorMode", LocalColorMode.current.name)
        StatusRow("isInDarkTheme()", isInDarkTheme().toString())
        StatusRow("ThemeSettings.isDark", ThemeSettings.isDark.toString())
        StatusRow("ThemeSettings.themeColor", ThemeSettings.themeColor)
        StatusRow(
            "ThemeSettings.keyColor",
            "0x${"%08X".format(ThemeSettings.keyColor)}",
        )
        StatusRow(
            "ThemeSettings.followSystemAccent",
            ThemeSettings.followSystemAccent.toString(),
        )
        StatusRow(
            "ThemeSettings.displayPercentage",
            "${ThemeSettings.displayPercentage}%",
        )

        Caption("当前 keyColor 直观预览")
        Row(verticalAlignment = Alignment.CenterVertically) {
            val previewColor = if (ThemeSettings.keyColor == 0) {
                AnkioTheme.colorScheme.primary
            } else {
                Color(ThemeSettings.keyColor)
            }
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(previewColor),
            )
            ThemeText(
                text = if (ThemeSettings.keyColor == 0) " · 跟随系统动态色" else " · 种子色",
                style = AnkioTheme.textStyles.body2,
                color = AnkioTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
    }
}

@Composable
private fun StatusRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ThemeText(
            text = label,
            style = AnkioTheme.textStyles.body2,
            color = AnkioTheme.colorScheme.onSurfaceVariant,
        )
        ThemeText(
            text = value,
            style = AnkioTheme.textStyles.body2,
            color = AnkioTheme.colorScheme.onSurface,
        )
    }
}
