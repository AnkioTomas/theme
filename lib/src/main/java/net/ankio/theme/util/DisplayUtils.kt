/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.util

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.TypedValue
import android.view.WindowManager

object DisplayUtils {

    fun navigationBarHeight(context: Context): Int =
        systemDimen(context, "navigation_bar_height")

    fun statusBarHeight(context: Context): Int =
        systemDimen(context, "status_bar_height")

    fun dpToPx(context: Context, dp: Float): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics,
        ).toInt()

    fun realScreenSize(context: Context): Point {
        val size = Point()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager ?: return size
        val bounds = wm.currentWindowMetrics.bounds
        size.set(bounds.width(), bounds.height())
        return size
    }

    private fun systemDimen(context: Context, name: String): Int {
        val id = context.resources.getIdentifier(name, "dimen", "android")
        return if (id > 0) context.resources.getDimensionPixelSize(id) else 0
    }
}
