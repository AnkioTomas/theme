/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.AppSettings
import net.ankio.theme.LocalColorMode
import net.ankio.theme.LocalUiMode
import net.ankio.theme.ThemeSettings
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.demo.ui.ComponentSample
import net.ankio.theme.demo.ui.SectionCard
import net.ankio.theme.isInDarkTheme

@Composable
fun OverviewSection() {
    SectionCard(title = "集成要点") {
        ComponentSample(
            name = "Application 初始化",
            api = "ThemeSettings.init(applicationContext)",
            description = "在 Application.onCreate 中调用一次。所有读取 ThemeSettings 的代码都依赖此步骤。",
        ) {
            ThemeText(
                text = "DemoApp 已调用 init ✓",
                style = AnkioTheme.textStyles.body1,
                color = AnkioTheme.colorScheme.primary,
            )
        }

        ComponentSample(
            name = "Compose 根主题",
            api = "AutoTheme(appSettings = null) { /* 内容 */ }",
            description = "包裹页面根 Composable。appSettings 为 null 时从 ThemeSettings 读取；预览可传入 AppSettings 跳过 SP。",
        ) {
            ThemeText(
                text = "当前页面已被 BaseComposeActivity + AutoTheme 包裹",
                style = AnkioTheme.textStyles.body2,
                color = AnkioTheme.colorScheme.onSurface,
            )
        }

        ComponentSample(
            name = "Activity 基类",
            api = "class X : BaseComposeActivity() { @Composable override fun Content() }",
            description = "自动 enableEdgeToEdge、设置夜模式、注入 LocalUiMode 与 ThemeSurface。",
        ) {
            ThemeText(
                text = "MainActivity : BaseComposeActivity",
                style = AnkioTheme.textStyles.body2,
                color = AnkioTheme.colorScheme.onSurface,
            )
        }

        ComponentSample(
            name = "预测性返回",
            api = "PredictiveBackHandler + enableOnBackInvokedCallback",
            description = "分类详情：侧滑预览退出；设置 Tab：返回组件目录；根页：系统返回桌面动画。",
        ) {
            ThemeText(
                text = "Android 13+ 边缘返回可预览；详情页随进度右移",
                style = AnkioTheme.textStyles.body2,
                color = AnkioTheme.colorScheme.onSurface,
            )
        }
    }

    SectionCard(title = "运行时状态") {
        ComponentSample(
            name = "CompositionLocal",
            api = "LocalUiMode.current / LocalColorMode.current / isInDarkTheme()",
            description = "在 Composable 内读取，随 AutoTheme 与系统深色模式变化。",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                StatusRow("LocalUiMode", LocalUiMode.current.name)
                StatusRow("LocalColorMode", LocalColorMode.current.name)
                StatusRow("isInDarkTheme()", isInDarkTheme().toString())
            }
        }

        ComponentSample(
            name = "ThemeSettings",
            api = "ThemeSettings.uiMode / colorMode / themeColor / displayPercentage",
            description = "持久化配置。修改后通常调用 activity.recreate() 或 setDefaultNightMode + recreate。",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                StatusRow("uiMode", ThemeSettings.uiMode)
                StatusRow("colorMode", ThemeSettings.colorMode.toString())
                StatusRow("themeColor", ThemeSettings.themeColor)
                StatusRow("displayPercentage", "${ThemeSettings.displayPercentage}%")
                StatusRow("isDark", ThemeSettings.isDark.toString())
                StatusRow("keyColor", "0x${"%08X".format(ThemeSettings.keyColor)}")
            }
        }

        ComponentSample(
            name = "预览用 AppSettings",
            api = "AppSettings(colorMode, keyColor, displayPercentage = 100)",
            description = "配合 PreviewAllThemes 在 Android Studio 预览，不依赖 SharedPreferences。",
        ) {
            val preview = AppSettings(
                colorMode = LocalColorMode.current,
                keyColor = ThemeSettings.keyColor,
                displayPercentage = ThemeSettings.displayPercentage,
            )
            ThemeText(
                text = "colorMode=${preview.colorMode}, scale=${preview.displayPercentage}%",
                style = AnkioTheme.textStyles.footnote1,
                color = AnkioTheme.colorScheme.onSurfaceVariant,
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            val previewColor = if (ThemeSettings.keyColor == 0) {
                AnkioTheme.colorScheme.primary
            } else {
                Color(ThemeSettings.keyColor)
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(previewColor),
            )
            ThemeText(
                text = if (ThemeSettings.followSystemAccent) "跟随系统动态色" else "用户种子色",
                style = AnkioTheme.textStyles.body2,
                color = AnkioTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 12.dp),
            )
        }
    }
}

@Composable
private fun StatusRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ThemeText(
            text = label,
            style = AnkioTheme.textStyles.body2,
            color = AnkioTheme.colorScheme.onSurfaceVariant,
        )
        ThemeText(
            text = value,
            style = AnkioTheme.textStyles.body2,
            color = AnkioTheme.colorScheme.onSurface,
        )
    }
}
