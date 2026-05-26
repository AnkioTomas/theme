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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeSurface

/** BottomSheet 圆角半径（顶圆角 / 全圆角共用）。 */
val ThemeSheetTopCorner = 40.dp

/** 全圆角浮层卡片相对屏幕左右的留白。 */
val ThemeSheetFullyRoundedHorizontalMargin = 16.dp

/** 全圆角浮层卡片距底部的额外留白（设计留白，非系统导航条避让）。 */
val ThemeSheetFullyRoundedBottomMargin = 16.dp

internal fun sheetShape(shape: ThemeSheetShape): Shape = when (shape) {
    ThemeSheetShape.TopRounded -> RoundedCornerShape(
        topStart = ThemeSheetTopCorner,
        topEnd = ThemeSheetTopCorner,
    )
    ThemeSheetShape.FullyRounded -> RoundedCornerShape(ThemeSheetTopCorner)
}

@Composable
internal fun SheetContainer(
    shape: ThemeSheetShape = ThemeSheetShape.TopRounded,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val surfaceShape = sheetShape(shape)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (shape == ThemeSheetShape.FullyRounded) {
                    Modifier
                        .padding(horizontal = ThemeSheetFullyRoundedHorizontalMargin)
                        .padding(bottom = ThemeSheetFullyRoundedBottomMargin)
                } else {
                    Modifier
                },
            ),
    ) {
        ThemeSurface(
            modifier = Modifier.fillMaxWidth(),
            shape = surfaceShape,
            color = AnkioTheme.colorScheme.surfaceContainerHigh,
            contentColor = AnkioTheme.colorScheme.onSurface,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp)
                    .navigationBarsPadding(),
                content = content,
            )
        }
    }
}
