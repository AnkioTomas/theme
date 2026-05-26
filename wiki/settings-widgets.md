# 设置页组件

包名：`net.ankio.theme.settings`

用于构建 MIUI/Material 风格设置列表，与 `UiSettingsScreen` 视觉一致。底层依赖 `ThemeCard`、`ThemeTextField`、`ThemeSuperDropdown` 等 compat 组件。

---

## SettingCardPosition

连续多行拼成「一组卡片」时的位置。

| 值 | 圆角 | 垂直 padding |
|----|------|----------------|
| `First` | 仅上侧 12dp | 顶 0 / 底 3dp |
| `Middle` | 无圆角 | 上下 3dp |
| `Last` | 仅下侧 12dp | 顶 3 / 底 0 |
| `Single` | 四角 12dp | 上下 3dp |

```kotlin
SettingCardPosition.First.toShape()         // RoundedCornerShape
SettingCardPosition.Middle.toVerticalPadding()
```

---

## SettingCard

通用设置行：左图标 + 标题 + 副标题 + 右侧。

```kotlin
SettingCard(
    icon = {
        ThemeIcon(
            imageVector = Icons.Filled.Tune,
            contentDescription = null,
            tint = AnkioTheme.colorScheme.primary,
        )
    },
    title = "示例",
    subtitle = "说明文字",          // 可选
    onClick = { },                  // null = 不可点
    trailing = { ThemeSwitch(...) }, // 与 trailingValue 二选一
    trailingValue = "当前值",        // 展示值 + 展开箭头
    position = SettingCardPosition.Single,
    modifier = Modifier.fillMaxWidth(),
)
```

| 参数 | 说明 |
|------|------|
| `icon` | 左侧 40dp 槽内 Composable |
| `trailing` | 右侧控件（Switch、自定义等） |
| `trailingValue` | 只读展示 + `ExpandMore` 图标 |

标题 `title4`，副标题 `footnote1`。

---

## ThemeSectionHeader

分组标题（主题色 footnote 样式）。

```kotlin
ThemeSectionHeader(text = "主题风格")
```

等价于内部 `SectionHeader`：`footnote1` + `primary` 色。

---

## ThemeSettingClick

可点击跳转类设置项（封装 `SettingCard`）。

```kotlin
ThemeSettingClick(
    title = "关于",
    summary = "版本 1.0",
    onClick = { navController.navigate(...) },
    startAction = { /* 左侧图标 */ },
    position = SettingCardPosition.Middle,
)
```

---

## ThemeSettingSwitch

带开关的设置项；**点击整行**也会切换。

```kotlin
ThemeSettingSwitch(
    title = "深色模式",
    summary = "切换应用深色主题",
    checked = checked,
    onCheckedChange = { checked = it },
    startAction = { Icon(...) },
    position = SettingCardPosition.Single,
)
```

右侧为 `ThemeSwitch`。

---

## ThemeSettingDropdown

下拉选择设置项（封装 `ThemeSuperDropdown` + 分组圆角）。

```kotlin
ThemeSettingDropdown(
    items = listOf("默认", "蓝色", "绿色"),
    selectedIndex = index,
    onSelectedIndexChange = { index = it },
    title = "主题色",
    summary = "选择种子色",
    startAction = { Icon(...) },
    position = SettingCardPosition.Last,
    enabled = true,
)
```

---

## ThemeSettingTextField

输入类设置项：**图标与标题在输入框内部**。

```kotlin
ThemeSettingTextField(
    value = text,
    onValueChange = { text = it },
    title = "邮箱",                    // → TextField label
    summary = "用于通知",              // → supportingText
    placeholder = "name@example.com",
    startAction = { Icon(...) },       // → leadingIcon
    position = SettingCardPosition.Single,
    singleLine = true,
    maxLines = 1,
)
```

内部：`SettingCard` + `ThemeTextField(Filled)`。

---

## UiSettingsScreen

内置完整主题设置页（UI 模式、颜色模式、显示比例、主题色等）。

```kotlin
UiSettingsScreen(
    options = UiSettingsOptions(),
    modifier = Modifier.fillMaxSize(),
    scrollEnabled = true,
    onThemeChanged = { recreateForThemeChange() },
)
```

### UiSettingsOptions

| 字段 | 默认 | 说明 |
|------|------|------|
| `uiModeEntries` | lib 内置 | Miuix / Material 文案 |
| `colorModeEntries` | lib 内置 | 7 种 ColorMode |
| `themeColorEntries` | `emptyList()` | 空 = 全部 `themeKeyOptions` |

### 显示逻辑

- **主题色**区块：仅 `colorMode.isMonet` 或 `uiMode == Material` 时显示
- Miuix 非 Monet 模式隐藏种子色（内置调色板不使用 `keyColor`）
- 修改 `themeColor` 时自动关闭 `followSystemAccent`

### 内置子控件（internal）

| 组件 | 用途 |
|------|------|
| `UiModeSelector` | Miuix / Material |
| `ColorModeSelector` | 颜色模式 |
| `DisplayPercentageSlider` | 显示比例 85–130% |
| `FollowSystemAccentSwitch` | 跟随系统动态色 |
| `ThemeColorSelector` | 预设主题色（`ThemeSuperSpinner` + 色块图标） |

---

## Demo

`app` → **设置项组件**；主界面 **设置** Tab（完整 `UiSettingsScreen`）
