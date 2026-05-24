/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.AppSettings
import net.ankio.theme.ColorMode
import net.ankio.theme.PreviewAll
import net.ankio.theme.PreviewAllThemes
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.UiMode
import net.ankio.theme.compat.TextFieldStyle
import net.ankio.theme.compat.ThemeCard
import net.ankio.theme.compat.ThemeSwitch
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.compat.ThemeTextField
import net.ankio.theme.demo.gallery.ButtonsSection
import net.ankio.theme.seedColorFromThemeKey
import net.ankio.theme.settings.UiSettingsOptions
import net.ankio.theme.settings.UiSettingsScreen

private val demoSeed = seedColorFromThemeKey("MATERIAL_DEFAULT")

/** Demo 预览用 3 种主题：Miuix 浅/深 + Material 浅 */
class DemoPreviewParameterProvider : PreviewParameterProvider<ThemePreviewConfig> {
    private val configs = listOf(
        ThemePreviewConfig(
            name = "Miuix Light",
            appSettings = AppSettings(ColorMode.LIGHT, demoSeed),
            darkConfig = false,
            uiMode = UiMode.Miuix,
        ),
        ThemePreviewConfig(
            name = "Miuix Dark",
            appSettings = AppSettings(ColorMode.DARK, demoSeed),
            darkConfig = true,
            uiMode = UiMode.Miuix,
        ),
        ThemePreviewConfig(
            name = "Material Light",
            appSettings = AppSettings(ColorMode.LIGHT, demoSeed),
            darkConfig = false,
            uiMode = UiMode.Material,
        ),
    )

    override val values = configs.asSequence()

    override fun getDisplayName(index: Int): String? = configs.getOrNull(index)?.name
}

@PreviewAll
@Composable
private fun ButtonsSectionPreview(
    @PreviewParameter(DemoPreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewAllThemes(config) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            ButtonsSection()
        }
    }
}

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
                    verticalAlignment = Alignment.CenterVertically,
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
