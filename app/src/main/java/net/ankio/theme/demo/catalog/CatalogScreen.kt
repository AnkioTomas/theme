/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeCard
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.demo.ui.DemoPageHeader

@Composable
fun CatalogScreen(
    listState: LazyListState,
    nestedScrollModifier: Modifier,
    onOpenCategory: (DemoCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .then(nestedScrollModifier),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item(key = "header") {
            DemoPageHeader(
                title = "滚动列表",
                description = "上滑时顶部大标题会折叠。返回本页时列表位置会保留（LazyListState 绑定 Nav 返回栈条目）。",
            )
        }

        DemoCategory.byGroup().forEach { (group, categories) ->
            item(key = "group_${group.name}") {
                ThemeText(
                    text = group.label,
                    style = AnkioTheme.textStyles.title4,
                    color = AnkioTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                )
            }
            items(categories, key = { it.name }) { category ->
                CategoryRow(category = category, onClick = { onOpenCategory(category) })
            }
        }
    }
}

@Composable
private fun CategoryRow(
    category: DemoCategory,
    onClick: () -> Unit,
) {
    ThemeCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                ThemeText(
                    text = category.title,
                    style = AnkioTheme.textStyles.body1,
                    color = AnkioTheme.colorScheme.onSurface,
                )
                ThemeText(
                    text = category.summary,
                    style = AnkioTheme.textStyles.footnote1,
                    color = AnkioTheme.colorScheme.onSurfaceVariant,
                )
            }
            ThemeIcon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = AnkioTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
