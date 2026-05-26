/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.demo.ui.DemoPageHeader

@Composable
fun CategoryDetailScreen(
    category: DemoCategory,
    listState: LazyListState,
    nestedScrollModifier: Modifier,
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
        item(key = "summary") {
            DemoPageHeader(
                title = "示例",
                description = category.summary,
            )
        }
        item(key = "content") {
            category.Content()
        }
        item(key = "footer") {
            Spacer(Modifier.height(24.dp))
        }
    }
}
