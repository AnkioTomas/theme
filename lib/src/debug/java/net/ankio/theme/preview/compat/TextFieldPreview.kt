/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.compat

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import net.ankio.theme.AnkioTheme
import net.ankio.theme.PreviewAll
import net.ankio.theme.ThemePreviewConfig
import net.ankio.theme.ThemePreviewParameterProvider
import net.ankio.theme.compat.TextFieldStyle
import net.ankio.theme.compat.ThemeIcon
import net.ankio.theme.compat.ThemeIconButton
import net.ankio.theme.compat.ThemeText
import net.ankio.theme.compat.ThemeTextField
import net.ankio.theme.preview.PreviewHost

@PreviewAll
@Composable
private fun ThemeTextFieldOutlinedPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    var text by remember { mutableStateOf("Hello") }
    PreviewHost(config) {
        ThemeTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth(),
            style = TextFieldStyle.Outlined,
            label = "ThemeTextField",
            placeholder = "请输入",
            leadingIcon = {
                ThemeIcon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = AnkioTheme.colorScheme.onSurfaceVariant,
                )
            },
            trailingIcon = {
                ThemeIconButton(onClick = { text = "" }) {
                    ThemeIcon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "清除",
                        tint = AnkioTheme.colorScheme.onSurfaceVariant,
                    )
                }
            },
            supportingText = {
                ThemeText(
                    text = "leadingIcon + trailingIcon",
                    style = AnkioTheme.textStyles.footnote1,
                    color = AnkioTheme.colorScheme.onSurfaceVariant,
                )
            },
        )
    }
}

@PreviewAll
@Composable
private fun ThemeTextFieldFilledPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    var text by remember { mutableStateOf("") }
    PreviewHost(config) {
        ThemeTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth(),
            style = TextFieldStyle.Filled,
            label = "邮箱",
            placeholder = "name@example.com",
            leadingIcon = {
                ThemeIcon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = AnkioTheme.colorScheme.onSurfaceVariant,
                )
            },
        )
    }
}
