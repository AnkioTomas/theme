/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeCard
import net.ankio.theme.compat.ThemeFloatingToolbar
import net.ankio.theme.compat.ThemeHorizontalDivider
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemeSurface
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.compat.ThemeVerticalDivider

/** 容器类组件展示：Surface/Card（多形态）/Divider/FloatingToolbar */
@Composable
internal fun ContainersSection() {
    SectionCard(title = "容器 / Containers") {

        Caption("ThemeSurface · 默认")
        ThemeSurface(
            modifier = Modifier.fillMaxWidth(),
            color = AnkioTheme.colorScheme.primaryContainer,
            contentColor = AnkioTheme.colorScheme.onPrimaryContainer,
        ) {
            ThemeText(
                text = "Surface · primaryContainer",
                style = AnkioTheme.textStyles.body1,
                color = AnkioTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(16.dp),
            )
        }

        Caption("ThemeSurface · shadowElevation = 6dp + border")
        ThemeSurface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = AnkioTheme.colorScheme.surface,
            contentColor = AnkioTheme.colorScheme.onSurface,
            shadowElevation = 6.dp,
            border = BorderStroke(1.dp, AnkioTheme.colorScheme.outlineVariant),
        ) {
            ThemeText(
                text = "Surface · 阴影 + 描边",
                style = AnkioTheme.textStyles.body1,
                color = AnkioTheme.colorScheme.onSurface,
                modifier = Modifier.padding(16.dp),
            )
        }

        Caption("ThemeCard · onClick = null（静态）")
        ThemeCard(modifier = Modifier.fillMaxWidth()) {
            ThemeText(
                text = "这是一张静态卡片，无点击反馈。",
                style = AnkioTheme.textStyles.body1,
                color = AnkioTheme.colorScheme.onSurface,
                modifier = Modifier.padding(16.dp),
            )
        }

        Caption("ThemeCard · 可点击 + 自定义 shape (28dp 圆角)")
        var clickCount by remember { mutableIntStateOf(0) }
        ThemeCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            onClick = { clickCount++ },
        ) {
            ThemeText(
                text = "点击我（已点击 $clickCount 次）",
                style = AnkioTheme.textStyles.body1,
                color = AnkioTheme.colorScheme.onSurface,
                modifier = Modifier.padding(16.dp),
            )
        }

        Caption("ThemeCard · elevation + border")
        ThemeCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp,
            border = BorderStroke(1.dp, AnkioTheme.colorScheme.primary),
            containerColor = AnkioTheme.colorScheme.surface,
        ) {
            ThemeText(
                text = "Card · elevation 4dp + 主色描边",
                style = AnkioTheme.textStyles.body1,
                color = AnkioTheme.colorScheme.onSurface,
                modifier = Modifier.padding(16.dp),
            )
        }

        Caption("ThemeHorizontalDivider / ThemeVerticalDivider")
        ThemeHorizontalDivider(modifier = Modifier.fillMaxWidth())
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ThemeText(
                text = "左",
                style = AnkioTheme.textStyles.body1,
                color = AnkioTheme.colorScheme.onSurface,
            )
            ThemeVerticalDivider(modifier = Modifier.padding(horizontal = 4.dp))
            ThemeText(
                text = "右",
                style = AnkioTheme.textStyles.body1,
                color = AnkioTheme.colorScheme.onSurface,
            )
        }

        Caption("ThemeFloatingToolbar")
        ThemeFloatingToolbar(
            modifier = Modifier.fillMaxWidth(),
            color = AnkioTheme.colorScheme.surfaceContainer,
            shadowElevation = 4.dp,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ThemeIconButton(onClick = {}) {
                    ThemeIcon(
                        imageVector = Icons.Filled.ChevronLeft,
                        contentDescription = null,
                        tint = AnkioTheme.colorScheme.onSurface,
                    )
                }
                ThemeText(
                    text = "工具栏",
                    style = AnkioTheme.textStyles.body1,
                    color = AnkioTheme.colorScheme.onSurface,
                )
                ThemeIconButton(onClick = {}) {
                    ThemeIcon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = null,
                        tint = AnkioTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}
