/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.TextFieldStyle
import net.ankio.theme.compat.ThemeCheckbox
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemeNumberPicker
import net.ankio.theme.compat.ThemeSlider
import net.ankio.theme.compat.ThemeSwitch
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.compat.ThemeTextField

/** 表单类组件展示：Switch/Checkbox/Slider/TextField/NumberPicker */
@Composable
internal fun FormsSection() {
    SectionCard(title = "表单 / Forms") {

        Caption("ThemeSwitch / ThemeCheckbox")
        var switchOn by remember { mutableStateOf(true) }
        var checkboxOn by remember { mutableStateOf(false) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ThemeSwitch(checked = switchOn, onCheckedChange = { switchOn = it })
            ThemeCheckbox(checked = checkboxOn, onCheckedChange = { checkboxOn = it })
            ThemeSwitch(checked = false, onCheckedChange = {}, enabled = false)
        }

        Caption("ThemeSlider")
        var sliderValue by remember { mutableFloatStateOf(0.4f) }
        ThemeSlider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            modifier = Modifier.fillMaxWidth(),
            valueRange = 0f..1f,
            steps = 4,
        )

        Caption("ThemeTextField · Outlined")
        var outlinedText by remember { mutableStateOf("") }
        ThemeTextField(
            value = outlinedText,
            onValueChange = { outlinedText = it },
            modifier = Modifier.fillMaxWidth(),
            style = TextFieldStyle.Outlined,
            label = "邮箱",
            placeholder = "请输入邮箱",
            leadingIcon = {
                ThemeIcon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = AnkioTheme.colorScheme.onSurfaceVariant,
                )
            },
            supportingText = {
                Text(
                    text = "用于登录验证",
                    color = AnkioTheme.colorScheme.onSurfaceVariant,
                )
            },
        )

        Caption("ThemeTextField · Filled · 错误态")
        var filledText by remember { mutableStateOf("12") }
        ThemeTextField(
            value = filledText,
            onValueChange = { filledText = it },
            modifier = Modifier.fillMaxWidth(),
            style = TextFieldStyle.Filled,
            label = "年龄",
            isError = filledText.toIntOrNull() == null || filledText.toInt() < 18,
            trailingIcon = {
                ThemeIconButton(onClick = { filledText = "" }) {
                    ThemeIcon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = null,
                        tint = AnkioTheme.colorScheme.onSurfaceVariant,
                    )
                }
            },
        )

        Caption("ThemeNumberPicker")
        var pickerValue by remember { mutableIntStateOf(5) }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            ThemeNumberPicker(
                value = pickerValue,
                onValueChange = { pickerValue = it },
                range = 0..20,
                visibleItemCount = 3,
            )
        }
    }
}
