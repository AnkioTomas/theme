/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.demo.R

/**
 * 图标展示：
 * - [ThemeIcon] 的两个重载：drawable 资源 id（@DrawableRes）与 [ImageVector]
 * - 多种尺寸（16/24/32/48）+ 多种 tint
 */
@Composable
internal fun IconsSection() {
    SectionCard(title = "图标 / Icons") {

        Caption("ThemeIcon · ImageVector · 多 tint")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ThemeIcon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = AnkioTheme.colorScheme.primary,
            )
            ThemeIcon(
                imageVector = Icons.Filled.Cloud,
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = AnkioTheme.colorScheme.secondary,
            )
            ThemeIcon(
                imageVector = Icons.Filled.Bolt,
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = AnkioTheme.colorScheme.tertiary,
            )
        }

        Caption("ThemeIcon · drawable 资源（@DrawableRes 重载）")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ThemeIcon(
                resId = R.drawable.ic_demo_palette,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = AnkioTheme.colorScheme.primary,
            )
            ThemeIcon(
                resId = R.drawable.ic_demo_logo,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = AnkioTheme.colorScheme.tertiary,
            )
        }

        Caption("多尺寸 · 16 / 24 / 32 / 48 dp")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            listOf(16, 24, 32, 48).forEach { sizeDp ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ThemeIcon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.size(sizeDp.dp),
                        tint = AnkioTheme.colorScheme.primary,
                    )
                    Caption("${sizeDp}dp")
                }
            }
        }
    }
}
