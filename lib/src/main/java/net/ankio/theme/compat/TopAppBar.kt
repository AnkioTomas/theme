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

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.TopAppBar as MiuixTopAppBar

/**
 * 顶部应用栏兼容层。
 * Material 使用 CenterAlignedTopAppBar，Miuix 使用 Miuix TopAppBar（支持大标题折叠）。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    when (LocalUiMode.current) {
        UiMode.Material -> CenterAlignedTopAppBar(
            title = { androidx.compose.material3.Text(title) },
            modifier = modifier,
            navigationIcon = navigationIcon,
            actions = actions,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AnkioTheme.colorScheme.surface,
                titleContentColor = AnkioTheme.colorScheme.onSurface,
                navigationIconContentColor = AnkioTheme.colorScheme.onSurface,
                actionIconContentColor = AnkioTheme.colorScheme.onSurface,
            ),
        )

        UiMode.Miuix -> MiuixTopAppBar(
            title = title,
            modifier = modifier,
            color = AnkioTheme.colorScheme.surface,
            largeTitle = null,
            navigationIcon = navigationIcon,
            actions = actions,
            scrollBehavior = null,
        )
    }
}
