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

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.Icon as MiuixIcon

/** 图标：Miuix 用 MiuixIcon，Material 用 Icon。支持 drawable 或 ImageVector。 */
@Composable
fun ThemeIcon(
    @androidx.annotation.DrawableRes resId: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color
) {
    when (LocalUiMode.current) {
        UiMode.Material -> Icon(
            painter = painterResource(resId),
            contentDescription = contentDescription,
            modifier = modifier,
            tint = tint
        )
        UiMode.Miuix -> MiuixIcon(
            painter = painterResource(resId),
            contentDescription = contentDescription,
            modifier = modifier,
            tint = tint
        )
    }
}

/** ImageVector 重载，使用 Compose 图标库（如 Icons.Filled.xxx） */
@Composable
fun ThemeIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color
) {
    when (LocalUiMode.current) {
        UiMode.Material -> Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = modifier,
            tint = tint
        )

        UiMode.Miuix -> MiuixIcon(
            painter = rememberVectorPainter(imageVector),
            contentDescription = contentDescription,
            modifier = modifier,
            tint = tint
        )
    }
}
