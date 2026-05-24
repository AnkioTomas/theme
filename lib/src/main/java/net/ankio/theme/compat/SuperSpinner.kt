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
 *  WITHOUT WARRANTIES OR CONDITIONS FOR A PARTICULAR PURPOSE.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package net.ankio.theme.compat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.SpinnerEntry
import top.yukonga.miuix.kmp.extra.SuperSpinner as MiuixSuperSpinner

/**
 * 带图标/副标题的 Spinner 兼容层。
 * Miuix 用 SuperSpinner，Material 用 ExposedDropdownMenuBox + DropdownMenu。
 *
 * @param items 选项列表，每项含 icon/title/summary
 * @param selectedIndex 当前选中索引
 * @param onSelectedIndexChange 选中变更回调
 * @param title 标题
 * @param modifier 修饰符
 * @param summary 副标题，null 则不显示
 * @param startAction 左侧图标或控件
 * @param shape 卡片形状，用于分组时圆角
 * @param enabled 是否可用
 * @param showValue 是否显示当前选中值
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSuperSpinner(
    items: List<SpinnerEntry>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    startAction: @Composable (() -> Unit)? = null,
    shape: androidx.compose.ui.graphics.Shape = androidx.compose.material3.MaterialTheme.shapes.medium,
    enabled: Boolean = true,
    showValue: Boolean = true,
) {
    when (LocalUiMode.current) {
        UiMode.Material -> {
            var expanded by remember { mutableStateOf(false) }
            val actualEnabled = enabled && items.isNotEmpty()
            val displayText = items.getOrNull(selectedIndex)?.title ?: ""

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = modifier.fillMaxWidth(),
            ) {
                ThemeCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                    shape = shape,
                    onClick = if (actualEnabled) {
                        { expanded = true }
                    } else null,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (startAction != null) {
                            Box(
                                Modifier.size(40.dp),
                                contentAlignment = Alignment.Center
                            ) { startAction() }
                            Spacer(Modifier.size(12.dp))
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            ThemeText(
                                text = title,
                                style = AnkioTheme.textStyles.body1,
                                color = AnkioTheme.colorScheme.onSurface,
                            )
                            if (summary != null) {
                                Spacer(Modifier.height(2.dp))
                                ThemeText(
                                    text = summary,
                                    style = AnkioTheme.textStyles.footnote1,
                                    color = AnkioTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (showValue && items.isNotEmpty()) {
                                ThemeText(
                                    text = displayText,
                                    style = AnkioTheme.textStyles.body2,
                                    color = AnkioTheme.colorScheme.onSurfaceVariant,
                                )
                                Spacer(Modifier.size(8.dp))
                            }
                            Icon(
                                imageVector = Icons.Filled.ExpandMore,
                                contentDescription = null,
                                tint = AnkioTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
                androidx.compose.material3.DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    items.forEachIndexed { index, entry ->
                        val entrySummary = entry.summary
                        DropdownMenuItem(
                            leadingIcon = entry.icon?.let { icon ->
                                { icon(Modifier.size(24.dp)) }
                            },
                            text = {
                                Column {
                                    ThemeText(
                                        text = entry.title ?: "",
                                        style = AnkioTheme.textStyles.body1,
                                        color = AnkioTheme.colorScheme.onSurface,
                                    )
                                    if (entrySummary != null) {
                                        Spacer(Modifier.height(2.dp))
                                        ThemeText(
                                            text = entrySummary,
                                            style = AnkioTheme.textStyles.footnote1,
                                            color = AnkioTheme.colorScheme.onSurfaceVariant,
                                        )
                                    }
                                }
                            },
                            onClick = {
                                onSelectedIndexChange(index)
                                expanded = false
                            },
                        )
                    }
                }
            }
        }

        UiMode.Miuix -> MiuixSuperSpinner(
            items = items,
            selectedIndex = selectedIndex,
            title = title,
            modifier = modifier,
            summary = summary,
            startAction = startAction,
            enabled = enabled,
            showValue = showValue,
            onSelectedIndexChange = onSelectedIndexChange,
        )
    }
}
