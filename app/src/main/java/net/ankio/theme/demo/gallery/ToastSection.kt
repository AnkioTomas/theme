/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeSecondaryButton
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.toast.ThemeToast

/**
 * Toast 展示。
 * 注：[ThemeToast] 走 TYPE_APPLICATION_OVERLAY，需要悬浮窗权限。
 * 这里只做 API 用例展示，未授权时 lib 内部会回退为系统 Toast。
 */
@Composable
internal fun ToastSection() {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        (context.applicationContext as? Application)?.let { ThemeToast.init(it) }
    }

    SectionCard(title = "Toast / Overlay") {
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
        Caption("注：未授予悬浮窗权限时会回退为系统 Toast。")
    }
}
