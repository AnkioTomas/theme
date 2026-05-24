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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.NumberPicker as MiuixNumberPicker
import top.yukonga.miuix.kmp.basic.NumberPickerDefaults

/**
 * 数字选择器兼容层。
 * Material 无对应组件，两种模式均使用 Miuix NumberPicker。
 */
@Composable
fun ThemeNumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    range: IntRange = 0..10,
    label: (Int) -> String = { it.toString() },
    visibleItemCount: Int = 5,
    wrapAround: Boolean = false,
    textStyle: TextStyle = AnkioTheme.textStyles.title1,
    itemHeight: Dp = NumberPickerDefaults.ItemHeight,
) {
    when (LocalUiMode.current) {
        UiMode.Material, UiMode.Miuix -> MiuixNumberPicker(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            range = range,
            label = label,
            visibleItemCount = visibleItemCount,
            wrapAround = wrapAround,
            colors = NumberPickerDefaults.colors(),
            textStyle = textStyle,
            itemHeight = itemHeight,
        )
    }
}
