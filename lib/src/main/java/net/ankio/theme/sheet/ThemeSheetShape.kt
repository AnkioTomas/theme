/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.sheet

/** 底部弹层外形：顶圆角贴底，或四角圆角浮层卡片。 */
enum class ThemeSheetShape {
    /** 贴底宽屏，仅顶部 [ThemeSheetTopCorner] 圆角。 */
    TopRounded,
    /** 左右留白、四角均为 [ThemeSheetTopCorner] 的浮层卡片。 */
    FullyRounded,
}
