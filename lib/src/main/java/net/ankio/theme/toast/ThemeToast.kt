/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-3.0
 */

package net.ankio.theme.toast

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import java.lang.ref.WeakReference
import net.ankio.theme.appExtraColors
import net.ankio.theme.colorSchemeFromSeed

object ThemeToast {

    enum class Position { Top, Center, Bottom }

    enum class Style { Debug, Error, Success, Warning, Info }

    data class Config(
        val position: Position = Position.Bottom,
        val offsetX: Int = 0,
        val offsetY: Int = 0,
    ) {
        companion object {
            fun fromPosition(position: String) = Config(
                position = when (position.lowercase()) {
                    "top" -> Position.Top
                    "center" -> Position.Center
                    else -> Position.Bottom
                }
            )
        }
    }

    private const val DURATION_MS = 3000L
    private const val EDGE_MARGIN_DP = 80
    private const val DEFAULT_SEED = 0xFF6750A4.toInt()

    private var appContext: Context? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    private var overlayRef: WeakReference<View>? = null
    private var ownerRef: WeakReference<ComposeOverlayOwner>? = null
    private var wmRef: WeakReference<WindowManager>? = null

    private val dismissRunnable = Runnable { dismissOverlay() }

    fun init(application: Application) {
        appContext = application.applicationContext
    }

    /**
     * 是否已授予悬浮窗权限（[Settings.canDrawOverlays]）。
     * 没授权时 [show] 会降级为系统 [Toast]。
     * 不传 [context] 时使用 [init] 注入的 application context；
     * 若 [init] 也未调用，返回 false。
     */
    fun hasOverlayPermission(context: Context? = appContext): Boolean {
        val ctx = context ?: return false
        return Settings.canDrawOverlays(ctx)
    }

    /**
     * 跳转到系统设置页让用户授予本应用悬浮窗权限。
     * 调用方需自己处理回到 app 后的状态刷新（通常下次 onResume 重新检查 [hasOverlayPermission]）。
     */
    fun requestOverlayPermission(context: Context) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:${context.packageName}"),
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun show(
        message: String,
        style: Style = Style.Info,
        config: Config = Config(),
        trailingContent: (@Composable () -> Unit)? = null,
    ) {
        val ctx = appContext ?: return
        mainHandler.post {
            dismissOverlay()
            if (Settings.canDrawOverlays(ctx)) {
                tryShowOverlay(ctx, message, style, config, trailingContent)
            } else {
                Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun tryShowOverlay(
        ctx: Context,
        message: String,
        style: Style,
        config: Config,
        trailingContent: (@Composable () -> Unit)?,
    ) {
        val wm = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val darkTheme = (ctx.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                Configuration.UI_MODE_NIGHT_YES

        val owner = ComposeOverlayOwner()
        val composeView = ComposeView(ctx).apply {
            owner.attach(this)
            setContent {
                MaterialTheme(colorScheme = colorSchemeFromSeed(DEFAULT_SEED, darkTheme)) {
                    OverlayToastContent(message, style, darkTheme, trailingContent)
                }
            }
        }

        val params = buildLayoutParams(ctx, config)
        try {
            wm.addView(composeView, params)
            overlayRef = WeakReference(composeView)
            ownerRef = WeakReference(owner)
            wmRef = WeakReference(wm)
            mainHandler.postDelayed(dismissRunnable, DURATION_MS)
        } catch (e: Exception) {
            owner.detach()
            Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun buildLayoutParams(ctx: Context, config: Config): WindowManager.LayoutParams =
        WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = when (config.position) {
                Position.Top -> Gravity.TOP or Gravity.CENTER_HORIZONTAL
                Position.Center -> Gravity.CENTER
                Position.Bottom -> Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            }
            x = config.offsetX
            val edgeMarginPx = (EDGE_MARGIN_DP * ctx.resources.displayMetrics.density).toInt()
            y = when (config.position) {
                Position.Top -> edgeMarginPx + config.offsetY
                Position.Center -> config.offsetY
                Position.Bottom -> edgeMarginPx - config.offsetY
            }
        }

    private fun dismissOverlay() {
        mainHandler.removeCallbacks(dismissRunnable)
        val view = overlayRef?.get()
        val wm = wmRef?.get()
        val owner = ownerRef?.get()

        overlayRef = null
        wmRef = null
        ownerRef = null

        if (view != null && wm != null) {
            runCatching {
                if (view.isAttachedToWindow) wm.removeViewImmediate(view)
            }
        }
        owner?.detach()
    }
}

/** Toast 视觉描述：图标 + 背景色 + 文字色，对应一个 [ThemeToast.Style]。 */
@Immutable
private data class ToastVisuals(
    val icon: ImageVector,
    val background: Color,
    val foreground: Color,
)

@Composable
private fun OverlayToastContent(
    message: String,
    style: ThemeToast.Style,
    darkTheme: Boolean,
    trailingContent: (@Composable () -> Unit)?,
) {
    val extra = appExtraColors(darkTheme)
    val visuals = when (style) {
        ThemeToast.Style.Debug -> ToastVisuals(Icons.Rounded.Notifications, extra.debug.text, Color.White)
        ThemeToast.Style.Error -> ToastVisuals(Icons.Rounded.Warning, extra.error.text, Color.White)
        ThemeToast.Style.Success -> ToastVisuals(Icons.Rounded.Check, extra.success.text, Color.White)
        ThemeToast.Style.Warning -> ToastVisuals(Icons.Rounded.Warning, extra.warning.text, Color.White)
        ThemeToast.Style.Info -> ToastVisuals(Icons.Rounded.Info, extra.info.text, Color.White)
    }

    Surface(
        shape = RoundedCornerShape(26.dp),
        color = visuals.background,
        tonalElevation = 2.dp,
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .widthIn(max = 520.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(visuals.icon, null, Modifier.size(24.dp), tint = visuals.foreground)
            Text(
                message,
                color = visuals.foreground,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f, fill = false),
            )
            trailingContent?.invoke()
        }
    }
}
