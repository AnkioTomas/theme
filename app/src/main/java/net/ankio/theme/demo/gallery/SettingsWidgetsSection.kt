/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import net.ankio.theme.AnkioTheme
import net.ankio.theme.settings.SettingCard
import net.ankio.theme.settings.SettingCardPosition
import net.ankio.theme.settings.ThemeSettingClick
import net.ankio.theme.settings.ThemeSettingDropdown
import net.ankio.theme.settings.ThemeSettingSwitch
import net.ankio.theme.settings.ThemeSettingTextField
import net.ankio.theme.demo.ui.ComponentSample
import net.ankio.theme.demo.ui.SectionCard

@Composable
fun SettingsWidgetsSection() {
    SectionCard(title = "设置页原子组件") {
        var switchOn by remember { mutableStateOf(true) }
        var dropdownIndex by remember { mutableIntStateOf(0) }
        var text by remember { mutableStateOf("ankio@ankio.net") }

        ComponentSample(
            name = "SettingCard",
            api = "SettingCard(icon, title, subtitle, trailing, position, onClick)",
            description = "设置列表基础卡片。position 控制分组圆角：First / Middle / Last / Single。",
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                SettingCard(
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Tune,
                            contentDescription = null,
                            tint = AnkioTheme.colorScheme.primary,
                        )
                    },
                    title = "示例设置项",
                    subtitle = "点击整行或右侧控件",
                    onClick = {},
                    position = SettingCardPosition.Single,
                )
            }
        }

        ComponentSample(
            name = "ThemeSettingSwitch",
            api = "ThemeSettingSwitch(title, checked, onCheckedChange, startAction)",
            description = "右侧 ThemeSwitch，左侧标题副标题。",
        ) {
            ThemeSettingSwitch(
                title = "深色模式",
                summary = "切换应用深色主题",
                checked = switchOn,
                onCheckedChange = { switchOn = it },
                startAction = {
                    Icon(Icons.Filled.DarkMode, null, tint = AnkioTheme.colorScheme.primary)
                },
                position = SettingCardPosition.Single,
            )
        }

        ComponentSample(
            name = "ThemeSettingDropdown",
            api = "ThemeSettingDropdown(items, selectedIndex, onSelectedIndexChange, title)",
            description = "点击弹出下拉选择。items 为字符串列表。",
        ) {
            ThemeSettingDropdown(
                items = listOf("默认", "蓝色", "绿色"),
                selectedIndex = dropdownIndex,
                onSelectedIndexChange = { dropdownIndex = it },
                title = "主题色",
                summary = "选择种子色",
                startAction = {
                    Icon(Icons.Filled.Palette, null, tint = AnkioTheme.colorScheme.primary)
                },
                position = SettingCardPosition.Single,
            )
        }

        ComponentSample(
            name = "ThemeSettingTextField",
            api = "ThemeSettingTextField(value, onValueChange, title, startAction)",
            description = "设置页内嵌文本输入。",
        ) {
            ThemeSettingTextField(
                value = text,
                onValueChange = { text = it },
                title = "邮箱",
                summary = "用于通知",
                label = "邮箱地址",
                startAction = {
                    Icon(Icons.Filled.Tune, null, tint = AnkioTheme.colorScheme.primary)
                },
                position = SettingCardPosition.Single,
            )
        }

        ComponentSample(
            name = "ThemeSettingClick",
            api = "ThemeSettingClick(title, onClick, startAction)",
            description = "整行可点击，用于跳转类设置项。",
        ) {
            ThemeSettingClick(
                title = "关于",
                summary = "版本 1.0",
                onClick = {},
                startAction = {
                    Icon(Icons.Filled.Tune, null, tint = AnkioTheme.colorScheme.primary)
                },
                position = SettingCardPosition.Single,
            )
        }

        ComponentSample(
            name = "完整设置页",
            api = "UiSettingsScreen(options, onThemeChanged)",
            description = "已内置在「设置」Tab。options 可裁剪选项与自定义文案。",
        ) {
            net.ankio.theme.compat.ThemeText(
                text = "请切换到主界面「设置」Tab 查看完整页面",
                style = AnkioTheme.textStyles.body2,
                color = AnkioTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
