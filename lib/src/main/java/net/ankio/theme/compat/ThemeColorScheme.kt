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

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import net.ankio.theme.AppExtraColors
import top.yukonga.miuix.kmp.theme.Colors as MiuixColors

/**
 * 颜色兼容层：统一 Material ColorScheme 与 Miuix Colors 的访问接口。
 * 业务通过 [AnkioTheme.colorScheme] 获取，无需关心底层是 Material 还是 Miuix。
 *
 * 属性与 Material ColorScheme 对齐，Miuix 缺失项按语义映射：
 * - surfaceContainerLow：Miuix 无，用 surfaceContainer
 * - onSurfaceVariant：Miuix 用 onSurfaceContainerVariant
 */
@Stable
@Immutable
data class ThemeColorScheme(
    /** 语义色：info/warning/error/success，每类含 text 与 bg */
    val semantic: AppExtraColors,
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val surfaceContainer: Color,
    val surfaceContainerLow: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color,
    val outline: Color,
    val outlineVariant: Color,
) {
    companion object {
        /** Material ColorScheme 转 ThemeColorScheme，透传并注入语义色 */
        fun fromMaterial(scheme: ColorScheme, semantic: AppExtraColors): ThemeColorScheme =
            ThemeColorScheme(
                semantic = semantic,
            primary = scheme.primary,
            onPrimary = scheme.onPrimary,
            primaryContainer = scheme.primaryContainer,
            onPrimaryContainer = scheme.onPrimaryContainer,
            secondary = scheme.secondary,
            onSecondary = scheme.onSecondary,
            secondaryContainer = scheme.secondaryContainer,
            onSecondaryContainer = scheme.onSecondaryContainer,
            tertiary = scheme.tertiary,
            onTertiary = scheme.onTertiary,
            tertiaryContainer = scheme.tertiaryContainer,
            onTertiaryContainer = scheme.onTertiaryContainer,
            error = scheme.error,
            onError = scheme.onError,
            errorContainer = scheme.errorContainer,
            onErrorContainer = scheme.onErrorContainer,
            background = scheme.background,
            onBackground = scheme.onBackground,
            surface = scheme.surface,
            onSurface = scheme.onSurface,
            surfaceVariant = scheme.surfaceVariant,
            onSurfaceVariant = scheme.onSurfaceVariant,
                // surfaceContainer* 在部分 Material 版本中不存在，用 lerp 在 surface 与 surfaceVariant 间插值生成明度层级（MD3 做法）
                surfaceContainer = lerp(scheme.surface, scheme.surfaceVariant, 0.5f),
                surfaceContainerLow = lerp(scheme.surface, scheme.surfaceVariant, 0.25f),
                surfaceContainerHigh = lerp(scheme.surface, scheme.surfaceVariant, 0.75f),
            surfaceContainerHighest = scheme.surfaceVariant,
            outline = scheme.outline,
            outlineVariant = scheme.outlineVariant,
        )

        /** Miuix Colors 转 ThemeColorScheme，缺失项按语义映射 */
        fun fromMiuix(c: MiuixColors, semantic: AppExtraColors): ThemeColorScheme =
            ThemeColorScheme(
                semantic = semantic,
            primary = c.primary,
            onPrimary = c.onPrimary,
            primaryContainer = c.primaryContainer,
            onPrimaryContainer = c.onPrimaryContainer,
            secondary = c.secondary,
            onSecondary = c.onSecondary,
            secondaryContainer = c.secondaryContainer,
            onSecondaryContainer = c.onSecondaryContainer,
            tertiary = c.tertiaryContainer, // Miuix 无 tertiary，用 tertiaryContainer 近似
            onTertiary = c.onTertiaryContainer,
            tertiaryContainer = c.tertiaryContainer,
            onTertiaryContainer = c.onTertiaryContainer,
            error = c.error,
            onError = c.onError,
            errorContainer = c.errorContainer,
            onErrorContainer = c.onErrorContainer,
            background = c.background,
            onBackground = c.onBackground,
            surface = c.surface,
            onSurface = c.onSurface,
            surfaceVariant = c.surfaceVariant,
            onSurfaceVariant = c.onSurfaceContainerVariant, // Miuix 无 onSurfaceVariant
            surfaceContainer = c.surfaceContainer,
                surfaceContainerLow = lerp(
                    c.surface,
                    c.surfaceContainer,
                    0.5f
                ), // Miuix 无 surfaceContainerLow，插值生成
            surfaceContainerHigh = c.surfaceContainerHigh,
            surfaceContainerHighest = c.surfaceContainerHighest,
            outline = c.outline,
            outlineVariant = c.outline, // Miuix 无 outlineVariant
        )
    }
}
