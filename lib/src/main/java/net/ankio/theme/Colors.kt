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

import androidx.annotation.StringRes
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.google.android.material.color.utilities.Hct
import com.google.android.material.color.utilities.SchemeTonalSpot

/**
 * 单类语义色的 text（主色/文字）与 bg（背景色）配对。
 * text 用于图标、文字；bg 用于容器、高亮背景。
 */
@Immutable
data class SemanticColorPair(
    val text: Color,
    val bg: Color,
)

/**
 * 五类语义色：信息、警告、错误、成功、调试。
 * 不属于标准 Material 3 token，业务层通过 [AutoThemeTokens.extraColors] 或 [AnkioTheme.colorScheme] 使用。
 */
@Immutable
data class AppExtraColors(
    val info: SemanticColorPair,
    val warning: SemanticColorPair,
    val error: SemanticColorPair,
    val success: SemanticColorPair,
    val debug: SemanticColorPair,
)

/**
 * 主题色定义：种子色 ARGB + 显示名称资源 id。
 * 单一数据源 [themeColorDefs] 维护所有可用主题色，避免名称与种子色双份维护。
 */
@Immutable
data class ThemeColorDef(
    val seed: Int,
    @param:StringRes val nameRes: Int,
)

/**
 * 主题色定义表。
 * 名称与现有 themeColor 保持一致，避免迁移后用户已有配置失效。
 * 新增主题色仅需在此处加一项，所有派生 API（seed/options/displayName）自动跟随。
 */
private val themeColorDefs: Map<String, ThemeColorDef> = linkedMapOf(
    "MATERIAL_DEFAULT" to ThemeColorDef(0xFF6750A4.toInt(), R.string.theme_color_default),
    "MATERIAL_SAKURA" to ThemeColorDef(0xFFFF9CA8.toInt(), R.string.theme_color_sakura),
    "MATERIAL_RED" to ThemeColorDef(0xFFF44336.toInt(), R.string.theme_color_red),
    "MATERIAL_PINK" to ThemeColorDef(0xFFE91E63.toInt(), R.string.theme_color_pink),
    "MATERIAL_PURPLE" to ThemeColorDef(0xFF9C27B0.toInt(), R.string.theme_color_purple),
    "MATERIAL_DEEP_PURPLE" to ThemeColorDef(0xFF673AB7.toInt(), R.string.theme_color_deep_purple),
    "MATERIAL_INDIGO" to ThemeColorDef(0xFF3F51B5.toInt(), R.string.theme_color_indigo),
    "MATERIAL_BLUE" to ThemeColorDef(0xFF2196F3.toInt(), R.string.theme_color_blue),
    "MATERIAL_LIGHT_BLUE" to ThemeColorDef(0xFF03A9F4.toInt(), R.string.theme_color_light_blue),
    "MATERIAL_CYAN" to ThemeColorDef(0xFF00BCD4.toInt(), R.string.theme_color_cyan),
    "MATERIAL_TEAL" to ThemeColorDef(0xFF009688.toInt(), R.string.theme_color_teal),
    "MATERIAL_GREEN" to ThemeColorDef(0xFF4FAF50.toInt(), R.string.theme_color_green),
    "MATERIAL_LIGHT_GREEN" to ThemeColorDef(0xFF8BC3A4.toInt(), R.string.theme_color_light_green),
    "MATERIAL_LIME" to ThemeColorDef(0xFFCDDC39.toInt(), R.string.theme_color_lime),
    "MATERIAL_YELLOW" to ThemeColorDef(0xFFFFEB3B.toInt(), R.string.theme_color_yellow),
    "MATERIAL_AMBER" to ThemeColorDef(0xFFFFC107.toInt(), R.string.theme_color_amber),
    "MATERIAL_ORANGE" to ThemeColorDef(0xFFFF9800.toInt(), R.string.theme_color_orange),
    "MATERIAL_DEEP_ORANGE" to ThemeColorDef(0xFFFF5722.toInt(), R.string.theme_color_deep_orange),
    "MATERIAL_BROWN" to ThemeColorDef(0xFF795548.toInt(), R.string.theme_color_brown),
    "MATERIAL_BLUE_GREY" to ThemeColorDef(0xFF607D8F.toInt(), R.string.theme_color_blue_grey),
)

private val defaultDef: ThemeColorDef = themeColorDefs.getValue("MATERIAL_DEFAULT")

/** 给设置页或 Compose 预览用的候选主色列表 */
val keyColorOptions: List<Int> = themeColorDefs.values.map { it.seed }

/** 主题色 key 列表，供 UiSettingsScreen 等使用 */
val themeKeyOptions: List<String> = themeColorDefs.keys.toList()

/** 将 Android ARGB Int 转为 Compose Color */
fun Int.toComposeColor(): Color = Color(this)

