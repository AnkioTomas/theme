/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-3.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package net.ankio.theme.compat

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.HorizontalDivider as MiuixHorizontalDivider
import top.yukonga.miuix.kmp.basic.VerticalDivider as MiuixVerticalDivider

/** 水平分割线：Miuix 用 MiuixHorizontalDivider，Material 用 HorizontalDivider */
@Composable
fun ThemeHorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = androidx.compose.material3.DividerDefaults.Thickness,
    color: Color = AnkioTheme.colorScheme.outlineVariant
) {
    when (LocalUiMode.current) {
        UiMode.Material -> HorizontalDivider(
            modifier = modifier,
            thickness = thickness,
            color = color
        )

        UiMode.Miuix -> MiuixHorizontalDivider(
            modifier = modifier,
            thickness = thickness,
            color = color
        )
    }
}

/** 垂直分割线：Miuix 用 MiuixVerticalDivider，Material 用 VerticalDivider */
@Composable
fun ThemeVerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = androidx.compose.material3.DividerDefaults.Thickness,
    color: Color = AnkioTheme.colorScheme.outlineVariant
) {
    when (LocalUiMode.current) {
        UiMode.Material -> VerticalDivider(
            modifier = modifier,
            thickness = thickness,
            color = color
        )

        UiMode.Miuix -> MiuixVerticalDivider(
            modifier = modifier,
            thickness = thickness,
            color = color
        )
    }
}
