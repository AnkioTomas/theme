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

package net.ankio.theme

import top.yukonga.miuix.kmp.basic.ColorSpace as MiuixColorSpace

/**
 * 颜色空间类型别名，用于 [ThemeColorPicker]。
 * 实际类型来自 Miuix，支持 HSV、OKHSV、OKLAB、OKLCH。
 */
typealias ColorSpace = MiuixColorSpace
