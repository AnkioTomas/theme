/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemePrimaryButton
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.demo.ui.Caption
import net.ankio.theme.demo.ui.ComponentSample
import net.ankio.theme.demo.ui.SectionCard
import net.ankio.theme.sheet.ThemeBottomSheet
import net.ankio.theme.sheet.ThemeSheet

/**
 * BottomSheet 展示：[ThemeSheet.show]（Context 调起）与 [ThemeBottomSheet]（Compose 树内）。
 */
@Composable
fun SheetSection() {
    val context = LocalContext.current
    var showComposeSheet by remember { mutableStateOf(false) }

    if (showComposeSheet) {
        ThemeBottomSheet(onDismissRequest = { showComposeSheet = false }) { dismiss ->
            SheetDemoContent(title = "ThemeBottomSheet", dismiss = dismiss)
        }
    }

    SectionCard(title = "BottomSheet / 底部弹层") {
        ComponentSample(
            name = "ThemeSheet",
            api = "ThemeSheet.show(context) { dismiss -> ... }",
            description = "从 Activity 等 Context 调起；lambda 写内容，32dp 顶部大圆角。",
        ) {
            ThemePrimaryButton(
                onClick = {
                    ThemeSheet.show(context) { dismiss ->
                        SheetDemoContent(title = "ThemeSheet", dismiss = dismiss)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                ThemeText(
                    text = "打开 ThemeSheet",
                    style = AnkioTheme.textStyles.button,
                    color = AnkioTheme.colorScheme.onPrimary,
                )
            }
        }

        Caption("ThemeBottomSheet（Compose 内）")
        ThemePrimaryButton(
            onClick = { showComposeSheet = true },
            modifier = Modifier.fillMaxWidth(),
        ) {
            ThemeText(
                text = "打开 ThemeBottomSheet",
                style = AnkioTheme.textStyles.button,
                color = AnkioTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
private fun SheetDemoContent(title: String, dismiss: () -> Unit) {
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
            text = "顶部圆角 32dp，内容区 24dp 水平内边距。",
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
