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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.ListPopupColumn
import top.yukonga.miuix.kmp.basic.ListPopupDefaults
import top.yukonga.miuix.kmp.basic.PopupPositionProvider
import top.yukonga.miuix.kmp.extra.WindowListPopup as MiuixWindowListPopup

/**
 * 列表弹窗兼容层。
 * Miuix 用 SuperListPopup，Material 用 Popup + Surface。
 *
 * @param show 是否显示
 * @param onDismissRequest 关闭回调
 * @param popupModifier 弹窗修饰符
 * @param popupPositionProvider Miuix 定位提供者，Material 忽略
 * @param alignment Miuix 对齐方式；Material 下用于 Popup 的 alignment
 * @param offset Material 模式下 Popup 的偏移，用于下拉定位（如 IntOffset(0, anchorHeight)）
 * @param maxHeight 最大高度，null 表示不限制
 * @param minWidth 最小宽度
 * @param content 弹窗内容，Miuix 下建议使用 [ThemeListPopupColumn]
 */
@Composable
fun ThemeSuperListPopup(
    show: Boolean,
    onDismissRequest: () -> Unit,
    popupModifier: Modifier = Modifier,
    popupPositionProvider: PopupPositionProvider = ListPopupDefaults.ContextMenuPositionProvider,
    alignment: PopupPositionProvider.Align = PopupPositionProvider.Align.Start,
    offset: IntOffset = IntOffset.Zero,
    maxHeight: androidx.compose.ui.unit.Dp? = null,
    minWidth: androidx.compose.ui.unit.Dp = 200.dp,
    enableContainerScroll: Boolean = true,
    content: @Composable () -> Unit,
) {
    if (!show) return

    when (LocalUiMode.current) {
        UiMode.Material -> Popup(
            alignment = when (alignment) {
                PopupPositionProvider.Align.Start -> Alignment.TopStart
                PopupPositionProvider.Align.End -> Alignment.TopEnd
                PopupPositionProvider.Align.TopStart -> Alignment.TopStart
                PopupPositionProvider.Align.TopEnd -> Alignment.TopEnd
                PopupPositionProvider.Align.BottomStart -> Alignment.BottomStart
                PopupPositionProvider.Align.BottomEnd -> Alignment.BottomEnd
            },
            offset = offset,
            onDismissRequest = onDismissRequest,
        ) {
            Surface(
                modifier = popupModifier
                    .widthIn(min = minWidth)
                    .then(if (maxHeight != null) Modifier.heightIn(max = maxHeight) else Modifier),
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 3.dp,
            ) {
                val contentModifier = Modifier
                    .widthIn(min = minWidth)
                    .then(if (maxHeight != null) Modifier.heightIn(max = maxHeight) else Modifier)
                if (enableContainerScroll) {
                    val scrollState = rememberScrollState()
                    Column(
                        modifier = contentModifier.verticalScroll(scrollState)
                    ) {
                        content()
                    }
                } else {
                    Column(modifier = contentModifier) {
                        content()
                    }
                }
            }
        }

        // 使用 WindowListPopup 而非 SuperListPopup，因引导页等场景无 Scaffold，PopupLayout 需 Scaffold 才能显示
        UiMode.Miuix -> MiuixWindowListPopup(
            show = show,
            popupModifier = popupModifier,
            popupPositionProvider = popupPositionProvider,
            alignment = alignment,
            onDismissRequest = onDismissRequest,
            maxHeight = maxHeight,
            minWidth = minWidth,
            content = content,
        )
    }
}

/**
 * 列表弹窗列兼容层。
 * Miuix 用 ListPopupColumn（自适应宽度），Material 用 Column。
 */
@Composable
fun ThemeListPopupColumn(
    content: @Composable () -> Unit,
) {
    when (LocalUiMode.current) {
        UiMode.Material -> Column(modifier = Modifier) { content() }
        UiMode.Miuix -> ListPopupColumn(content = content)
    }
}

/**
 * 大数据量列表弹窗列（懒加载版）。
 * 与 ThemeSuperListPopup 搭配时，建议将 enableContainerScroll 设为 false。
 */
@Composable
fun <T> ThemeLazyListPopupColumn(
    items: List<T>,
    modifier: Modifier = Modifier,
    key: ((index: Int, item: T) -> Any)? = null,
    itemContent: @Composable (item: T) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        if (key != null) {
            itemsIndexed(items = items, key = { index, item -> key(index, item) }) { _, item ->
                itemContent(item)
            }
        } else {
            itemsIndexed(items = items) { _, item ->
                itemContent(item)
            }
        }
    }
}

/**
 * 列表弹窗项，用于 ThemeListPopupColumn 内。
 * 点击触发 onClick。
 */
@Composable
fun ThemeListPopupItem(
    text: String,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = Color.Transparent,
    ) {
        ThemeText(
            text = text,
            style = AnkioTheme.textStyles.body1,
            color = AnkioTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        )
    }
}
