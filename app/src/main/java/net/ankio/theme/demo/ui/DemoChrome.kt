/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeCard
import net.ankio.theme.compat.ThemeSmallTitle
import net.ankio.theme.compat.ThemeSurface
import net.ankio.theme.compat.ThemeText

/** 详情页顶部说明 */
@Composable
fun DemoPageHeader(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        ThemeText(
            text = title,
            style = AnkioTheme.textStyles.title3,
            color = AnkioTheme.colorScheme.onSurface,
        )
        ThemeText(
            text = description,
            style = AnkioTheme.textStyles.body2,
            color = AnkioTheme.colorScheme.onSurfaceVariant,
        )
    }
}

/** 大类目卡片：标题 + 多个示例 */
@Composable
fun SectionCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ThemeSmallTitle(
            text = title,
            modifier = Modifier.padding(vertical = 8.dp),
        )
        ThemeCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                content = content,
            )
        }
    }
}

/**
 * 单个组件示例块：名称、API 签名、说明、可交互预览。
 */
@Composable
fun ComponentSample(
    name: String,
    api: String,
    description: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ThemeText(
            text = name,
            style = AnkioTheme.textStyles.title4,
            color = AnkioTheme.colorScheme.onSurface,
        )
        if (description.isNotBlank()) {
            ThemeText(
                text = description,
                style = AnkioTheme.textStyles.body2,
                color = AnkioTheme.colorScheme.onSurfaceVariant,
            )
        }
        ApiSignature(text = api)
        ThemeSurface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = AnkioTheme.colorScheme.surfaceContainerLow,
            contentColor = AnkioTheme.colorScheme.onSurface,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            ) {
                content()
            }
        }
    }
}

@Composable
fun ApiSignature(text: String) {
    ThemeSurface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = AnkioTheme.colorScheme.surfaceContainerHighest,
        contentColor = AnkioTheme.colorScheme.onSurfaceVariant,
    ) {
        ThemeText(
            text = text,
            style = AnkioTheme.textStyles.footnote1,
            color = AnkioTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
        )
    }
}

@Composable
fun Caption(text: String) {
    ThemeText(
        text = text,
        style = AnkioTheme.textStyles.footnote1,
        color = AnkioTheme.colorScheme.onSurfaceVariant,
    )
}
