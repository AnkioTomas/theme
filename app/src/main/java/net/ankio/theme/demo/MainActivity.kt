package net.ankio.theme.demo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.BaseComposeActivity
import net.ankio.theme.ThemeSettings
import net.ankio.theme.UiMode
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemeTabRow
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.compat.ThemeTopAppBar
import net.ankio.theme.demo.gallery.ComponentsGallery
import net.ankio.theme.settings.UiSettingsOptions
import net.ankio.theme.settings.UiSettingsScreen

/**
 * Demo 主页。
 * 边到边布局：BaseComposeActivity 已 enableEdgeToEdge() + ThemeSurface 填充全屏背景；
 * 这里只需在内容区做 systemBars padding，避开状态栏与导航栏。
 *
 * UI：顶部 [ThemeTopAppBar] + [ThemeTabRow] 切换「组件」「设置」。
 */
class MainActivity : BaseComposeActivity() {

    @Composable
    override fun Content() {
        var tabIndex by remember { mutableIntStateOf(0) }
        val tabs = listOf("组件", "设置")
        val title = if (tabIndex == 0) "组件展示" else "主题设置"

        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
        ) {
            ThemeTopAppBar(
                title = title,
                modifier = Modifier.fillMaxWidth(),
                actions = {
                    ThemeText(
                        text = if (ThemeSettings.isDark) "Dark" else "Light",
                        style = AnkioTheme.textStyles.footnote1,
                        color = AnkioTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(end = 8.dp),
                    )
                    ThemeIconButton(onClick = ::recreateForThemeChange) {
                        ThemeIcon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "重建",
                            tint = AnkioTheme.colorScheme.onSurface,
                        )
                    }
                },
            )

            ThemeTabRow(
                tabs = tabs,
                selectedTabIndex = tabIndex,
                onTabSelected = { tabIndex = it },
                modifier = Modifier.fillMaxWidth(),
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when (tabIndex) {
                    0 -> ComponentsGallery(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                    )
                    else -> UiSettingsScreen(
                        options = demoOptions,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        onThemeChanged = ::recreateForThemeChange,
                    )
                }
            }
        }
    }

    companion object {
        private val demoOptions = UiSettingsOptions(
            uiModeEntries = listOf(
                UiMode.Miuix.value to "Miuix",
                UiMode.Material.value to "Material",
            ),
            colorModeEntries = listOf(
                0 to "Follow system (MIUI)",
                1 to "Light",
                2 to "Dark",
                3 to "Follow system (Monet)",
                4 to "Light (Monet)",
                5 to "Dark (Monet)",
                6 to "Dark (AMOLED)",
            ),
            themeColorEntries = emptyList(),
        )
    }
}
