/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.compat

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/** [ThemeButton] / [ThemeGroupButton] 的视觉风格。 */
sealed interface ThemeButtonStyle {

    /** 主按钮：Miuix `buttonColorsPrimary` + `onPrimary` 文案。 */
    data object Primary : ThemeButtonStyle

    /** 次要按钮：Miuix `secondaryVariant` 底 + `onSurfaceContainer` 文案。 */
    data object Secondary : ThemeButtonStyle

    /**
     * 完全自定义容器色与文案色（Primary / Secondary 的默认规则不生效）。
     */
    @Immutable
    data class Custom(
        val containerColor: Color,
        val contentColor: Color,
        val disabledContainerColor: Color = containerColor.copy(alpha = 0.38f),
        val disabledContentColor: Color = contentColor.copy(alpha = 0.38f),
    ) : ThemeButtonStyle
}
