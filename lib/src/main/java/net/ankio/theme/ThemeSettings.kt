/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
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

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.core.content.edit
import com.google.android.material.color.DynamicColors

/** 与 AppCompatDelegate 夜模式常量一致，供 setDefaultNightMode 使用 */
private const val MODE_NIGHT_NO = 1
private const val MODE_NIGHT_YES = 2
private const val MODE_NIGHT_FOLLOW_SYSTEM = -1

/**
 * 主题配置管理器，由 theme 库直接管理存储。
 * 需在 Application.onCreate 中调用 [init]。
 */
object ThemeSettings {

    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_UI_MODE = "ui_mode"
    private const val KEY_COLOR_MODE = "color_mode"
    private const val KEY_FOLLOW_SYSTEM_ACCENT = "follow_system_accent"
    private const val KEY_THEME_COLOR = "theme_color"
    private const val KEY_DISPLAY_PERCENTAGE = "display_percentage"

    private const val DEFAULT_UI_MODE = "miuix"
    private const val DEFAULT_THEME_COLOR = "MATERIAL_DEFAULT"
    private const val DEFAULT_DISPLAY_PERCENTAGE = 100

    private lateinit var appContext: Context
    private lateinit var pref: SharedPreferences

    /** 在 Application.onCreate 调用，传入 application context。 */
    fun init(context: Context) {
        appContext = context.applicationContext
        pref = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun requirePref(): SharedPreferences {
        check(::pref.isInitialized) {
            "ThemeSettings.init(application) 必须在 Application.onCreate 中调用"
        }
        return pref
    }

    /** UI 风格：miuix / material */
    var uiMode: String
        get() = requirePref().getString(KEY_UI_MODE, DEFAULT_UI_MODE) ?: DEFAULT_UI_MODE
        set(value) = requirePref().edit { putString(KEY_UI_MODE, value) }

    /** 颜色模式 0-6（含 DARK_AMOLED） */
    var colorMode: Int
        get() = requirePref().getInt(KEY_COLOR_MODE, 0)
        set(value) = requirePref().edit { putInt(KEY_COLOR_MODE, value) }

    /** 跟随系统强调色 */
    var followSystemAccent: Boolean
        get() = requirePref().getBoolean(KEY_FOLLOW_SYSTEM_ACCENT, true)
        set(value) = requirePref().edit { putBoolean(KEY_FOLLOW_SYSTEM_ACCENT, value) }

    /** 主题色标识（如 MATERIAL_DEFAULT） */
    var themeColor: String
        get() = requirePref().getString(KEY_THEME_COLOR, DEFAULT_THEME_COLOR) ?: DEFAULT_THEME_COLOR
        set(value) = requirePref().edit { putString(KEY_THEME_COLOR, value) }

    /** 页面显示百分比 70-120，控制页面显示效果 */
    var displayPercentage: Int
        get() = requirePref().getInt(KEY_DISPLAY_PERCENTAGE, DEFAULT_DISPLAY_PERCENTAGE)
            .coerceIn(70, 120)
        set(value) = requirePref().edit { putInt(KEY_DISPLAY_PERCENTAGE, value.coerceIn(70, 120)) }

    /** 当前 ColorMode 枚举值 */
    private val currentColorMode: ColorMode get() = ColorMode.fromValue(colorMode)

    /** 解析后的 keyColor：0=跟随系统动态色，否则为种子色 ARGB */
    val keyColor: Int
        get() = if (followSystemAccent && DynamicColors.isDynamicColorAvailable()) 0
        else seedColorFromThemeKey(themeColor)

    /** 转 AppCompatDelegate 夜模式（返回值与 MODE_NIGHT_* 一致） */
    fun colorModeToNightMode(): Int {
        val mode = currentColorMode
        return when {
            mode.isDark -> MODE_NIGHT_YES
            mode.isSystem -> MODE_NIGHT_FOLLOW_SYSTEM
            else -> MODE_NIGHT_NO
        }
    }

    private fun isNightModeEnabled(config: Configuration): Boolean {
        val mode = currentColorMode
        return mode.isDark ||
                (mode.isSystem &&
                        (config.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES)
    }

    /** 根据 colorMode 判断是否启用夜间主题 */
    fun shouldUseDarkTheme(context: Context): Boolean =
        isNightModeEnabled(context.resources.configuration)

    /** 是否处于深色主题（需已 init） */
    val isDark: Boolean
        get() = isNightModeEnabled(requireContext().resources.configuration)

    private fun requireContext(): Context {
        check(::appContext.isInitialized) {
            "ThemeSettings.init(application) 必须在 Application.onCreate 中调用"
        }
        return appContext
    }

    /** 主题化上下文，供 View 膨胀使用 */
    fun themedContext(context: Context): Context {
        val config = context.resources.configuration
        val shouldUseNight = isNightModeEnabled(config)
        val desiredNightFlag =
            if (shouldUseNight) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO
        val currentNightFlag = config.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentNightFlag == desiredNightFlag) return context
        val override = Configuration(config).apply {
            uiMode = (config.uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()) or desiredNightFlag
        }
        return context.createConfigurationContext(override)
    }

    /** 当前 AppSettings，供 AutoTheme 使用 */
    fun getAppSettings(): AppSettings = AppSettings(currentColorMode, keyColor)
}
