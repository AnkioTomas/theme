/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.sheet

import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/** 悬浮窗全屏铺到状态栏 / 导航条 / 刘海区域（Compose 仍可通过 [imePadding] 响应键盘）。 */
internal fun WindowManager.LayoutParams.applyOverlayEdgeToEdge() {
    width = WindowManager.LayoutParams.MATCH_PARENT
    height = WindowManager.LayoutParams.MATCH_PARENT
    gravity = android.view.Gravity.TOP or android.view.Gravity.START
    flags = flags or
        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
        WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        fitInsetsTypes = 0
    }
}

internal fun View.setupOverlayEdgeToEdge() {
    fitsSystemWindows = false
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, windowInsets ->
        val edgeToEdge = WindowInsetsCompat.Builder(windowInsets)
            .setInsets(WindowInsetsCompat.Type.systemBars(), Insets.NONE)
            .setInsets(WindowInsetsCompat.Type.displayCutout(), Insets.NONE)
            .build()
        ViewCompat.onApplyWindowInsets(view, edgeToEdge)
    }
    ViewCompat.requestApplyInsets(this)
}
