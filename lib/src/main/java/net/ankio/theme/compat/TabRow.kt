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

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.TabRow as MiuixTabRow

/**
 * 标签栏兼容层。
 * Material 使用 PrimaryTabRow + Tab，Miuix 使用 Miuix TabRow。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (LocalUiMode.current) {
        UiMode.Material -> PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    text = { Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                )
            }
        }

        UiMode.Miuix -> MiuixTabRow(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
            modifier = modifier,
        )
    }
}
