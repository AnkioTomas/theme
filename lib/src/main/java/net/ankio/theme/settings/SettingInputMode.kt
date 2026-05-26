/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.settings

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

/**
 * 设置项文本输入模式。
 *
 * - [Text]：普通文本
 * - [Password]：密码遮罩 + 密码键盘
 * - [Number]：数字键盘，仅保留数字字符
 * - [Pattern]：按 [regex] 校验；非空且不匹配时显示错误态，[invalidMessage] 可覆盖 [summary]
 */
sealed class SettingInputMode {

    data object Text : SettingInputMode()

    data object Password : SettingInputMode()

    data object Number : SettingInputMode()

    data class Pattern(
        val regex: Regex,
        val invalidMessage: String? = null,
    ) : SettingInputMode() {
        constructor(pattern: String, invalidMessage: String? = null) : this(Regex(pattern), invalidMessage)
    }
}

internal fun SettingInputMode.filterInput(value: String): String = when (this) {
    SettingInputMode.Number -> value.filter(Char::isDigit)
    else -> value
}

internal fun SettingInputMode.isInputError(value: String): Boolean = when (this) {
    is SettingInputMode.Pattern -> value.isNotEmpty() && !regex.matches(value)
    else -> false
}

internal fun SettingInputMode.toKeyboardOptions(): KeyboardOptions = when (this) {
    SettingInputMode.Password -> KeyboardOptions(keyboardType = KeyboardType.Password)
    SettingInputMode.Number -> KeyboardOptions(keyboardType = KeyboardType.Number)
    else -> KeyboardOptions.Default
}

internal fun SettingInputMode.toVisualTransformation(): VisualTransformation = when (this) {
    SettingInputMode.Password -> PasswordVisualTransformation()
    else -> VisualTransformation.None
}
