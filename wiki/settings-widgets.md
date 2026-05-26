# 设置页组件

包名：`net.ankio.theme.settings`

用于构建分组设置列表，与 `UiSettingsScreen` 风格一致。

## SettingCardPosition

| 值 | 圆角 | 垂直间距 |
|----|------|----------|
| `First` | 上圆角 | 顶 0 / 底 3dp |
| `Middle` | 无圆角 | 上下 3dp |
| `Last` | 下圆角 | 顶 3 / 底 0 |
| `Single` | 四角圆角 | 上下 3dp |

连续多项时使用 `First` → `Middle` → `Last` 拼接为一张「分组卡片」。

## SettingCard

通用设置行：左图标 + 标题 + 副标题 + 右侧控件。

```kotlin
SettingCard(
    icon = { Icon(Icons.Filled.Tune, null, tint = AnkioTheme.colorScheme.primary) },
    title = "示例",
    subtitle = "说明文字",
    onClick = { },           // 整行点击，null 不可点
    trailing = { ThemeSwitch(...) },
    trailingValue = "当前值", // 下拉展示用，与 trailing 二选一
    position = SettingCardPosition.Single,
)
```

## ThemeSettingSwitch

```kotlin
ThemeSettingSwitch(
    title = "深色模式",
    summary = "切换应用深色主题",
    checked = checked,
    onCheckedChange = { checked = it },
    startAction = { Icon(...) },
    position = SettingCardPosition.Middle,
)
```

## ThemeSettingDropdown

```kotlin
ThemeSettingDropdown(
    items = listOf("默认", "蓝色", "绿色"),
    selectedIndex = index,
    onSelectedIndexChange = { index = it },
    title = "主题色",
    summary = "选择种子色",
    startAction = { Icon(...) },
    position = SettingCardPosition.Last,
)
```

## ThemeSettingTextField

图标与标题在 **输入框内部**（`leadingIcon` + `label`），副标题在框下。

```kotlin
ThemeSettingTextField(
    value = text,
    onValueChange = { text = it },
    title = "邮箱",              // → TextField label
    summary = "用于通知",        // → supportingText
    placeholder = "name@example.com",
    startAction = { Icon(...) }, // → leadingIcon
    position = SettingCardPosition.Single,
    singleLine = true,
)
```

## ThemeSettingClick

跳转类设置项，整行可点。

```kotlin
ThemeSettingClick(
    title = "关于",
    summary = "版本 1.0",
    onClick = { },
    startAction = { Icon(...) },
)
```

## ThemeSectionHeader

分组标题（主题色 footnote 样式）。

```kotlin
ThemeSectionHeader(text = "主题风格")
```

## UiSettingsScreen

内置完整主题设置页。

```kotlin
UiSettingsScreen(
    options = UiSettingsOptions(), // 可裁剪选项与文案
    modifier = Modifier.fillMaxSize(),
    scrollEnabled = true,        // false 时由外层滚动
    onThemeChanged = { recreateForThemeChange() },
)
```

### UiSettingsOptions

| 字段 | 默认 |
|------|------|
| `uiModeEntries` | lib 内置 |
| `colorModeEntries` | lib 内置 |
| `themeColorEntries` | `emptyList()` → 全部预设色 |

「主题色」分组仅在 Material 或 Monet 模式下显示。

## Demo

`app` → **设置项组件**；主界面 **设置** Tab
