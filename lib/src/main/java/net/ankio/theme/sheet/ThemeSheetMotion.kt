/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.sheet

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.spring

/**
 * [ThemeSheet.show] 进出场曲线，对齐 Material3 `ModalBottomSheet` 所用 Standard Motion：
 * - 展开：`DefaultSpatial`（damping 0.9, stiffness 700）
 * - 收起：`FastEffects`（damping 1.0, stiffness 3800）
 * - 遮罩：`DefaultEffects` / `FastEffects`
 */
object ThemeSheetMotion {
    const val ScrimAlpha = 0.32f

    /** 内容区上滑展开（同 M3 showMotion / DefaultSpatial）。 */
    val sheetShowSpec: FiniteAnimationSpec<Float> = spring(
        dampingRatio = 0.9f,
        stiffness = 700f,
    )

    /** 内容区下滑收起（同 M3 hideMotion / FastEffects）。 */
    val sheetHideSpec: FiniteAnimationSpec<Float> = spring(
        dampingRatio = 1f,
        stiffness = 3800f,
    )

    /** 遮罩淡入（同 M3 Scrim / DefaultEffects）。 */
    val scrimShowSpec: FiniteAnimationSpec<Float> = spring(
        dampingRatio = 1f,
        stiffness = 1600f,
    )

    /** 遮罩淡出（同 M3 收起 / FastEffects）。 */
    val scrimHideSpec: FiniteAnimationSpec<Float> = spring(
        dampingRatio = 1f,
        stiffness = 3800f,
    )
}
