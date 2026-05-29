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

package net.ankio.theme.settings

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeCard
import net.ankio.theme.compat.ThemeText

/** 卡片在分组中的位置，用于控制圆角与上下间距 */
enum class SettingCardPosition {
    First, Middle, Last, Single
}

/** 供 ThemeSuperDropdown / [ThemeSettingDropdown] 等复用分组圆角 */
fun SettingCardPosition.toShape(): Shape = when (this) {
    SettingCardPosition.First -> RoundedCornerShape(
        topStart = 12.dp,
        topEnd = 12.dp,
        bottomStart = 4.dp,
        bottomEnd = 4.dp
    )

    SettingCardPosition.Middle -> RoundedCornerShape(4.dp)
    SettingCardPosition.Last -> RoundedCornerShape(
        topStart = 4.dp,
        topEnd = 4.dp,
        bottomStart = 12.dp,
        bottomEnd = 12.dp
    )

    SettingCardPosition.Single -> RoundedCornerShape(12.dp)
}

/**
 * 分组内卡片的上下间距：First 顶部贴上一项、Last 底部贴下一项，Middle 两侧都留 3dp，
 * Single 上下都留 3dp。所有 Selector 共用此规则避免出现各自 hardcode padding。
 */
fun SettingCardPosition.toVerticalPadding(): Pair<Dp, Dp> = when (this) {
    SettingCardPosition.First -> 0.dp to 2.dp
    SettingCardPosition.Middle -> 2.dp to 2.dp
    SettingCardPosition.Last -> 2.dp to 0.dp
    SettingCardPosition.Single -> 2.dp to 2.dp
}

/**
 * 通用设置项卡片：左侧图标、标题、副标题、右侧 Switch/下拉值。
 * 供 UI 引擎、主题模式、动态颜色等设置项复用。
 *
 * @param icon 左侧 40dp 图标
 * @param title 主标题
 * @param modifier 可传入 menuAnchor 等，用于下拉锚点
 * @param subtitle 副标题（说明文字，标题下方）
 * @param onClick 点击回调，null 时卡片不可点击
 * @param trailing 右侧控件（如 Switch），与 trailingValue 二选一
 * @param trailingValue 下拉时右侧显示的值，与 trailing 二选一
 * @param position 分组位置，Single 时独立圆角+间距，First/Middle/Last 时连在一起
 */
@Composable
fun SettingCard(
    icon: @Composable () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    trailingValue: String? = null,
    position: SettingCardPosition = SettingCardPosition.Single,
) {
    val (topPad, bottomPad) = position.toVerticalPadding()
    ThemeCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = topPad, bottom = bottomPad),
        shape = position.toShape(),
        onClick = onClick,
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.size(40.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    icon()
                }
                Column(Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)) {
                    ThemeText(
                        text = title,
                        style = AnkioTheme.textStyles.title4,
                        color = AnkioTheme.colorScheme.onSurface,
                    )
                    if (subtitle != null) {
                        Spacer(Modifier.height(2.dp))
                        ThemeText(
                            text = subtitle,
                            style = AnkioTheme.textStyles.footnote1,
                            color = AnkioTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                when {
                    trailing != null -> trailing()
                    trailingValue != null -> Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ThemeText(
                            text = trailingValue,
                            style = AnkioTheme.textStyles.footnote1,
                            color = AnkioTheme.colorScheme.onSurfaceVariant,
                        )
                        Icon(
                            imageVector = Icons.Filled.ExpandMore,
                            contentDescription = null,
                            tint = AnkioTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }
            }
        }
    }
}
