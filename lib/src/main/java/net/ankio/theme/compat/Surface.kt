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

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.Surface as MiuixSurface

/** Surface：Miuix 用 MiuixSurface，Material 用 Surface */
@Composable
fun ThemeSurface(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    color: Color = AnkioTheme.colorScheme.surface,
    contentColor: Color = AnkioTheme.colorScheme.onSurface,
    border: BorderStroke? = null,
    shadowElevation: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    when (LocalUiMode.current) {
        UiMode.Material -> Surface(
            modifier = modifier,
            shape = shape,
            color = color,
            contentColor = contentColor,
            border = border,
            shadowElevation = shadowElevation,
            content = content
        )

        UiMode.Miuix -> MiuixSurface(
            modifier = modifier,
            shape = shape,
            color = color,
            contentColor = contentColor,
            border = border,
            shadowElevation = shadowElevation,
            content = content
        )
    }
}
