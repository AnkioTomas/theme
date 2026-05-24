/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.layout.Arrangement
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
import net.ankio.theme.compat.ThemeFloatingActionButton
import net.ankio.theme.compat.ThemeGroupButton
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemePrimaryButton
import net.ankio.theme.compat.ThemeSecondaryButton
import net.ankio.theme.compat.ThemeText

/** 按钮类组件展示：Primary/Secondary/Icon/FAB/ButtonGroup */
@Composable
internal fun ButtonsSection() {
    SectionCard(title = "按钮 / Buttons") {
        Caption("ThemePrimaryButton / ThemeSecondaryButton")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ThemePrimaryButton(onClick = {}, modifier = Modifier) {
                ThemeText(
                    text = "Primary",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onPrimary,
                )
            }
            ThemeSecondaryButton(onClick = {}) {
                ThemeText(
                    text = "Secondary",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onSecondaryContainer,
                )
            }
            ThemePrimaryButton(onClick = {}, enabled = false) {
                ThemeText(
                    text = "Disabled",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onPrimary,
                )
            }
        }

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

        Caption("ThemeButtonGroup + ThemeGroupButton（连体按钮组）")
        val selected = remember { mutableIntStateOf(0) }
        ThemeButtonGroup(modifier = Modifier.fillMaxWidth()) {
            listOf("低", "中", "高").forEachIndexed { index, label ->
                val pos = when (index) {
                    0 -> ButtonGroupPosition.Start
                    2 -> ButtonGroupPosition.End
                    else -> ButtonGroupPosition.Middle
                }
                ThemeGroupButton(
                    onClick = { selected.intValue = index },
                    modifier = Modifier.weight(1f),
                    position = pos,
                ) {
                    ThemeText(
                        text = label,
                        style = AnkioTheme.textStyles.button,
                        color = AnkioTheme.colorScheme.onSecondaryContainer,
                    )
                }
            }
        }
        Caption("当前选中：${selected.intValue}")
    }
}
