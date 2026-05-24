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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.PopupPositionProvider
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.SpinnerEntry
import top.yukonga.miuix.kmp.extra.WindowSpinner as MiuixWindowSpinner

/**
 * 把弹窗右上角对齐到 anchor 右下角，向下展开；
 * 下方空间不够时翻转到 anchor 上方。与 Miuix Spinner 弹窗位置保持一致。
 */
private class AnchorEndPopupPositionProvider(
    private val verticalSpacingPx: Int,
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
    ): IntOffset {
        val maxX = (windowSize.width - popupContentSize.width).coerceAtLeast(0)
        val x = (anchorBounds.right - popupContentSize.width).coerceIn(0, maxX)
        val downY = anchorBounds.bottom + verticalSpacingPx
        val y = if (downY + popupContentSize.height <= windowSize.height) {
            downY
        } else {
            (anchorBounds.top - popupContentSize.height - verticalSpacingPx).coerceAtLeast(0)
        }
        return IntOffset(x, y)
    }
}

/**
 * 带图标/副标题的 Spinner 兼容层。
 * Miuix 走 [MiuixWindowSpinner]（窗口级，无需外层 Scaffold）；
 * Material 走 [Popup] + [AnchorEndPopupPositionProvider]，弹窗右上角对齐 Card 右下角，
 * 视觉上与 Miuix 保持一致。
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
@Composable
fun ThemeSuperSpinner(
    items: List<SpinnerEntry>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    startAction: @Composable (() -> Unit)? = null,
    shape: androidx.compose.ui.graphics.Shape = MaterialTheme.shapes.medium,
    enabled: Boolean = true,
    showValue: Boolean = true,
) {
    when (LocalUiMode.current) {
        UiMode.Material -> MaterialSpinner(
            items = items,
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

        UiMode.Miuix -> MiuixWindowSpinner(
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

@Composable
private fun MaterialSpinner(
    items: List<SpinnerEntry>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    title: String,
    modifier: Modifier,
    summary: String?,
    startAction: @Composable (() -> Unit)?,
    shape: androidx.compose.ui.graphics.Shape,
    enabled: Boolean,
    showValue: Boolean,
) {
    var expanded by remember { mutableStateOf(false) }
    val actualEnabled = enabled && items.isNotEmpty()
    val displayText = items.getOrNull(selectedIndex)?.title ?: ""
    val verticalSpacingPx = with(LocalDensity.current) { 4.dp.roundToPx() }
    val positionProvider = remember(verticalSpacingPx) {
        AnchorEndPopupPositionProvider(verticalSpacingPx)
    }

    Box(modifier = modifier.fillMaxWidth()) {
        ThemeCard(
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            onClick = if (actualEnabled) ({ expanded = true }) else null,
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
                        contentAlignment = Alignment.Center,
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

        if (expanded) {
            Popup(
                popupPositionProvider = positionProvider,
                onDismissRequest = { expanded = false },
                properties = PopupProperties(focusable = true),
            ) {
                Surface(
                    modifier = Modifier.widthIn(min = 200.dp, max = 320.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = AnkioTheme.colorScheme.surface,
                    tonalElevation = 3.dp,
                    shadowElevation = 4.dp,
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(vertical = 4.dp),
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
        }
    }
}