/** 根据设置值解析种子色，未知值回退默认主题色 */
fun seedColorFromThemeKey(themeKey: String): Int =
    (themeColorDefs[themeKey] ?: defaultDef).seed

/** 根据设置值解析显示名称资源 id，未知值回退默认主题色 */
@StringRes
fun themeKeyToDisplayNameResId(themeKey: String): Int =
    (themeColorDefs[themeKey] ?: defaultDef).nameRes

/**
 * 使用 Material 自带的色彩算法从种子色生成 ColorScheme。
 * 参考 KernelSU：主题由「深浅模式 + key color」驱动。
 */
fun colorSchemeFromSeed(seedColor: Int, darkTheme: Boolean): ColorScheme {
    val scheme = SchemeTonalSpot(Hct.fromInt(seedColor), darkTheme, 0.0)

    return ColorScheme(
        primary = scheme.primary.toComposeColor(),
        onPrimary = scheme.onPrimary.toComposeColor(),
        primaryContainer = scheme.primaryContainer.toComposeColor(),
        onPrimaryContainer = scheme.onPrimaryContainer.toComposeColor(),
        inversePrimary = scheme.inversePrimary.toComposeColor(),
        secondary = scheme.secondary.toComposeColor(),
        onSecondary = scheme.onSecondary.toComposeColor(),
        secondaryContainer = scheme.secondaryContainer.toComposeColor(),
        onSecondaryContainer = scheme.onSecondaryContainer.toComposeColor(),
        tertiary = scheme.tertiary.toComposeColor(),
        onTertiary = scheme.onTertiary.toComposeColor(),
        tertiaryContainer = scheme.tertiaryContainer.toComposeColor(),
        onTertiaryContainer = scheme.onTertiaryContainer.toComposeColor(),
        background = scheme.background.toComposeColor(),
        onBackground = scheme.onBackground.toComposeColor(),
        surface = scheme.surface.toComposeColor(),
        onSurface = scheme.onSurface.toComposeColor(),
        surfaceVariant = scheme.surfaceVariant.toComposeColor(),
        onSurfaceVariant = scheme.onSurfaceVariant.toComposeColor(),
        surfaceTint = scheme.surfaceTint.toComposeColor(),
        inverseSurface = scheme.inverseSurface.toComposeColor(),
        inverseOnSurface = scheme.inverseOnSurface.toComposeColor(),
        error = scheme.error.toComposeColor(),
        onError = scheme.onError.toComposeColor(),
        errorContainer = scheme.errorContainer.toComposeColor(),
        onErrorContainer = scheme.onErrorContainer.toComposeColor(),
        outline = scheme.outline.toComposeColor(),
        outlineVariant = scheme.outlineVariant.toComposeColor(),
        scrim = scheme.scrim.toComposeColor(),
        surfaceBright = scheme.surfaceBright.toComposeColor(),
        surfaceDim = scheme.surfaceDim.toComposeColor(),
        surfaceContainer = scheme.surfaceContainer.toComposeColor(),
        surfaceContainerHigh = scheme.surfaceContainerHigh.toComposeColor(),
        surfaceContainerHighest = scheme.surfaceContainerHighest.toComposeColor(),
        surfaceContainerLow = scheme.surfaceContainerLow.toComposeColor(),
        surfaceContainerLowest = scheme.surfaceContainerLowest.toComposeColor()
    )
}

/** 返回业务扩展色，根据深浅模式提供适配的 text/bg 配色 */
fun appExtraColors(darkTheme: Boolean): AppExtraColors {
    return if (darkTheme) {
        AppExtraColors(
            info = SemanticColorPair(text = Color(0xFF64B5F6), bg = Color(0x3364B5F6)),
            warning = SemanticColorPair(text = Color(0xFFFFB74D), bg = Color(0x33FFB74D)),
            error = SemanticColorPair(text = Color(0xFFEF5350), bg = Color(0x33EF5350)),
            success = SemanticColorPair(text = Color(0xFF66BB6A), bg = Color(0x3366BB6A)),
            debug = SemanticColorPair(text = Color(0xFFB0BEC5), bg = Color(0x33B0BEC5)),
        )
    } else {
        AppExtraColors(
            info = SemanticColorPair(text = Color(0xFF1976D2), bg = Color(0x1F1976D2)),
            warning = SemanticColorPair(text = Color(0xFFF57C00), bg = Color(0x1FF57C00)),
            error = SemanticColorPair(text = Color(0xFFC62828), bg = Color(0x1FC62828)),
            success = SemanticColorPair(text = Color(0xFF2E7D32), bg = Color(0x1F2E7D32)),
            debug = SemanticColorPair(text = Color(0xFF6C757D), bg = Color(0x1F6C757D)),
        )
    }
}
