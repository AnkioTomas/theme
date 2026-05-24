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

/**
 * 组件兼容层统一入口。
 * 所有 Theme* 组件根据 LocalUiMode 在 Miuix 与 Material 间切换。
 *
 * 已实现：Button, Surface, Card, Text, Icon, IconButton, Divider,
 * Switch, Checkbox, Slider, ProgressIndicator, FloatingActionButton,
 * TopAppBar, NavigationBar, NavigationRail, TabRow, SmallTitle, PullToRefresh,
 * TextField, SearchBar, ButtonGroup, ColorPalette, ColorPicker, FloatingToolbar, NumberPicker,
 * SuperListPopup, ThemeListPopupColumn, SuperDropdown, SuperSpinner。
 *
 * 注：BasicComponent 在 Miuix 库中不存在，未实现。
 */
