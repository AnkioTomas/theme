/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 *
 * Debug-only preview mirror. Not packaged in release.
 */

package net.ankio.theme.preview.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import net.ankio.theme.AnkioTheme
import net.ankio.theme.compat.ThemeIcon

@Composable
internal fun SettingFieldStartIcon(imageVector: ImageVector) {
    ThemeIcon(
        imageVector = imageVector,
        contentDescription = null,
        tint = AnkioTheme.colorScheme.primary,
    )
}
