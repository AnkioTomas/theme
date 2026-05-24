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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.SmallTitle as MiuixSmallTitle

/**
 * 小标题兼容层，用于分组标题。
 * Material 使用 ThemeText + subtitle 样式，Miuix 使用 Miuix SmallTitle。
 */
@Composable
fun ThemeSmallTitle(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = AnkioTheme.colorScheme.onSurfaceVariant,
    insideMargin: PaddingValues = PaddingValues(28.dp, 8.dp),
) {
    when (LocalUiMode.current) {
        UiMode.Material -> ThemeText(
            text = text,
            style = AnkioTheme.textStyles.subtitle,
            color = textColor,
            modifier = modifier.padding(insideMargin),
        )

        UiMode.Miuix -> MiuixSmallTitle(
            text = text,
            modifier = modifier,
            textColor = textColor,
            insideMargin = insideMargin,
        )
    }
}
