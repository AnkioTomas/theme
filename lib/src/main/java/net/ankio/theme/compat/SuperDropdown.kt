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

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import top.yukonga.miuix.kmp.basic.SpinnerEntry

/**
 * 纯文本下拉兼容层，基于 ThemeSuperSpinner 封装。
 */
@Composable
fun ThemeSuperDropdown(
    items: List<String>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    startAction: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    enabled: Boolean = true,
    showValue: Boolean = true,
) {
    val spinnerItems = items.map { titleText ->
        SpinnerEntry(title = titleText)
    }
    ThemeSuperSpinner(
        items = spinnerItems,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = onSelectedIndexChange,
        title = title,
        modifier = modifier,
        summary = summary,
        startAction = startAction,
        shape = shape,
        enabled = enabled,
        showValue = showValue,
    )
}

