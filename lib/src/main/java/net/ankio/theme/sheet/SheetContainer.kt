/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeSurface

/** BottomSheet 顶部大圆角（与 [SheetContainer] 一致）。 */
val ThemeSheetTopCorner = 32.dp

@Composable
internal fun SheetContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(
        topStart = ThemeSheetTopCorner,
        topEnd = ThemeSheetTopCorner,
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
    ) {
        ThemeSurface(
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            color = AnkioTheme.colorScheme.surfaceContainerHigh,
            contentColor = AnkioTheme.colorScheme.onSurface,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                content = content,
            )
        }
    }
}
