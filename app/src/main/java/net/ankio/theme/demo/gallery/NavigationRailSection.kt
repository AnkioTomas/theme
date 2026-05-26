/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemeNavigationRail
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.demo.ui.ComponentSample
import net.ankio.theme.demo.ui.SectionCard

@Composable
fun NavigationRailSection() {
    SectionCard(title = "ThemeNavigationRail") {
        var railIndex by remember { mutableIntStateOf(0) }
        val icons = listOf(Icons.Filled.Home, Icons.Filled.Person, Icons.Filled.Settings)

        ComponentSample(
            name = "侧边导航",
            api = "ThemeNavigationRail(header) { /* icon buttons */ }",
            description = "适用于平板 / 横屏。放在 Row 左侧，右侧为主内容区。",
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(AnkioTheme.colorScheme.surfaceContainer),
            ) {
                Row(modifier = Modifier.fillMaxSize()) {
                    ThemeNavigationRail(
                        modifier = Modifier.fillMaxHeight(),
                        header = {
                            ThemeIconButton(onClick = {}) {
                                ThemeIcon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = null,
                                    tint = AnkioTheme.colorScheme.primary,
                                )
                            }
                        },
                    ) {
                        icons.forEachIndexed { index, icon ->
                            ThemeIconButton(onClick = { railIndex = index }) {
                                ThemeIcon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = if (railIndex == index) {
                                        AnkioTheme.colorScheme.primary
                                    } else {
                                        AnkioTheme.colorScheme.onSurfaceVariant
                                    },
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        ThemeText(
                            text = "内容区 · 第 ${railIndex + 1} 项",
                            style = AnkioTheme.textStyles.body1,
                            color = AnkioTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }
    }
}
