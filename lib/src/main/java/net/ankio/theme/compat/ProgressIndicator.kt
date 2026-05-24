/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-3.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package net.ankio.theme.compat

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import top.yukonga.miuix.kmp.basic.CircularProgressIndicator as MiuixCircularProgressIndicator
import top.yukonga.miuix.kmp.basic.LinearProgressIndicator as MiuixLinearProgressIndicator

/** 线性进度条：Miuix 用 MiuixLinearProgressIndicator，Material 用 LinearProgressIndicator */
@Composable
fun ThemeLinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float? = null
) {
    when (LocalUiMode.current) {
        UiMode.Material -> {
            if (progress == null) {
                LinearProgressIndicator(modifier = modifier)
            } else {
                LinearProgressIndicator(
                    modifier = modifier,
                    progress = { progress },
                )
            }
        }

        UiMode.Miuix -> MiuixLinearProgressIndicator(modifier = modifier, progress = progress)
    }
}

/** 圆形进度条：Miuix 用 MiuixCircularProgressIndicator，Material 用 CircularProgressIndicator */
@Composable
fun ThemeCircularProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float? = null
) {
    when (LocalUiMode.current) {
        UiMode.Material -> {
            if (progress == null) {
                CircularProgressIndicator(modifier = modifier)
            } else {
                CircularProgressIndicator(
                    modifier = modifier,
                    progress = { progress },
                )
            }
        }

        UiMode.Miuix -> MiuixCircularProgressIndicator(modifier = modifier, progress = progress)
    }
}
