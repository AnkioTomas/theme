/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.compat.ButtonGroupPosition
import net.ankio.theme.compat.ThemeButtonGroup
import net.ankio.theme.compat.ThemeButtonLabel
import net.ankio.theme.compat.ThemeButtonStyle
import net.ankio.theme.compat.ThemeGroupButton
import net.ankio.theme.compat.ThemeTopAppBarTitleAlignment

@Composable
fun DemoTitleAlignmentBar(
    value: ThemeTopAppBarTitleAlignment,
    onValueChange: (ThemeTopAppBarTitleAlignment) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ThemeButtonGroup {
            listOf(
                ThemeTopAppBarTitleAlignment.Start to "居左",
                ThemeTopAppBarTitleAlignment.Center to "居中",
            ).forEachIndexed { index, (align, label) ->
                val pos = when (index) {
                    0 -> ButtonGroupPosition.Start
                    else -> ButtonGroupPosition.End
                }
                ThemeGroupButton(
                    onClick = { onValueChange(align) },
                    position = pos,
                    style = if (value == align) {
                        ThemeButtonStyle.Primary
                    } else {
                        ThemeButtonStyle.Secondary
                    },
                ) {
                    ThemeButtonLabel(label)
                }
            }
        }
    }
}
