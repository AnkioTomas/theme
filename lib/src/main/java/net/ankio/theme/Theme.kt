/*
 * Copyright (C) 2025 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-3.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package net.ankio.theme

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowInsetsControllerCompat
import top.yukonga.miuix.kmp.theme.ColorSchemeMode
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.theme.ThemeController as MiuixThemeController

/** CompositionLocal：当前 ColorMode，供 [isInDarkTheme] 等使用 */
val LocalColorMode = staticCompositionLocalOf { ColorMode.SYSTEM }

/** Compose 侧业务扩展色的 CompositionLocal */
private val LocalAppExtraColors = staticCompositionLocalOf<AppExtraColors> {
    error("AppExtraColors 未通过 AutoTheme 初始化")
}

/** 判断当前是否处于深色主题 */
@Composable
@ReadOnlyComposable
fun isInDarkTheme(): Boolean {
    val mode = LocalColorMode.current
    return mode.isDark || (mode.isSystem && isSystemInDarkTheme())
}

/**
 * Compose 主题入口。根据 [uiMode] 切换 Miuix / Material。
 *
 * @param appSettings 主题配置；为 null 时从 [ThemeSettings] 读取（需先调用 [ThemeSettings.init]）
 */
@Composable
fun AutoTheme(
    appSettings: AppSettings? = null,
    uiMode: UiMode = LocalUiMode.current,
    content: @Composable () -> Unit
) {
    val settings = appSettings ?: ThemeSettings.getAppSettings()
    val systemDark = isSystemInDarkTheme()
    val darkTheme = settings.colorMode.isDark || (settings.colorMode.isSystem && systemDark)
    val extraColors = remember(darkTheme) { appExtraColors(darkTheme) }

    CompositionLocalProvider(
        LocalColorMode provides settings.colorMode,
        LocalAppExtraColors provides extraColors
    ) {
        when (uiMode) {
            UiMode.Miuix -> MiuixWrapped(settings, darkTheme) {
                MaterialBlock(settings, darkTheme, content)
            }
            UiMode.Material -> MaterialBlock(settings, darkTheme, content)
        }
    }
}

/**
 * Material 主题块：解析 ColorScheme + 同步状态栏 + 应用 [MaterialTheme]。
 * Miuix 与 Material 分支共享此实现。
 */
@Composable
private fun MaterialBlock(
    appSettings: AppSettings,
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = remember(appSettings.keyColor, darkTheme) {
        resolveColorScheme(context, appSettings.keyColor, darkTheme)
    }

    LaunchedEffect(darkTheme) { applyStatusBarStyle(context, darkTheme) }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = appShapes(),
        typography = AppTypography,
        content = content
    )
}

/** Miuix 主题包装层，仅负责套一层 [MiuixTheme]，由内部 [MaterialBlock] 提供 Material API。 */
@Composable
private fun MiuixWrapped(
    appSettings: AppSettings,
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorSchemeMode = when (appSettings.colorMode) {
        ColorMode.SYSTEM -> ColorSchemeMode.System
        ColorMode.LIGHT -> ColorSchemeMode.Light
        ColorMode.DARK -> ColorSchemeMode.Dark
        ColorMode.MONET_SYSTEM -> ColorSchemeMode.MonetSystem
        ColorMode.MONET_LIGHT -> ColorSchemeMode.MonetLight
        ColorMode.MONET_DARK, ColorMode.DARK_AMOLED -> ColorSchemeMode.MonetDark
    }

    val controller = remember(colorSchemeMode, darkTheme, appSettings.keyColor) {
        MiuixThemeController(
            colorSchemeMode = colorSchemeMode,
            keyColor = if (appSettings.keyColor == 0) null else Color(appSettings.keyColor),
            isDark = darkTheme
        )
    }

    MiuixTheme(controller = controller, content = content)
}

/** 解析 ColorScheme：keyColor==0 走系统动态色（API 31+），否则用种子色生成。 */
private fun resolveColorScheme(
    context: Context,
    keyColor: Int,
    darkTheme: Boolean
): ColorScheme {
    val useDynamic = keyColor == 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    return when {
        useDynamic && darkTheme -> dynamicDarkColorScheme(context)
        useDynamic -> dynamicLightColorScheme(context)
        else -> colorSchemeFromSeed(keyColor, darkTheme)
    }
}

/** 同步状态栏与导航栏图标深浅，仅当 context 是 Activity 时生效。 */
private fun applyStatusBarStyle(context: Context, darkTheme: Boolean) {
    val window = (context as? Activity)?.window ?: return
    WindowInsetsControllerCompat(window, window.decorView).apply {
        isAppearanceLightStatusBars = !darkTheme
        isAppearanceLightNavigationBars = !darkTheme
    }
}

/** Compose 侧扩展色访问器，用法：`AutoThemeTokens.extraColors.error.text` */
@Stable
object AutoThemeTokens {
    val extraColors: AppExtraColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppExtraColors.current
}
