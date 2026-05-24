/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeCard
import net.ankio.theme.compat.ThemeSmallTitle
import net.ankio.theme.compat.ThemeText

/**
 * 组件展示页。按类目分块展示 lib 中所有 Theme* 组件用例。
 * 每个分块就是一个 [SectionCard]：标题 + 组件实例。
 */
@Composable
fun ComponentsGallery(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        StatusSection()
        ButtonsSection()
        FormsSection()
        IconsSection()
        ContainersSection()
        DisplaySection()
        TextStylesSection()
        ShapesSection()
        NavigationSection()
        PopupSection()
        ToastSection()
        ThemeTokensSection()
        Spacer(Modifier.height(24.dp))
    }
}

/**
 * 类目卡片：统一的标题 + 内容容器。
 * 用 ThemeSmallTitle 做小标题，下面用一个 ThemeCard 装组件实例。
 */
@Composable
internal fun SectionCard(
    title: String,
    content: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ThemeSmallTitle(text = title)
        ThemeCard(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                content()
            }
        }
    }
}

/** 子组件描述行，标注组件名 */
@Composable
internal fun Caption(text: String) {
    ThemeText(
        text = text,
        style = AnkioTheme.textStyles.footnote1,
        color = AnkioTheme.colorScheme.onSurfaceVariant,
    )
}
