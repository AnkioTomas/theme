/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import net.ankio.theme.compat.ThemeCircularProgressIndicator
import net.ankio.theme.compat.ThemeLinearProgressIndicator
import net.ankio.theme.compat.ThemeSmallTitle

/** 进度条 + SmallTitle */
@Composable
internal fun DisplaySection() {
    SectionCard(title = "进度与小标题 / Progress & SmallTitle") {

        Caption("ThemeLinearProgressIndicator · 确定进度 + 不确定")
        var progress by remember { mutableFloatStateOf(0f) }
        LaunchedEffect(Unit) {
            while (true) {
                delay(40L)
                progress = (progress + 0.01f).let { if (it > 1f) 0f else it }
            }
        }
        ThemeLinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = progress,
        )
        ThemeLinearProgressIndicator(modifier = Modifier.fillMaxWidth(), progress = null)

        Caption("ThemeCircularProgressIndicator · 确定进度 + 不确定")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ThemeCircularProgressIndicator(progress = progress)
            ThemeCircularProgressIndicator(progress = null)
        }

        Caption("ThemeSmallTitle")
        ThemeSmallTitle(
            text = "我是一个分组小标题",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
