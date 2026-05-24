/*
 * Copyright (C) 2025 ankio(ankio@ankio.net)
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

package net.ankio.theme

import androidx.compose.runtime.staticCompositionLocalOf

/**
 * UI 风格枚举，对齐 KernelSU UiMode。
 * Miuix：澎湃风格；Material：Material Design 3。
 */
enum class UiMode(val value: String) {
    Miuix("miuix"),
    Material("material");

    companion object {
        fun fromValue(value: String): UiMode = entries.find { it.value == value } ?: Miuix
    }
}

/** CompositionLocal：当前 UI 风格 */
val LocalUiMode = staticCompositionLocalOf { UiMode.Miuix }
