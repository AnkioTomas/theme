/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.ColorMode
import net.ankio.theme.PreviewAll
import net.ankio.theme.PreviewAllThemes
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.UiMode
import net.ankio.theme.compat.TextFieldStyle
import net.ankio.theme.compat.ThemeCard
import net.ankio.theme.compat.ThemeNavigationBar
import net.ankio.theme.compat.ThemeNavigationBarItem
import net.ankio.theme.compat.ThemePrimaryButton
import net.ankio.theme.compat.ThemeSecondaryButton
import net.ankio.theme.compat.ThemeSwitch
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.compat.ThemeTextField
import net.ankio.theme.demo.gallery.ButtonsSection
import net.ankio.theme.demo.gallery.ThemeTokensSection
import net.ankio.theme.settings.UiSettingsOptions
import net.ankio.theme.settings.UiSettingsScreen

/**
 * Demo 常用预览子集（6 项），比 lib 全量 [ThemePreviewParameterProvider] 更快。
 * 需要全量 20 项时改用 [ThemePreviewParameterProvider]。
 */
class DemoPreviewParameterProvider : PreviewParameterProvider<ThemePreviewConfig> {
    private val configs = listOf(
        ThemePreviewConfig.of(UiMode.Miuix, ColorMode.LIGHT, darkConfig = false),
        ThemePreviewConfig.of(UiMode.Miuix, ColorMode.DARK, darkConfig = true),
        ThemePreviewConfig.of(UiMode.Miuix, ColorMode.DARK_AMOLED, darkConfig = true),
        ThemePreviewConfig.of(UiMode.Material, ColorMode.MONET_LIGHT, darkConfig = false),
        ThemePreviewConfig.of(UiMode.Material, ColorMode.MONET_DARK, darkConfig = true),
        ThemePreviewConfig.of(
            uiMode = UiMode.Miuix,
            colorMode = ColorMode.LIGHT,
            darkConfig = false,
            name = "Miuix Light (Floating Nav)",
            navigationBarFloating = true,
        ),
    )

    override val values = configs.asSequence()

    override fun getDisplayName(index: Int): String? = configs.getOrNull(index)?.name
}

// ── 按钮 ──────────────────────────────────────────────────────────────────

@PreviewAll
@Composable
private fun ButtonsSectionPreview(
    @PreviewParameter(DemoPreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewAllThemes(config) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            ButtonsSection()
        }
    }
}

@PreviewAll
@Composable
private fun ButtonsCompactPreview(
    @PreviewParameter(DemoPreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewAllThemes(config) {
        ThemeCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ThemePrimaryButton(onClick = {}) {
                    ThemeText(
                        text = "Primary",
                        style = AnkioTheme.textStyles.button,
                        color = AnkioTheme.colorScheme.onPrimary,
                    )
                }
                ThemeSecondaryButton(onClick = {}) {
                    ThemeText(
                        text = "Secondary",
                        style = AnkioTheme.textStyles.button,
                        color = AnkioTheme.colorScheme.onSecondaryContainer,
                    )
                }
            }
        }
    }
}

// ── 表单 ──────────────────────────────────────────────────────────────────

@PreviewAll
@Composable
private fun FormsPreview(
    @PreviewParameter(DemoPreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewAllThemes(config) {
        var switchOn by remember { mutableStateOf(true) }
        var text by remember { mutableStateOf("demo@ankio.net") }

        ThemeCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                ) {
                    ThemeText(
                        text = "通知",
                        style = AnkioTheme.textStyles.body1,
                        color = AnkioTheme.colorScheme.onSurface,
                    )
                    ThemeSwitch(checked = switchOn, onCheckedChange = { switchOn = it })
                }
                ThemeTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxWidth(),
                    style = TextFieldStyle.Outlined,
                    label = "邮箱",
                    placeholder = "请输入邮箱",
                )
            }
        }
    }
}

// ── 导航栏（含悬浮模式，读 config.navigationBarFloating）──────────────────

@PreviewAll
@Composable
private fun NavigationBarPreview(
    @PreviewParameter(DemoPreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewAllThemes(config) {
        var selected by remember { mutableIntStateOf(0) }
        val items = listOf(
            "首页" to Icons.Filled.Home,
            "通知" to Icons.Filled.Notifications,
            "设置" to Icons.Filled.Settings,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ThemeText(
                text = if (config.navigationBarFloating) "悬浮导航栏" else "普通导航栏",
                style = AnkioTheme.textStyles.footnote1,
                color = AnkioTheme.colorScheme.onSurfaceVariant,
            )
            ThemeNavigationBar(modifier = Modifier.fillMaxWidth()) {
                items.forEachIndexed { index, (label, icon) ->
                    ThemeNavigationBarItem(
                        selected = selected == index,
                        onClick = { selected = index },
                        icon = icon,
                        label = label,
                    )
                }
            }
        }
    }
}

// ── 主题令牌 ──────────────────────────────────────────────────────────────

@PreviewAll
@Composable
private fun ThemeTokensPreview(
    @PreviewParameter(DemoPreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewAllThemes(config) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            ThemeTokensSection()
        }
    }
}

// ── 设置页（全量 ColorMode 选项）──────────────────────────────────────────

@PreviewAll
@Composable
private fun UiSettingsPreview(
    @PreviewParameter(DemoPreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    val previewOptions = UiSettingsOptions(
        uiModeEntries = listOf("miuix" to "Miuix", "material" to "Material"),
        colorModeEntries = listOf(
            0 to "跟随系统 (MIUI)",
            1 to "浅色",
            2 to "深色",
            3 to "跟随系统 (Monet)",
            4 to "浅色 (Monet)",
            5 to "深色 (Monet)",
            6 to "AMOLED",
        ),
    )

    PreviewAllThemes(config) {
        UiSettingsScreen(
            options = previewOptions,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}

// ── 全量主题矩阵（20 项，验证所有 ColorMode × UiMode）────────────────────

@PreviewAll
@Composable
private fun FullMatrixButtonsPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewAllThemes(config) {
        ThemeCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                ThemeText(
                    text = config.name,
                    style = AnkioTheme.textStyles.title4,
                    color = AnkioTheme.colorScheme.onSurface,
                )
                ThemePrimaryButton(onClick = {}) {
                    ThemeText(
                        text = "Preview",
                        style = AnkioTheme.textStyles.button,
                        color = AnkioTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
    }
}
