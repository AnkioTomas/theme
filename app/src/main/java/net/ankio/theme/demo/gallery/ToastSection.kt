/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemePrimaryButton
import net.ankio.theme.compat.ThemeSecondaryButton
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.toast.ThemeToast

/**
 * Toast 展示。
 * [ThemeToast] 走 TYPE_APPLICATION_OVERLAY，需要用户授予悬浮窗权限。
 * 未授权时 lib 内部会自动降级为系统 Toast，本节同时演示权限引导 API。
 */
@Composable
internal fun ToastSection() {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        (context.applicationContext as? Application)?.let { ThemeToast.init(it) }
    }

    var hasPermission by remember { mutableStateOf(ThemeToast.hasOverlayPermission(context)) }
    val activity = context as? ComponentActivity
    DisposableEffect(activity) {
        val lifecycle = activity?.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                hasPermission = ThemeToast.hasOverlayPermission(context)
            }
        }
        lifecycle?.addObserver(observer)
        onDispose { lifecycle?.removeObserver(observer) }
    }

    SectionCard(title = "Toast / Overlay") {
        Caption(
            if (hasPermission) "悬浮窗权限：已授权 · 显示主题化 Toast"
            else "悬浮窗权限：未授权 · 当前为系统 Toast 降级",
        )
        if (!hasPermission) {
            ThemePrimaryButton(
                onClick = { ThemeToast.requestOverlayPermission(context) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                ThemeText(
                    text = "授予悬浮窗权限",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onPrimary,
                )
            }
        }

        Caption("ThemeToast.show（5 种 style）")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ThemeSecondaryButton(onClick = {
                ThemeToast.show("信息提示", ThemeToast.Style.Info)
            }) {
                ThemeText(
                    text = "Info",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onSecondaryContainer,
                )
            }
            ThemeSecondaryButton(onClick = {
                ThemeToast.show("操作成功", ThemeToast.Style.Success)
            }) {
                ThemeText(
                    text = "Success",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onSecondaryContainer,
                )
            }
            ThemeSecondaryButton(onClick = {
                ThemeToast.show("注意事项", ThemeToast.Style.Warning)
            }) {
                ThemeText(
                    text = "Warning",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onSecondaryContainer,
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ThemeSecondaryButton(onClick = {
                ThemeToast.show("发生错误", ThemeToast.Style.Error)
            }) {
                ThemeText(
                    text = "Error",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onSecondaryContainer,
                )
            }
            ThemeSecondaryButton(onClick = {
                ThemeToast.show("调试输出", ThemeToast.Style.Debug)
            }) {
                ThemeText(
                    text = "Debug",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onSecondaryContainer,
                )
            }
            ThemeSecondaryButton(onClick = {
                ThemeToast.show(
                    "顶部 Toast",
                    ThemeToast.Style.Info,
                    ThemeToast.Config.fromPosition("top"),
                )
            }) {
                ThemeText(
                    text = "Top",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onSecondaryContainer,
                )
            }
        }
    }
}
