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

package net.ankio.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import net.ankio.theme.compat.ThemeColorScheme
import net.ankio.theme.compat.ThemeTextStyles
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * 主题统一入口：颜色与字体。
 * 根据 LocalUiMode 在 Material 与 Miuix 间切换，业务层统一使用本对象。
 */
object AnkioTheme {
    val colorScheme: ThemeColorScheme
        @Composable @ReadOnlyComposable
        get() {
            val semantic = AutoThemeTokens.extraColors
            return when (LocalUiMode.current) {
                UiMode.Material -> ThemeColorScheme.fromMaterial(
                    MaterialTheme.colorScheme,
                    semantic
                )

                UiMode.Miuix -> ThemeColorScheme.fromMiuix(MiuixTheme.colorScheme, semantic)
            }
        }

    val textStyles: ThemeTextStyles
        @Composable @ReadOnlyComposable
        get() = when (LocalUiMode.current) {
            UiMode.Material -> ThemeTextStyles.fromMaterial(MaterialTheme.typography)
            UiMode.Miuix -> ThemeTextStyles.fromMiuix(MiuixTheme.textStyles)
        }


}