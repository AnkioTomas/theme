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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ButtonGroupPosition
import net.ankio.theme.compat.ThemeButtonGroup
import net.ankio.theme.compat.ThemeButtonStyle
import net.ankio.theme.compat.ThemeCustomButton
import net.ankio.theme.compat.ThemeFloatingActionButton
import net.ankio.theme.compat.ThemeGroupButton
import net.ankio.theme.compat.ThemeGroupCustomButton
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemePrimaryButton
import net.ankio.theme.compat.ThemeSecondaryButton
import net.ankio.theme.demo.ui.Caption
import net.ankio.theme.demo.ui.SectionCard

/** 按钮类组件展示：Primary/Secondary/Custom/Icon/FAB/ButtonGroup */
@Composable
fun ButtonsSection() {
    SectionCard(title = "按钮 / Buttons") {
        Caption("ThemePrimaryButton / ThemeSecondaryButton（文案色组件内默认）")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ThemePrimaryButton(onClick = {}, text = "Primary")
            ThemeSecondaryButton(onClick = {}, text = "Secondary")
            ThemePrimaryButton(onClick = {}, text = "Disabled", enabled = false)
        }

        Caption("ThemeCustomButton")
        ThemeCustomButton(
            onClick = {},
            containerColor = AnkioTheme.colorScheme.tertiaryContainer,
            contentColor = AnkioTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.fillMaxWidth(),
            text = "Custom 完全自定义",
        )

        Caption("ThemeIconButton")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ThemeIconButton(onClick = {}) {
                ThemeIcon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = AnkioTheme.colorScheme.primary,
                )
            }
            ThemeIconButton(onClick = {}) {
                ThemeIcon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = AnkioTheme.colorScheme.secondary,
                )
            }
        }

        Caption("ThemeFloatingActionButton")
        ThemeFloatingActionButton(onClick = {}, modifier = Modifier.size(56.dp)) {
            ThemeIcon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = AnkioTheme.colorScheme.onPrimaryContainer,
            )
        }

        Caption("ThemeButtonGroup：Secondary / Primary / Custom")
        val selected = remember { mutableIntStateOf(0) }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeButtonGroup(modifier = Modifier.fillMaxWidth()) {
                ThemeGroupButton(
                    onClick = { selected.intValue = 0 },
                    text = "低",
                    modifier = Modifier.weight(1f),
                    position = ButtonGroupPosition.Start,
                    style = ThemeButtonStyle.Secondary,
                )
                ThemeGroupButton(
                    onClick = { selected.intValue = 1 },
                    text = "中",
                    modifier = Modifier.weight(1f),
                    position = ButtonGroupPosition.Middle,
                    style = ThemeButtonStyle.Primary,
                )
                ThemeGroupCustomButton(
                    onClick = { selected.intValue = 2 },
                    modifier = Modifier.weight(1f),
                    position = ButtonGroupPosition.End,
                    containerColor = AnkioTheme.colorScheme.errorContainer,
                    contentColor = AnkioTheme.colorScheme.onErrorContainer,
                ) {
                    net.ankio.theme.compat.ThemeButtonLabel("高")
                }
            }
            Caption("当前选中：${selected.intValue}")
        }
    }
}
