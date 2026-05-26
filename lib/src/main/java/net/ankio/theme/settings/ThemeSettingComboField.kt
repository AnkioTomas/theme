/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 * Licensed under the Apache License, Version 3.0 (the "License");
 */

package net.ankio.theme.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import net.ankio.theme.LocalUiMode
import net.ankio.theme.UiMode
import net.ankio.theme.compat.AnchorEndPopupPositionProvider

/**
 * 设置项下拉输入框：外观同 [ThemeSettingTextField]，只读 + 展开列表。
 */
@Composable
fun ThemeSettingComboField(
    items: List<String>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    title: String,
    startAction: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    summary: String? = null,
    placeholder: String = "",
    fieldEndAction: @Composable (() -> Unit)? = null,
    endAction: @Composable (() -> Unit)? = null,
    position: SettingCardPosition = SettingCardPosition.Single,
    enabled: Boolean = true,
) {
    val actualEnabled = enabled && items.isNotEmpty()
    val fieldValue = items.getOrNull(selectedIndex).orEmpty()

    SettingFieldCard(position = position, modifier = modifier, endAction = endAction) {
        when (LocalUiMode.current) {
            UiMode.Material -> MaterialSettingComboDropdown(
                items = items,
                onSelectedIndexChange = onSelectedIndexChange,
                title = title,
                summary = summary,
                placeholder = placeholder,
                startAction = startAction,
                fieldEndAction = fieldEndAction,
                fieldValue = fieldValue,
                actualEnabled = actualEnabled,
            )

            UiMode.Miuix -> MiuixSettingComboDropdown(
                items = items,
                onSelectedIndexChange = onSelectedIndexChange,
                title = title,
                summary = summary,
                placeholder = placeholder,
                startAction = startAction,
                fieldEndAction = fieldEndAction,
                fieldValue = fieldValue,
                actualEnabled = actualEnabled,
            )
        }
    }
}

@Composable
private fun SettingComboReadOnlyField(
    fieldValue: String,
    title: String,
    summary: String?,
    placeholder: String,
    startAction: @Composable () -> Unit,
    fieldEndAction: @Composable (() -> Unit)?,
    actualEnabled: Boolean,
    expanded: Boolean,
    onDropdownToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingFilledTextField(
        value = fieldValue,
        onValueChange = {},
        title = title,
        startAction = startAction,
        modifier = modifier,
        summary = summary,
        placeholder = placeholder,
        enabled = actualEnabled,
        readOnly = true,
        trailingIcon = {
            SettingFieldInnerTrailing(
                fieldEndAction = fieldEndAction,
                showDropdownToggle = true,
                expanded = expanded,
                onDropdownToggle = onDropdownToggle,
                enabled = actualEnabled,
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MaterialSettingComboDropdown(
    items: List<String>,
    onSelectedIndexChange: (Int) -> Unit,
    title: String,
    summary: String?,
    placeholder: String,
    startAction: @Composable () -> Unit,
    fieldEndAction: @Composable (() -> Unit)?,
    fieldValue: String,
    actualEnabled: Boolean,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { if (actualEnabled) expanded = it },
        modifier = Modifier.fillMaxWidth(),
    ) {
        SettingComboReadOnlyField(
            fieldValue = fieldValue,
            title = title,
            summary = summary,
            placeholder = placeholder,
            startAction = startAction,
            fieldEndAction = fieldEndAction,
            actualEnabled = actualEnabled,
            expanded = expanded,
            onDropdownToggle = { expanded = !expanded },
            modifier = Modifier.menuAnchor(
                type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                enabled = actualEnabled,
            ),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEachIndexed { index, label ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        onSelectedIndexChange(index)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
private fun MiuixSettingComboDropdown(
    items: List<String>,
    onSelectedIndexChange: (Int) -> Unit,
    title: String,
    summary: String?,
    placeholder: String,
    startAction: @Composable () -> Unit,
    fieldEndAction: @Composable (() -> Unit)?,
    fieldValue: String,
    actualEnabled: Boolean,
) {
    var expanded by remember { mutableStateOf(false) }
    val verticalSpacingPx = with(LocalDensity.current) { 4.dp.roundToPx() }
    val positionProvider = remember(verticalSpacingPx) {
        AnchorEndPopupPositionProvider(verticalSpacingPx)
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        SettingComboReadOnlyField(
            fieldValue = fieldValue,
            title = title,
            summary = summary,
            placeholder = placeholder,
            startAction = startAction,
            fieldEndAction = fieldEndAction,
            actualEnabled = actualEnabled,
            expanded = expanded,
            onDropdownToggle = { if (actualEnabled) expanded = !expanded },
            modifier = Modifier.clickable(enabled = actualEnabled) { expanded = !expanded },
        )
        SettingItemsPopup(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            items = items,
            onItemSelected = { index ->
                onSelectedIndexChange(index)
                expanded = false
            },
            positionProvider = positionProvider,
        )
    }
}
