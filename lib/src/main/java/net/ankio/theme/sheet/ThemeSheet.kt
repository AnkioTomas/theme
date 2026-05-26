/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.sheet

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.KeyEvent
import android.view.WindowManager
import androidx.core.net.toUri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import net.ankio.theme.AnkioTheme
import net.ankio.theme.AutoTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.ThemeSettings
import net.ankio.theme.UiMode
import net.ankio.theme.compose.OverlayLifecycleOwner
import java.lang.ref.WeakReference

/**
 * 全局底部弹层：统一使用 WindowManager (TYPE_APPLICATION_OVERLAY) 实现。
 * 注意：调用方必须拥有 SYSTEM_ALERT_WINDOW (悬浮窗) 权限。
 */
object ThemeSheet {

    private val mainHandler = Handler(Looper.getMainLooper())
    private var session: SheetSession? = null

    private fun Context.findLifecycleOwner(): LifecycleOwner {
        var current: Context? = this
        while (current != null) {
            if (current is LifecycleOwner) return current
            current = (current as? ContextWrapper)?.baseContext
        }
        error("无法从 Context 找到 LifecycleOwner")
    }

    fun hasOverlayPermission(context: Context): Boolean =
        Settings.canDrawOverlays(context.applicationContext)

    fun requestOverlayPermission(context: Context) {
        val pkg = context.applicationContext.packageName
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            "package:$pkg".toUri(),
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    /**
     * @param context 任意带 [LifecycleOwner] 的 Context（Activity/Service 均可）。
     * @param onShowFailed 无悬浮窗权限或 [WindowManager.addView] 失败时回调（不会调用 [onDismiss]）。
     */
    fun show(
        context: Context,
        shape: ThemeSheetShape = ThemeSheetShape.TopRounded,
        cancelable: Boolean = true,
        onDismiss: () -> Unit = {},
        onShowFailed: () -> Unit = {},
        content: @Composable (dismiss: () -> Unit) -> Unit,
    ) {
        mainHandler.post {
            val host = runCatching { context.findLifecycleOwner() }.getOrNull() ?: return@post
            if (host.lifecycle.currentState == Lifecycle.State.DESTROYED) return@post
            if (!hasOverlayPermission(context)) {
                onShowFailed()
                return@post
            }

            detachCurrentSession()

            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) detachCurrentSession()
            }
            host.lifecycle.addObserver(observer)

            session = showFloatingWindow(
                context = context,
                shape = shape,
                cancelable = cancelable,
                onDismiss = onDismiss,
                onShowFailed = onShowFailed,
                content = content,
                observer = observer,
                host = host,
            )
        }
    }

    fun dismiss() {
        mainHandler.post { session?.requestDismiss() ?: detachCurrentSession() }
    }

    private fun detachCurrentSession() {
        session?.detach()
        session = null
    }

    private fun showFloatingWindow(
        context: Context,
        shape: ThemeSheetShape,
        cancelable: Boolean,
        onDismiss: () -> Unit,
        onShowFailed: () -> Unit,
        content: @Composable (dismiss: () -> Unit) -> Unit,
        observer: LifecycleEventObserver,
        host: LifecycleOwner,
    ): SheetSession {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val overlayOwner = OverlayLifecycleOwner()
        val visibleState = mutableStateOf(false)
        lateinit var sheetSession: SheetSession

        val composeView = ComposeView(context).apply {
            setupOverlayEdgeToEdge()
            overlayOwner.attach(this)
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
            setContent {
                SheetTheme {
                    val visible by visibleState
                    AnimatedSheetOverlay(
                        visible = visible,
                        shape = shape,
                        cancelable = cancelable,
                        onDismissRequest = { sheetSession.requestDismiss() },
                        onExitComplete = {
                            mainHandler.post {
                                if (session === sheetSession) detachCurrentSession()
                            }
                        },
                        content = content,
                    )
                }
            }
        }

        // 设置悬浮窗布局参数
        val windowType =
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY

        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            windowType,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT,
        ).apply {
            applyOverlayEdgeToEdge()
        }

        // 拦截物理返回键
        if (cancelable) {
            composeView.isFocusableInTouchMode = true
            composeView.requestFocus()
            composeView.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                    sheetSession.requestDismiss()
                    true
                } else {
                    false
                }
            }
        }

        sheetSession = SheetSession(
            composeView = composeView,
            windowManager = windowManager,
            overlayOwner = overlayOwner,
            visibleState = visibleState,
            onDetach = {
                host.lifecycle.removeObserver(observer)
                onDismiss()
            },
        )

        // 尝试挂载视图（防备无悬浮窗权限导致 Crash）
        runCatching {
            windowManager.addView(composeView, layoutParams)
        }.onFailure {
            overlayOwner.destroy()
            host.lifecycle.removeObserver(observer)
            onShowFailed()
            return SheetSession()
        }

        composeView.post { visibleState.value = true }

        session = sheetSession
        return sheetSession
    }

    /**
     * 持有当前展示状态的弱引用与相关资源。
     * 提供 [detach] 方法进行统一清理。
     */
    private class SheetSession(
        composeView: ComposeView? = null,
        private val windowManager: WindowManager? = null,
        private val overlayOwner: OverlayLifecycleOwner? = null,
        private val visibleState: MutableState<Boolean>? = null,
        private val onDetach: (() -> Unit)? = null,
    ) {
        private val viewRef = composeView?.let { WeakReference(it) }
        private var detached = false

        fun requestDismiss() {
            if (detached) return
            visibleState?.value = false
        }

        fun detach() {
            if (detached) return
            detached = true
            onDetach?.invoke()
            viewRef?.get()?.let { view ->
                runCatching {
                    if (windowManager != null && view.isAttachedToWindow) {
                        windowManager.removeViewImmediate(view)
                    }
                }
            }
            overlayOwner?.destroy()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeBottomSheet(
    onDismissRequest: () -> Unit,
    shape: ThemeSheetShape = ThemeSheetShape.TopRounded,
    content: @Composable ColumnScope.(dismiss: () -> Unit) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val sheetModifier = when (shape) {
        ThemeSheetShape.TopRounded -> Modifier
        ThemeSheetShape.FullyRounded -> Modifier.fullyRoundedSheetInsets()
    }
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = sheetModifier,
        shape = sheetShape(shape),
        containerColor = AnkioTheme.colorScheme.surfaceContainerHigh,
    ) {
        ThemeSheetBody(
            dismiss = onDismissRequest,
            navigationBarsPadding = shape == ThemeSheetShape.TopRounded,
            content = content,
        )
    }
}

@Composable
private fun ThemeSheetBody(
    dismiss: () -> Unit,
    navigationBarsPadding: Boolean = true,
    content: @Composable ColumnScope.(dismiss: () -> Unit) -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .then(
                if (navigationBarsPadding) Modifier.navigationBarsPadding() else Modifier,
            ),
    ) {
        content(dismiss)
    }
}

/** Compose 主题壳。 */
@Composable
private fun SheetTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalUiMode provides UiMode.fromValue(ThemeSettings.uiMode)) {
        AutoTheme(content = content)
    }
}