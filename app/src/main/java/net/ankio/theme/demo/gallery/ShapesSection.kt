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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme

/**
 * 形状展示：直接读取 MaterialTheme.shapes，由 lib 的 [appShapes] 提供。
 * 5 级（extraSmall / small / medium / large / extraLarge）对应 lib 中的圆角方案。
 */
@Composable
internal fun ShapesSection() {
    SectionCard(title = "形状 / Shapes") {
        Caption("MaterialTheme.shapes（来自 appShapes()）")
        val s = MaterialTheme.shapes
        ShapeRow(
            entries = listOf(
                "extraSmall (10dp)" to s.extraSmall,
                "small (10dp)" to s.small,
                "medium (18dp)" to s.medium,
                "large (24dp)" to s.large,
                "extraLarge (24dp)" to s.extraLarge,
            ),
        )
    }
}

@Composable
private fun ShapeRow(entries: List<Pair<String, Shape>>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        entries.forEach { (name, shape) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 80.dp, height = 40.dp)
                        .clip(shape)
                        .background(AnkioTheme.colorScheme.primaryContainer),
                )
                Caption(name)
            }
        }
    }
}
