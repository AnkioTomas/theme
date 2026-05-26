/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.AutoThemeTokens
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.demo.ui.Caption
import net.ankio.theme.demo.ui.SectionCard

/**
 * 主题令牌展示：
 * - AnkioTheme.colorScheme（通用色）
 * - AutoThemeTokens.extraColors（语义色 info/warning/error/success/debug）
 */
@Composable
fun ThemeTokensSection() {
    SectionCard(title = "主题令牌 / Tokens") {

        Caption("AnkioTheme.colorScheme · 主色与容器色")
        val cs = AnkioTheme.colorScheme
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ColorSwatch(label = "primary", color = cs.primary)
            ColorSwatch(label = "secondary", color = cs.secondary)
            ColorSwatch(label = "tertiary", color = cs.tertiary)
            ColorSwatch(label = "surface", color = cs.surface)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ColorSwatch(label = "primaryC", color = cs.primaryContainer)
            ColorSwatch(label = "secondaryC", color = cs.secondaryContainer)
            ColorSwatch(label = "tertiaryC", color = cs.tertiaryContainer)
            ColorSwatch(label = "errorC", color = cs.errorContainer)
        }

        Caption("AutoThemeTokens.extraColors · 语义色（text + bg）")
        val extra = AutoThemeTokens.extraColors
        SemanticRow("Info", extra.info.text, extra.info.bg)
        SemanticRow("Warning", extra.warning.text, extra.warning.bg)
        SemanticRow("Error", extra.error.text, extra.error.bg)
        SemanticRow("Success", extra.success.text, extra.success.bg)
        SemanticRow("Debug", extra.debug.text, extra.debug.bg)
    }
}

@Composable
private fun ColorSwatch(
    label: String,
    color: Color,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color)
                .border(
                    width = 1.dp,
                    color = AnkioTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(8.dp),
                ),
        )
        ThemeText(
            text = label,
            style = AnkioTheme.textStyles.footnote2,
            color = AnkioTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun SemanticRow(
    label: String,
    text: Color,
    bg: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .padding(12.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ThemeIcon(
                imageVector = Icons.Rounded.Bolt,
                contentDescription = null,
                tint = text,
            )
            ThemeText(
                text = "$label · 这是一段 $label 文案示例",
                style = AnkioTheme.textStyles.body2,
                color = text,
            )
        }
    }
}
