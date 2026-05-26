/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.util

import android.content.Context
import android.content.ContextWrapper
import androidx.lifecycle.LifecycleOwner
import net.ankio.theme.ThemeSettings

/** 主题化 Context，供 View 膨胀与 BottomSheet 使用。 */
fun Context.themed(): Context = ThemeSettings.themedContext(this)

/**
 * 从 [ContextWrapper] 链上查找 [LifecycleOwner]（Activity / Fragment / LifecycleService）。
 */
fun Context.findLifecycleOwner(): LifecycleOwner {
    var current: Context = this
    while (current is ContextWrapper) {
        if (current is LifecycleOwner) return current
        current = current.baseContext
    }
    error("无法从 Context 找到 LifecycleOwner")
}
