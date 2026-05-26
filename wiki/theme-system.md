# 主题体系

## ThemeSettings

持久化配置，**必须**在 `Application.onCreate` 调用 `ThemeSettings.init()`。

| API | 说明 |
|-----|------|
| `uiMode` | `"miuix"` / `"material"` |
| `colorMode` | 0–6，见下表 |
| `followSystemAccent` | Material You 动态色 |
| `themeColor` | 种子色 key，如 `MATERIAL_BLUE` |
| `displayPercentage` | 全局显示比例 85–130 |
| `keyColor` | 只读，0=动态色，否则 ARGB |
| `isDark` | 只读 |
| `shouldUseDarkTheme(context)` | 按 colorMode 判断 |
| `themedContext(context)` | View / Dialog 膨胀用 |
| `getAppSettings()` | `AutoTheme(appSettings = …)` |

### ColorMode

| 值 | 枚举 | 说明 |
|----|------|------|
| 0 | SYSTEM | 跟随系统（MIUI 调色板） |
| 1 | LIGHT | 浅色 |
| 2 | DARK | 深色 |
| 3 | MONET_SYSTEM | 跟随系统 Monet |
| 4 | MONET_LIGHT | Monet 浅色 |
| 5 | MONET_DARK | Monet 深色 |
| 6 | DARK_AMOLED | AMOLED 深色 |

Miuix 下仅 `MONET_*` 使用 `keyColor`；`SYSTEM`/`LIGHT`/`DARK` 用内置调色板。

## AutoTheme

```kotlin
@Composable
fun AutoTheme(
    appSettings: AppSettings? = null,  // null 则从 ThemeSettings 读
    uiMode: UiMode = LocalUiMode.current,
    content: @Composable () -> Unit,
)
```

- 注入 `LocalColorMode`、`LocalAppExtraColors`
- 按 `displayPercentage` 缩放 `LocalDensity`
- Miuix → `MiuixTheme`；Material → `MaterialTheme`

## AnkioTheme

```kotlin
AnkioTheme.colorScheme.primary
AnkioTheme.colorScheme.semantic.error.text
AnkioTheme.textStyles.body1
```

### 排版样式

`main`, `paragraph`, `body1`, `body2`, `button`, `footnote1`, `footnote2`, `headline1`, `headline2`, `subtitle`, `title1`–`title4`

### 语义色 AppExtraColors

| 类型 | 字段 |
|------|------|
| info / warning / error / success / debug | 各含 `text`、`bg` |

```kotlin
Box(Modifier.background(AnkioTheme.colorScheme.semantic.success.bg)) {
    ThemeText("成功", color = AnkioTheme.colorScheme.semantic.success.text)
}
```

## CompositionLocal

| Local | 说明 |
|-------|------|
| `LocalUiMode` | Miuix / Material |
| `LocalColorMode` | 当前 ColorMode |
| `isInDarkTheme()` | 是否深色 |

## 形状

`MaterialTheme.shapes`：`extraSmall/small=10dp`，`medium=18dp`，`large/extraLarge=24dp`。

## 预设主题色

`themeKeyOptions` 列出全部 key；`ThemeSettings.themeColor` 存 key 非 ARGB。
