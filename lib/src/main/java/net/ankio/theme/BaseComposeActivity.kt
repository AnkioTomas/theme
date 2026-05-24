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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import net.ankio.theme.compat.ThemeSurface

/**
 * Compose 基础 Activity：自动包裹 [AutoTheme] 与当前 [LocalUiMode]。
 * 子类实现 [Content] 即可，无需关心 Miuix / Material 切换细节。
 */
abstract class BaseComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppCompatDelegate.setDefaultNightMode(ThemeSettings.colorModeToNightMode())

        setContent {
            CompositionLocalProvider(
                LocalUiMode provides UiMode.fromValue(ThemeSettings.uiMode),
            ) {
                AutoTheme {
                    ThemeSurface(
                        modifier = Modifier.fillMaxSize(),
                        color = AnkioTheme.colorScheme.surface,
                    ) {
                        Content()
                    }
                }
            }
        }
    }

    /** 已被 AutoTheme + ThemeSurface 包裹的页面内容 */
    @Composable
    abstract fun Content()

    /** 主题设置变更后重建 Activity，供设置页回调 */
    protected fun recreateForThemeChange() {
        AppCompatDelegate.setDefaultNightMode(ThemeSettings.colorModeToNightMode())
        recreate()
    }
}
