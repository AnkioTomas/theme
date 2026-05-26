/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.sheet

import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleService
import net.ankio.theme.AnkioTheme
import net.ankio.theme.AutoTheme
import net.ankio.theme.LocalUiMode
import net.ankio.theme.ThemeSettings
import net.ankio.theme.UiMode
import net.ankio.theme.compose.OverlayLifecycleOwner
import net.ankio.theme.util.findLifecycleOwner
import net.ankio.theme.util.themed

/**
 * 底部弹层：Compose 树内用 [ThemeBottomSheet]，任意 [Context] 用 [show]（纯 Compose Dialog，无 View Binding / XML）。
 */
object ThemeSheet {

    private val mainHandler = Handler(Looper.getMainLooper())
    private var session: SheetSession? = null

    /**
     * 从 Activity / Service 等调起底部弹层。
     *
     * ```
     * ThemeSheet.show(context) { dismiss ->
     *     ThemeText("标题")
     *     ThemePrimaryButton(onClick = dismiss) { ThemeText("关闭") }
     * }
     * ```
     */
    fun show(
        context: Context,
        cancelable: Boolean = true,
        onDismiss: () -> Unit = {},
        content: @Composable (dismiss: () -> Unit) -> Unit,
    ) {
        mainHandler.post {
            val host = runCatching { context.findLifecycleOwner() }.getOrNull() ?: return@post
            if (host.lifecycle.currentState == Lifecycle.State.DESTROYED) return@post

            val themed = context.themed()
            val activity = themed as? Activity
            if (activity != null && (activity.isFinishing || activity.isDestroyed)) return@post

            dismiss()

            val owner = OverlayLifecycleOwner()
            var dismissed = false
            val dismissAction: () -> Unit = {
                if (!dismissed) {
                    dismissed = true
                    dismiss()
                    onDismiss()
                }
            }

            val composeView = ComposeView(themed).apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
                owner.attach(this)
                setContent {
                    SheetTheme {
                        ContextSheetDialog(
                            cancelable = cancelable,
                            onDismissRequest = dismissAction,
                            content = content,
                        )
                    }
                }
            }

            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) dismissAction()
            }
            host.lifecycle.addObserver(observer)

            val newSession = when {
                activity != null -> {
                    val root = activity.window.decorView as ViewGroup
                    root.addView(
                        composeView,
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        ),
                    )
                    SheetSession(composeView, root, null, owner, observer, host)
                }
                host is LifecycleService -> {
                    val wm = themed.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    val params = WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        PixelFormat.TRANSLUCENT,
                    )
                    wm.addView(composeView, params)
                    SheetSession(composeView, null, wm, owner, observer, host)
                }
                else -> {
                    owner.destroy()
                    host.lifecycle.removeObserver(observer)
                    return@post
                }
            }

            session = newSession
        }
    }

    fun dismiss() {
        mainHandler.post {
            val current = session ?: return@post
            session = null
            current.host.lifecycle.removeObserver(current.observer)
            runCatching {
                when {
                    current.parent != null -> current.parent.removeView(current.view)
                    current.windowManager != null && current.view.isAttachedToWindow ->
                        current.windowManager.removeViewImmediate(current.view)
                }
            }
            current.owner.destroy()
        }
    }

    private data class SheetSession(
        val view: ComposeView,
        val parent: ViewGroup?,
        val windowManager: WindowManager?,
        val owner: OverlayLifecycleOwner,
        val observer: LifecycleEventObserver,
        val host: LifecycleOwner,
    )
}

/** 已在 Compose 树内时使用 Material3 [ModalBottomSheet]。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeBottomSheet(
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.(dismiss: () -> Unit) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val shape = RoundedCornerShape(
        topStart = ThemeSheetTopCorner,
        topEnd = ThemeSheetTopCorner,
    )
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = shape,
        containerColor = AnkioTheme.colorScheme.surfaceContainerHigh,
    ) {
        ThemeSheetBody(onDismissRequest, content)
    }
}

/** [ThemeSheet.show]：全屏 Compose [Dialog] + 底部 [SheetContainer]。 */
@Composable
private fun ContextSheetDialog(
    cancelable: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable (dismiss: () -> Unit) -> Unit,
) {
    Dialog(
        onDismissRequest = {
            if (cancelable) onDismissRequest()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false,
            dismissOnBackPress = cancelable,
            dismissOnClickOutside = false,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
        ) {
            if (cancelable) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.32f))
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = onDismissRequest,
                        ),
                )
            }
            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
            ) {
                SheetContainer {
                    content(onDismissRequest)
                }
            }
        }
    }
}

@Composable
private fun ThemeSheetBody(
    dismiss: () -> Unit,
    content: @Composable ColumnScope.(dismiss: () -> Unit) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .navigationBarsPadding(),
    ) {
        content(dismiss)
    }
}

@Composable
private fun SheetTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalUiMode provides UiMode.fromValue(ThemeSettings.uiMode)) {
        AutoTheme(content = content)
    }
}
