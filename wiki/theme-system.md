# 主题体系

---

## ThemeSettings

持久化配置；**必须**在 `Application.onCreate` 调用 `ThemeSettings.init(context)`。

| API | 说明 |
|-----|------|
| `uiMode` | `"miuix"` / `"material"` |
| `colorMode` | `0`–`6`，见 ColorMode |
| `followSystemAccent` | Material You 动态色（选具体主题色时会自动关闭） |
| `themeColor` | 种子色 key，如 `MATERIAL_BLUE`（非 ARGB） |
| `displayPercentage` | 全局显示比例 **85–130**，100 为默认 |
| `keyColor` | 只读：0 = 系统动态色，否则 ARGB |
| `isDark` | 只读：当前是否深色 |
| `shouldUseDarkTheme(context)` | 按 colorMode 判断 |
| `themedContext(context)` | View / Dialog 膨胀用主题 Context |
| `getAppSettings()` | 供 `AutoTheme(appSettings = …)` |

### ColorMode

| 值 | 枚举 | 说明 |
|----|------|------|
| 0 | `SYSTEM` | 跟随系统（Miuix 内置调色板） |
| 1 | `LIGHT` | 浅色 |
| 2 | `DARK` | 深色 |
| 3 | `MONET_SYSTEM` | Monet 跟随系统 |
| 4 | `MONET_LIGHT` | Monet 浅色 |
| 5 | `MONET_DARK` | Monet 深色 |
| 6 | `DARK_AMOLED` | AMOLED 深色（映射 Monet Dark） |

**Miuix**：仅 `MONET_*` 使用 `keyColor`；`SYSTEM`/`LIGHT`/`DARK` 用内置米家调色板。  
**Material**：`keyColor == 0` 且 API 31+ 用系统动态色，否则种子色生成。

---

## AutoTheme

```kotlin
@Composable
fun AutoTheme(
    appSettings: AppSettings? = null,
    uiMode: UiMode = LocalUiMode.current,
    content: @Composable () -> Unit,
)
```

| 行为 | 说明 |
|------|------|
| Miuix 分支 | `MiuixTheme` → 注入 `LocalContentColor = onSurface` → `MaterialBlock` |
| Material 分支 | 仅 `MaterialTheme` |
| 密度 | `LocalDensity` × `displayPercentage / 100` |
| Locals | `LocalColorMode`、`LocalAppExtraColors` |
| 系统栏 | Activity 下同步状态栏/导航栏图标深浅 |

---

## AnkioTheme

业务统一入口，勿直接依赖 `MaterialTheme.colorScheme` / `MiuixTheme.colorScheme`。

```kotlin
AnkioTheme.colorScheme.primary
AnkioTheme.colorScheme.onSurfaceVariant
AnkioTheme.colorScheme.semantic.error.text
AnkioTheme.textStyles.body1
```

### ThemeColorScheme

Material `ColorScheme` 与 Miuix `Colors` 的对齐封装；详见 `ThemeColorScheme.kt`。

| Miuix 映射说明 | |
|----------------|--|
| `onSurfaceVariant` | ← `onSurfaceContainerVariant` |
| `tertiary` | ← `tertiaryContainer`（近似） |
| `surfaceContainerLow` | lerp 生成 |

### ThemeTextStyles

`main`, `paragraph`, `body1`, `body2`, `button`, `footnote1`, `footnote2`, `headline1`, `headline2`, `subtitle`, `title1`–`title4`

### AppExtraColors（语义色）

| 类型 | 字段 |
|------|------|
| `info` / `warning` / `error` / `success` / `debug` | 各含 `text`、`bg` |

```kotlin
Box(Modifier.background(AnkioTheme.colorScheme.semantic.success.bg)) {
    ThemeText(
        text = "成功",
        color = AnkioTheme.colorScheme.semantic.success.text,
        style = AnkioTheme.textStyles.body1,
    )
}
```

访问：`AutoThemeTokens.extraColors`（须在 `AutoTheme` 内）。

---

## CompositionLocal

| Local | 说明 |
|-------|------|
| `LocalUiMode` | `UiMode.Miuix` / `Material` |
| `LocalColorMode` | 当前 `ColorMode` |
| `isInDarkTheme()` | 是否深色 |

---

## 形状 MaterialTheme.shapes

| 档位 | 圆角 |
|------|------|
| extraSmall / small | 10dp |
| medium | 18dp |
| large / extraLarge | 24dp |

---

## 预设主题色

`themeKeyOptions` 列出全部 key；`ThemeSettings.themeColor` 存 key 字符串。

---

## BaseComposeActivity

```kotlin
class MainActivity : BaseComposeActivity() {
    @Composable
    override fun Content() { MyScreen() }
}
```

自动：`enableEdgeToEdge`、`AppCompatDelegate` 夜模式、`LocalUiMode` + `AutoTheme` + 根 `ThemeSurface`。

主题变更：`recreateForThemeChange()`
