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

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import top.yukonga.miuix.kmp.theme.TextStyles as MiuixTextStyles

/**
 * 文本样式兼容层：统一 Material Typography 与 Miuix TextStyles 的访问接口。
 * 业务通过 [themeTextStyles] 获取，无需关心底层是 Material 还是 Miuix。
 *
 * 属性与 Miuix TextStyles 对齐，Material 缺失项按语义映射。
 */
@Stable
@Immutable
data class ThemeTextStyles(
    val main: TextStyle,
    val paragraph: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val button: TextStyle,
    val footnote1: TextStyle,
    val footnote2: TextStyle,
    val headline1: TextStyle,
    val headline2: TextStyle,
    val subtitle: TextStyle,
    val title1: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,
    val title4: TextStyle,
) {
    companion object {
        /** Material Typography 转 ThemeTextStyles，按语义映射 */
        fun fromMaterial(t: Typography): ThemeTextStyles = ThemeTextStyles(
            main = t.bodyLarge,
            paragraph = t.bodyLarge,
            body1 = t.bodyLarge,
            body2 = t.bodyMedium,
            button = t.labelLarge,
            footnote1 = t.bodySmall,
            footnote2 = t.labelSmall,
            headline1 = t.headlineSmall,
            headline2 = t.titleMedium,
            subtitle = t.titleSmall,
            title1 = t.headlineLarge,
            title2 = t.headlineMedium,
            title3 = t.titleMedium,
            title4 = t.titleSmall,
        )

        /** Miuix TextStyles 转 ThemeTextStyles，一比一透传 */
        fun fromMiuix(s: MiuixTextStyles): ThemeTextStyles = ThemeTextStyles(
            main = s.main,
            paragraph = s.paragraph,
            body1 = s.body1,
            body2 = s.body2,
            button = s.button,
            footnote1 = s.footnote1,
            footnote2 = s.footnote2,
            headline1 = s.headline1,
            headline2 = s.headline2,
            subtitle = s.subtitle,
            title1 = s.title1,
            title2 = s.title2,
            title3 = s.title3,
            title4 = s.title4,
        )
    }
}
