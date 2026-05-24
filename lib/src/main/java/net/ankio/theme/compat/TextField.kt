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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.TextField as MiuixTextField

/**
 * TextField 视觉风格：Material 的两种变体。
 * - [Outlined]：轮廓式，带边框，适合表单多字段场景
 * - [Filled]：填充式，带背景与底边指示线，视觉更突出
 */
enum class TextFieldStyle {
    /** 轮廓式，Material OutlinedTextField */
    Outlined,

    /** 填充式，Material TextField（Filled） */
    Filled,
}

/**
 * 文本输入框兼容层。
 * 统一 value + onValueChange API，Material 与 Miuix 均支持完整参数。
 *
 * Material：根据 [style] 选择 OutlinedTextField 或 TextField(Filled)。
 * Miuix：使用 value/onValueChange 重载，支持自定义 backgroundColor、borderColor、cornerRadius 等。
 *
 * @param value 当前文本
 * @param onValueChange 文本变更回调
 * @param modifier 修饰符
 * @param style 风格：Outlined 或 Filled。Miuix 下 Outlined 用 surface 背景，Filled 用 secondaryContainer
 * @param label 标签，空时隐藏
 * @param placeholder 占位符，有内容且 value 为空时显示
 * @param supportingText 辅助说明，显示于输入框下方（错误提示等）
 * @param isError 是否错误状态，影响边框/指示线颜色
 * @param enabled 是否可编辑
 * @param readOnly 是否只读
 * @param leadingIcon 前导图标
 * @param trailingIcon 后导图标。Miuix 模式下建议在 IconButton 上使用 Modifier.padding(end = 12.dp) 以避免贴边
 * @param visualTransformation 视觉变换（如密码遮罩）
 * @param keyboardOptions 键盘选项
 * @param keyboardActions 键盘动作
 * @param singleLine 是否单行
 * @param maxLines 最大行数
 * @param minLines 最小行数
 * @param textStyle 文本样式
 */
@Composable
fun ThemeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    style: TextFieldStyle = TextFieldStyle.Outlined,
    label: String = "",
    placeholder: String = "",
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    textStyle: TextStyle = AnkioTheme.textStyles.main,
) {
    val colors = AnkioTheme.colorScheme
    val labelComposable: (@Composable () -> Unit)? = remember(label) {
        if (label.isNotEmpty()) ({ Text(label) }) else null
    }
    val placeholderComposable: (@Composable () -> Unit)? = remember(placeholder) {
        if (placeholder.isNotEmpty()) ({ Text(placeholder) }) else null
    }

    when (LocalUiMode.current) {
        UiMode.Material -> {
            val materialColors = when (style) {
                TextFieldStyle.Outlined -> OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (isError) colors.error else colors.primary,
                    unfocusedBorderColor = if (isError) colors.error else colors.outline,
                    errorBorderColor = colors.error,
                    focusedLabelColor = if (isError) colors.error else colors.primary,
                    unfocusedLabelColor = colors.onSurfaceVariant,
                    errorLabelColor = colors.error,
                    cursorColor = colors.primary,
                    errorCursorColor = colors.error,
                    focusedTextColor = colors.onSurface,
                    unfocusedTextColor = colors.onSurface,
                    errorSupportingTextColor = colors.error,
                    unfocusedSupportingTextColor = colors.onSurfaceVariant,
                    focusedSupportingTextColor = colors.onSurfaceVariant,
                )

                TextFieldStyle.Filled -> TextFieldDefaults.colors(
                    focusedIndicatorColor = if (isError) colors.error else colors.primary,
                    unfocusedIndicatorColor = if (isError) colors.error else colors.outline,
                    errorIndicatorColor = colors.error,
                    focusedContainerColor = colors.surfaceContainerHighest,
                    unfocusedContainerColor = colors.surfaceContainerHighest,
                    errorContainerColor = colors.surfaceContainerHighest,
                    focusedLabelColor = if (isError) colors.error else colors.primary,
                    unfocusedLabelColor = colors.onSurfaceVariant,
                    errorLabelColor = colors.error,
                    cursorColor = colors.primary,
                    errorCursorColor = colors.error,
                    focusedTextColor = colors.onSurface,
                    unfocusedTextColor = colors.onSurface,
                    errorSupportingTextColor = colors.error,
                    unfocusedSupportingTextColor = colors.onSurfaceVariant,
                    focusedSupportingTextColor = colors.onSurfaceVariant,
                )
            }

            when (style) {
                TextFieldStyle.Outlined -> OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = modifier,
                    label = labelComposable,
                    placeholder = placeholderComposable,
                    supportingText = supportingText,
                    isError = isError,
                    enabled = enabled,
                    readOnly = readOnly,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    visualTransformation = visualTransformation,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    singleLine = singleLine,
                    maxLines = maxLines,
                    minLines = minLines,
                    textStyle = textStyle,
                    colors = materialColors,
                )

                TextFieldStyle.Filled -> TextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = modifier,
                    label = labelComposable,
                    placeholder = placeholderComposable,
                    supportingText = supportingText,
                    isError = isError,
                    enabled = enabled,
                    readOnly = readOnly,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    visualTransformation = visualTransformation,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    singleLine = singleLine,
                    maxLines = maxLines,
                    minLines = minLines,
                    textStyle = textStyle,
                    colors = materialColors,
                )
            }
        }

        UiMode.Miuix -> {
            val borderColor = if (isError) colors.error else colors.primary
            val backgroundColor = colors.secondaryContainer
            val labelColor = colors.onSecondaryContainer
            // Miuix 无独立 placeholder，有 placeholder 无 label 时用 placeholder 作 label 并作为占位符
            val miuixLabel = when {
                label.isNotEmpty() -> label
                placeholder.isNotEmpty() -> placeholder
                else -> ""
            }
            val useLabelAsPlaceholder = label.isEmpty() && placeholder.isNotEmpty()
            // 右侧图标：边距 + 统一 tint（onSurfaceVariant）
            val wrappedTrailingIcon = remember(trailingIcon, colors.onSurfaceVariant) {
                trailingIcon?.let { icon ->
                    @Composable {
                        Box(modifier = Modifier.padding(end = 12.dp)) {
                            CompositionLocalProvider(LocalContentColor provides colors.onSurfaceVariant) {
                                icon()
                            }
                        }
                    }
                }
            }
            val miuixTextStyle = remember(textStyle, colors.onSurface) {
                textStyle.copy(color = colors.onSurface)
            }
            val cursorBrush = remember(borderColor) { SolidColor(borderColor) }

            Column(modifier = modifier) {
                MiuixTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier,
                    label = miuixLabel,
                    backgroundColor = backgroundColor,
                    labelColor = if (isError) colors.error else labelColor,
                    borderColor = borderColor,
                    useLabelAsPlaceholder = useLabelAsPlaceholder,
                    enabled = enabled,
                    readOnly = readOnly,
                    leadingIcon = leadingIcon,
                    trailingIcon = wrappedTrailingIcon,
                    textStyle = miuixTextStyle,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    singleLine = singleLine,
                    maxLines = maxLines,
                    minLines = minLines,
                    visualTransformation = visualTransformation,
                    cursorBrush = cursorBrush,
                )
                if (supportingText != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    supportingText()
                }
            }
        }
    }
}
