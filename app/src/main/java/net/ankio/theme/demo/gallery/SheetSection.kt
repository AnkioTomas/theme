/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import net.ankio.theme.demo.ui.Caption
import net.ankio.theme.demo.ui.ComponentSample
import net.ankio.theme.demo.ui.SectionCard
import net.ankio.theme.sheet.ThemeBottomSheet
import net.ankio.theme.sheet.ThemeSheet
import net.ankio.theme.sheet.ThemeSheetShape
import net.ankio.theme.toast.ThemeToast

/**
 * 底部弹层三种风格：Overlay 顶圆角、Overlay 全圆角、Modal 顶圆角。
 */
@Composable
fun SheetSection() {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        (context.applicationContext as? Application)?.let { ThemeToast.init(it) }
    }
    var showModalSheet by remember { mutableStateOf(false) }
    var hasPermission by remember { mutableStateOf(ThemeSheet.hasOverlayPermission(context)) }

    val activity = context as? ComponentActivity
    DisposableEffect(activity) {
        val lifecycle = activity?.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                hasPermission = ThemeSheet.hasOverlayPermission(context)
            }
        }
        lifecycle?.addObserver(observer)
        onDispose { lifecycle?.removeObserver(observer) }
    }

    if (showModalSheet) {
        ThemeBottomSheet(
            onDismissRequest = { showModalSheet = false },
            shape = ThemeSheetShape.TopRounded,
        ) { dismiss ->
            SheetDemoContent(style = SheetDemoStyle.ModalTopRounded, dismiss = dismiss)
        }
    }

    SectionCard(title = "BottomSheet / 底部弹层") {
        Caption("三种风格：Overlay 顶圆角 · Overlay 全圆角浮卡 · Modal 顶圆角")

        Caption(
            if (hasPermission) "Overlay 依赖悬浮窗：已授权"
            else "Overlay 依赖悬浮窗：未授权（可先授权或仅试 Modal）",
        )
        if (!hasPermission) {
            ThemeSecondaryButton(
                onClick = { ThemeSheet.requestOverlayPermission(context) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                ThemeText(
                    text = "授予悬浮窗权限（Overlay 风格需要）",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onSecondaryContainer,
                )
            }
        }

        ComponentSample(
            name = "风格一 · Overlay 顶圆角",
            api = "ThemeSheet.show(shape = TopRounded)",
            description = "贴底宽屏，仅顶部 48dp 圆角；遮罩淡入 + 内容上滑。",
        ) {
            ThemePrimaryButton(
                onClick = {
                    showOverlaySheet(context, ThemeSheetShape.TopRounded, SheetDemoStyle.OverlayTopRounded)
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                ThemeText(
                    text = "打开 Overlay · 顶圆角",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onPrimary,
                )
            }
        }

        ComponentSample(
            name = "风格二 · Overlay 全圆角",
            api = "ThemeSheet.show(shape = FullyRounded)",
            description = "四角 48dp 圆角，左右 16dp 留白浮于遮罩上。",
        ) {
            ThemePrimaryButton(
                onClick = {
                    showOverlaySheet(context, ThemeSheetShape.FullyRounded, SheetDemoStyle.OverlayFullyRounded)
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                ThemeText(
                    text = "打开 Overlay · 全圆角",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onPrimary,
                )
            }
        }

        ComponentSample(
            name = "风格三 · Modal 顶圆角",
            api = "ThemeBottomSheet(shape = TopRounded)",
            description = "Compose 树内 ModalBottomSheet；下滑关闭；无需悬浮窗。",
        ) {
            ThemePrimaryButton(
                onClick = { showModalSheet = true },
                modifier = Modifier.fillMaxWidth(),
            ) {
                ThemeText(
                    text = "打开 Modal · 顶圆角",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

private fun showOverlaySheet(
    context: android.content.Context,
    shape: ThemeSheetShape,
    demoStyle: SheetDemoStyle,
) {
    ThemeSheet.show(
        context = context,
        shape = shape,
        onShowFailed = {
            ThemeToast.show("需要悬浮窗权限", ThemeToast.Style.Warning)
        },
    ) { dismiss ->
        SheetDemoContent(style = demoStyle, dismiss = dismiss)
    }
}

private enum class SheetDemoStyle {
    OverlayTopRounded,
    OverlayFullyRounded,
    ModalTopRounded,
}

@Composable
private fun SheetDemoContent(style: SheetDemoStyle, dismiss: () -> Unit) {
    val (title, subtitle, detail) = when (style) {
        SheetDemoStyle.OverlayTopRounded -> Triple(
            "Overlay · 顶圆角",
            "贴底宽屏 · 仅顶部 48dp 圆角",
            "TYPE_APPLICATION_OVERLAY · 点击遮罩或返回键关闭",
        )
        SheetDemoStyle.OverlayFullyRounded -> Triple(
            "Overlay · 全圆角",
            "四角 48dp 圆角 · 左右 16dp 留白浮卡",
            "shape = ThemeSheetShape.FullyRounded",
        )
        SheetDemoStyle.ModalTopRounded -> Triple(
            "Modal · 顶圆角",
            "Material3 ModalBottomSheet · 支持下滑手势",
            "Compose 树内 · 无需悬浮窗权限",
        )
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ThemeText(
            text = title,
            style = AnkioTheme.textStyles.title3,
            color = AnkioTheme.colorScheme.onSurface,
        )
        ThemeText(
            text = subtitle,
            style = AnkioTheme.textStyles.body2,
            color = AnkioTheme.colorScheme.onSurfaceVariant,
        )
        ThemeText(
            text = detail,
            style = AnkioTheme.textStyles.body2,
            color = AnkioTheme.colorScheme.onSurfaceVariant,
        )
        ThemePrimaryButton(
            onClick = dismiss,
            modifier = Modifier.fillMaxWidth(),
        ) {
            ThemeText(
                text = "关闭",
                style = AnkioTheme.textStyles.button,
                color = AnkioTheme.colorScheme.onPrimary,
            )
        }
    }
}
