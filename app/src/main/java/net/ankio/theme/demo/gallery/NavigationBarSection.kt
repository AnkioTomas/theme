/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import net.ankio.theme.compat.ThemeNavigationBar
import net.ankio.theme.compat.ThemeNavigationBarItem
import net.ankio.theme.demo.ui.ComponentSample
import net.ankio.theme.demo.ui.SectionCard

@Composable
fun NavigationBarSection() {
    SectionCard(title = "ThemeNavigationBar") {
        var selected by remember { mutableIntStateOf(0) }
        val items = listOf(
            "首页" to Icons.Filled.Home,
            "通知" to Icons.Filled.Notifications,
            "设置" to Icons.Filled.Settings,
        )

        ComponentSample(
            name = "底部导航 + Item",
            api = "ThemeNavigationBar { ThemeNavigationBarItem(selected, onClick, icon, label) }",
            description = "全宽贴底，Material NavigationBar / Miuix NavigationBar。每项在 RowScope 内均分宽度。",
        ) {
            ThemeNavigationBar(modifier = Modifier.fillMaxWidth()) {
                items.forEachIndexed { index, (label, icon) ->
                    ThemeNavigationBarItem(
                        selected = selected == index,
                        onClick = { selected = index },
                        icon = icon,
                        label = label,
                    )
                }
            }
        }
    }
}
