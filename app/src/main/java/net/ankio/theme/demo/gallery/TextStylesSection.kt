/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.demo.ui.Caption
import net.ankio.theme.demo.ui.SectionCard

/**
 * 排版展示：演示 [AnkioTheme.textStyles] 全部 14 种样式。
 * Miuix 一比一透传 Miuix TextStyles；Material 按语义映射 Typography。
 */
@Composable
fun TextStylesSection() {
    SectionCard(title = "排版 / TextStyles") {
        Caption("AnkioTheme.textStyles · 14 种样式")
        val s = AnkioTheme.textStyles
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            ThemeText("title1 · 一级标题", s.title1, AnkioTheme.colorScheme.onSurface)
            ThemeText("title2 · 二级标题", s.title2, AnkioTheme.colorScheme.onSurface)
            ThemeText("title3 · 三级标题", s.title3, AnkioTheme.colorScheme.onSurface)
            ThemeText("title4 · 四级标题", s.title4, AnkioTheme.colorScheme.onSurface)
            ThemeText("headline1 · 大字标题", s.headline1, AnkioTheme.colorScheme.onSurface)
            ThemeText("headline2 · 中字标题", s.headline2, AnkioTheme.colorScheme.onSurface)
            ThemeText("subtitle · 副标题", s.subtitle, AnkioTheme.colorScheme.onSurfaceVariant)
            ThemeText("main · 主文本", s.main, AnkioTheme.colorScheme.onSurface)
            ThemeText("paragraph · 段落", s.paragraph, AnkioTheme.colorScheme.onSurface)
            ThemeText("body1 · 正文", s.body1, AnkioTheme.colorScheme.onSurface)
            ThemeText("body2 · 次正文", s.body2, AnkioTheme.colorScheme.onSurface)
            ThemeText("button · 按钮文本", s.button, AnkioTheme.colorScheme.primary)
            ThemeText("footnote1 · 脚注 1", s.footnote1, AnkioTheme.colorScheme.onSurfaceVariant)
            ThemeText("footnote2 · 脚注 2", s.footnote2, AnkioTheme.colorScheme.onSurfaceVariant)
        }
    }
}
