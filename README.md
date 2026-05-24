# Ankio Theme

Compose 主题与组件兼容库。在 **Miuix** 与 **Material 3** 两套 UI 引擎之间提供统一 API，业务层只写一套 `Theme*` 组件，用户可在设置中切换风格。

[![JitPack](https://jitpack.io/v/AnkioTomas/theme.svg)](https://jitpack.io/#AnkioTomas/theme)

---

## 环境要求

| 项 | 值 |
|---|---|
| minSdk | 30 |
| Java | 17 |
| Compose | BOM 2025.08+ |
| 依赖 | Miuix KMP 0.8.7、Material 3 |

---

## 接入

### 1. 添加 JitPack 仓库

`settings.gradle.kts`：

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### 2. 添加依赖

```kotlin
dependencies {
    implementation("com.github.AnkioTomas.theme:lib:VERSION")
}
```

`VERSION` 为 GitHub Release 标签（如 `1.0.0`）或 commit hash。

### 3. 初始化

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeSettings.init(this)
        ThemeToast.init(this) // 若使用 ThemeToast
    }
}
```

`ThemeToast` 依赖悬浮窗权限（lib 已声明 `SYSTEM_ALERT_WINDOW`），未授权时自动降级为系统 Toast。

---

## 快速开始

### 方式 A：继承 BaseComposeActivity（推荐）

自动处理 Edge-to-Edge、夜模式、`AutoTheme`、`LocalUiMode`：

```kotlin
class MainActivity : BaseComposeActivity() {
    @Composable
    override fun Content() {
        // 已包裹 AutoTheme + ThemeSurface
        MyScreen()
    }
}
```

主题变更后重建 Activity：

```kotlin
recreateForThemeChange()
```

### 方式 B：手动包裹主题

```kotlin
setContent {
    CompositionLocalProvider(
        LocalUiMode provides UiMode.fromValue(ThemeSettings.uiMode),
    ) {
        AutoTheme {
            ThemeSurface(
                modifier = Modifier.fillMaxSize(),
                color = AnkioTheme.colorScheme.surface,
            ) {
                MyScreen()
            }
        }
    }
}
```

---

## 主题体系

### ThemeSettings

持久化配置，需在 `Application.onCreate` 调用 `ThemeSettings.init()`。

| 属性 | 说明 |
|------|------|
| `uiMode` | UI 引擎：`"miuix"` / `"material"` |
| `colorMode` | 颜色模式 0–6，见 [ColorMode](#colormode) |
| `followSystemAccent` | 跟随系统动态色（Material You） |
| `themeColor` | 主题色 key，如 `MATERIAL_BLUE`；设置时自动关闭 `followSystemAccent` |
| `displayPercentage` | 全局显示比例 85–130，100 为默认 |
| `navigationBarFloating` | 底部导航栏是否悬浮样式 |
| `keyColor` | 只读，0 = 跟随系统动态色，否则为种子色 ARGB |
| `isDark` | 只读，当前是否深色主题 |
| `shouldUseDarkTheme(context)` | 根据 colorMode 判断 |
| `colorModeToNightMode()` | 转 AppCompatDelegate 夜模式常量 |
| `themedContext(context)` | 供 View 膨胀使用的主题化 Context |
| `getAppSettings()` | 供 `AutoTheme` 使用的 `AppSettings` |

### ColorMode

| 值 | 枚举 | 说明 |
|----|------|------|
| 0 | `SYSTEM` | 跟随系统（MIUI 默认调色板） |
| 1 | `LIGHT` | 浅色（MIUI） |
| 2 | `DARK` | 深色（MIUI） |
| 3 | `MONET_SYSTEM` | 跟随系统（Monet 动态色） |
| 4 | `MONET_LIGHT` | 浅色（Monet） |
| 5 | `MONET_DARK` | 深色（Monet） |
| 6 | `DARK_AMOLED` | 深色 AMOLED |

Miuix 模式下 `SYSTEM`/`LIGHT`/`DARK` 使用 Miuix 内置调色板；`MONET_*` 才应用 `keyColor`。

### UiMode

```kotlin
enum class UiMode { Miuix, Material }
```

### AnkioTheme

统一颜色与字体入口，自动适配当前 UI 引擎：

```kotlin
AnkioTheme.colorScheme.primary
AnkioTheme.colorScheme.semantic.error.text   // 语义色
AnkioTheme.textStyles.body1
AnkioTheme.textStyles.title4
```

### AutoThemeTokens

语义扩展色（info / warning / error / success / debug）：

```kotlin
AutoThemeTokens.extraColors.error.text
AutoThemeTokens.extraColors.success.bg
```

### CompositionLocal

| Local | 说明 |
|-------|------|
| `LocalUiMode` | 当前 UI 引擎 |
| `LocalColorMode` | 当前颜色模式 |
| `isInDarkTheme()` | 是否深色主题 |

### 形状

`AutoTheme` 内置 `appShapes()`：`extraSmall/small=10dp`，`medium=18dp`，`large/extraLarge=24dp`。通过 `MaterialTheme.shapes` 访问。

### 内置设置页 UiSettingsScreen

lib 提供完整主题设置 UI，可直接嵌入：

```kotlin
UiSettingsScreen(
    modifier = Modifier.fillMaxSize(),
    onThemeChanged = { recreateForThemeChange() },
)
```

设置项分组：

- **主题风格**：UI 引擎、颜色模式、显示比例
- **导航**：悬浮导航栏
- **主题颜色**（Material 或 Monet 模式下显示）：动态颜色、主题色

自定义选项文案（默认走 lib 多语言）：

```kotlin
UiSettingsScreen(
    options = UiSettingsOptions(
        uiModeEntries = listOf("miuix" to "Miuix", "material" to "Material"),
        colorModeEntries = listOf(0 to "跟随系统", 1 to "浅色"),
        themeColorEntries = emptyList(), // empty 使用 lib 内置主题色列表
    ),
    onThemeChanged = { recreateForThemeChange() },
)
```

---

## 组件一览

所有 `Theme*` 组件位于 `net.ankio.theme.compat`，根据 `LocalUiMode` 在 Miuix / Material 间自动切换。

| 分类 | 组件 |
|------|------|
| 按钮 | `ThemePrimaryButton`, `ThemeSecondaryButton`, `ThemeButtonGroup`, `ThemeGroupButton`, `ThemeFloatingActionButton` |
| 文本/图标 | `ThemeText`, `ThemeIcon`, `ThemeIconButton`, `ThemeSmallTitle` |
| 容器 | `ThemeSurface`, `ThemeCard`, `ThemeFloatingToolbar` |
| 表单 | `ThemeTextField`, `ThemeSearchBar`, `ThemeSwitch`, `ThemeCheckbox`, `ThemeSlider`, `ThemeNumberPicker`, `ThemeSuperSpinner`, `ThemeSuperDropdown` |
| 导航 | `ThemeTopAppBar`, `ThemeNavigationBar`, `ThemeNavigationBarItem`, `ThemeNavigationRail`, `ThemeTabRow` |
| 反馈 | `ThemeLinearProgressIndicator`, `ThemeCircularProgressIndicator`, `ThemePullToRefresh`, `ThemeHorizontalDivider`, `ThemeVerticalDivider` |
| 弹窗 | `ThemeSuperListPopup`, `ThemeListPopupColumn`, `ThemeLazyListPopupColumn`, `ThemeListPopupItem` |
| 其他 | `ThemeToast`, `UiSettingsScreen` |

### 按钮

#### ThemePrimaryButton / ThemeSecondaryButton

```kotlin
ThemePrimaryButton(onClick = { }) {
    ThemeText("确认", style = AnkioTheme.textStyles.button)
}

ThemeSecondaryButton(onClick = { }) {
    ThemeText("取消")
}
```

#### ThemeButtonGroup / ThemeGroupButton

连体按钮组，按位置自动处理圆角：

```kotlin
ThemeButtonGroup {
    ThemeGroupButton(onClick = { }, position = ButtonGroupPosition.Start) {
        ThemeText("左")
    }
    ThemeGroupButton(onClick = { }, position = ButtonGroupPosition.Middle) {
        ThemeText("中")
    }
    ThemeGroupButton(onClick = { }, position = ButtonGroupPosition.End) {
        ThemeText("右")
    }
}
```

#### ThemeFloatingActionButton

```kotlin
ThemeFloatingActionButton(onClick = { }) {
    ThemeIcon(Icons.Filled.Add, null, tint = AnkioTheme.colorScheme.onPrimary)
}
```

---

### 文本与图标

#### ThemeText

```kotlin
ThemeText(
    text = "正文",
    style = AnkioTheme.textStyles.body1,
    color = AnkioTheme.colorScheme.onSurface,
)
```

可用样式：`main`, `paragraph`, `body1`, `body2`, `button`, `footnote1`, `footnote2`, `headline1`, `headline2`, `subtitle`, `title1`–`title4`。

#### ThemeIcon

支持 `@DrawableRes` 与 `ImageVector` 两种重载：

```kotlin
ThemeIcon(
    imageVector = Icons.Filled.Home,
    contentDescription = null,
    tint = AnkioTheme.colorScheme.primary,
)

ThemeIcon(
    resId = R.drawable.ic_logo,
    contentDescription = null,
    tint = AnkioTheme.colorScheme.onSurface,
)
```

#### ThemeIconButton

```kotlin
ThemeIconButton(onClick = { }) {
    ThemeIcon(Icons.Filled.Settings, null, tint = AnkioTheme.colorScheme.onSurface)
}
```

#### ThemeSmallTitle

分区小标题：

```kotlin
ThemeSmallTitle(text = "账户设置")
```

---

### 容器

#### ThemeSurface

```kotlin
ThemeSurface(
    modifier = Modifier.fillMaxSize(),
    color = AnkioTheme.colorScheme.surface,
) {
    // content
}
```

#### ThemeCard

支持自定义 `shape`、`border`、`onClick`：

```kotlin
ThemeCard(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    containerColor = AnkioTheme.colorScheme.surfaceContainerLow,
    onClick = { /* 可选 */ },
) {
    ThemeText("卡片内容")
}
```

设置页内部使用 `SettingCard` 实现连续卡片分组圆角，业务层直接使用 `ThemeCard` 即可。

#### ThemeFloatingToolbar

```kotlin
ThemeFloatingToolbar {
  // 悬浮工具栏内容
}
```

---

### 表单与输入

#### ThemeTextField

统一样式 API，Material 支持 `Outlined` / `Filled`：

```kotlin
var text by remember { mutableStateOf("") }

ThemeTextField(
    value = text,
    onValueChange = { text = it },
    label = "用户名",
    placeholder = "请输入",
    leadingIcon = {
        ThemeIcon(Icons.Filled.Person, null, tint = AnkioTheme.colorScheme.onSurfaceVariant)
    },
    style = TextFieldStyle.Outlined,
)
```

#### ThemeSearchBar

```kotlin
var query by remember { mutableStateOf("") }
var expanded by remember { mutableStateOf(false) }

ThemeSearchBar(
    query = query,
    onQueryChange = { query = it },
    expanded = expanded,
    onExpandedChange = { expanded = it },
    onSearch = { /* 搜索 */ },
) {
    // 展开后的建议列表等内容
}
```

#### ThemeSwitch / ThemeCheckbox / ThemeSlider

```kotlin
var checked by remember { mutableStateOf(false) }
ThemeSwitch(checked = checked, onCheckedChange = { checked = it })

ThemeCheckbox(checked = checked, onCheckedChange = { checked = it })

ThemeSlider(
    value = progress,
    onValueChange = { progress = it },
    valueRange = 0f..100f,
)
```

#### ThemeNumberPicker

```kotlin
ThemeNumberPicker(
    value = number,
    onValueChange = { number = it },
    range = 1..10,
)
```

#### ThemeSuperSpinner / ThemeSuperDropdown

带标题、副标题、图标的下拉选择器（设置项常用）：

```kotlin
import top.yukonga.miuix.kmp.basic.SpinnerEntry

ThemeSuperSpinner(
    items = listOf(
        SpinnerEntry(title = "选项 A"),
        SpinnerEntry(title = "选项 B"),
    ),
    selectedIndex = index,
    onSelectedIndexChange = { index = it },
    title = "标题",
    summary = "副标题说明",
)

// 纯文本简化版
ThemeSuperDropdown(
    items = listOf("A", "B", "C"),
    selectedIndex = index,
    onSelectedIndexChange = { index = it },
    title = "选择项",
)
```

---

### 导航

#### ThemeTopAppBar

```kotlin
ThemeTopAppBar(
    title = "页面标题",
    navigationIcon = {
        ThemeIconButton(onClick = { }) {
            ThemeIcon(Icons.AutoMirrored.Filled.ArrowBack, null,
                tint = AnkioTheme.colorScheme.onSurface)
        }
    },
    actions = {
        ThemeIconButton(onClick = { }) {
            ThemeIcon(Icons.Filled.Search, null, tint = AnkioTheme.colorScheme.onSurface)
        }
    },
)
```

#### ThemeNavigationBar / ThemeNavigationBarItem

默认读取 `ThemeSettings.navigationBarFloating` 决定是否悬浮；也可显式传入 `floating`：

```kotlin
val items = listOf("首页" to Icons.Filled.Home, "设置" to Icons.Filled.Settings)
var selected by remember { mutableIntStateOf(0) }

ThemeNavigationBar(
    modifier = Modifier.fillMaxWidth(),
    // floating = true,  // 可选，覆盖 ThemeSettings
    mode = ThemeFloatingNavBarMode.IconAndText,
) {
    items.forEachIndexed { index, (label, icon) ->
        ThemeNavigationBarItem(
            selected = selected == index,
            onClick = { selected = index },
            icon = icon,
            label = label,
        )
    }
}
```

`ThemeFloatingNavBarMode`：`IconAndText` / `IconOnly` / `TextOnly`。

#### ThemeNavigationRail

```kotlin
ThemeNavigationRail(
    containerColor = AnkioTheme.colorScheme.surface,
    header = { /* 顶部图标 */ },
) {
    // rail 内容
}
```

#### ThemeTabRow

```kotlin
ThemeTabRow(
    tabs = listOf("首页", "发现", "我的"),
    selectedTabIndex = tabIndex,
    onTabSelected = { tabIndex = it },
)
```

---

### 反馈与进度

#### ThemeLinearProgressIndicator / ThemeCircularProgressIndicator

```kotlin
ThemeLinearProgressIndicator(progress = { 0.6f })
ThemeCircularProgressIndicator()
```

#### ThemePullToRefresh

```kotlin
ThemePullToRefresh(
    isRefreshing = refreshing,
    onRefresh = { refreshing = true },
) {
    LazyColumn { /* 列表内容 */ }
}
```

#### ThemeHorizontalDivider / ThemeVerticalDivider

```kotlin
ThemeHorizontalDivider()
ThemeVerticalDivider()
```

---

### 弹窗

#### ThemeSuperListPopup

列表弹窗容器，Miuix 使用窗口级 Popup（无需 Scaffold）：

```kotlin
var show by remember { mutableStateOf(false) }

ThemeSuperListPopup(
    show = show,
    onDismissRequest = { show = false },
) {
    ThemeListPopupColumn {
        ThemeListPopupItem(text = "编辑") { show = false }
        ThemeListPopupItem(text = "删除") { show = false }
    }
}
```

#### ThemeLazyListPopupColumn

大数据量懒加载列表，配合 `enableContainerScroll = false`：

```kotlin
ThemeSuperListPopup(
    show = show,
    onDismissRequest = { show = false },
    enableContainerScroll = false,
) {
    ThemeLazyListPopupColumn(items = largeList) { item ->
        ThemeListPopupItem(text = item.name) { /* ... */ }
    }
}
```

---

### ThemeToast

Compose 风格 Toast，支持多种语义样式：

```kotlin
// Application 中 init
ThemeToast.init(application)

// 检查 / 申请悬浮窗权限
if (!ThemeToast.hasOverlayPermission()) {
    ThemeToast.requestOverlayPermission(context)
}

// 显示
ThemeToast.show("操作成功", ThemeToast.Style.Success)
ThemeToast.show("出错了", ThemeToast.Style.Error)
ThemeToast.show("提示", ThemeToast.Style.Info, ThemeToast.Config(position = ThemeToast.Position.Top))
```

样式：`Debug` / `Error` / `Success` / `Warning` / `Info`  
位置：`Top` / `Center` / `Bottom`

---

## 颜色与语义色

### ThemeColorScheme

通过 `AnkioTheme.colorScheme` 访问，属性与 Material ColorScheme 对齐：

`primary`, `onPrimary`, `primaryContainer`, `surface`, `surfaceContainer`, `onSurface`, `onSurfaceVariant`, `outline`, `error` 等。

### 语义色 AppExtraColors

| 类型 | 用途 |
|------|------|
| `info` | 信息提示 |
| `warning` | 警告 |
| `error` | 错误 |
| `success` | 成功 |
| `debug` | 调试 |

每类含 `text`（前景）与 `bg`（背景）：

```kotlin
Box(Modifier.background(AnkioTheme.colorScheme.semantic.success.bg)) {
    ThemeText("成功", color = AnkioTheme.colorScheme.semantic.success.text)
}
```

### 预设主题色

`themeKeyOptions` 提供全部可选 key（如 `MATERIAL_BLUE`）。`ThemeSettings.themeColor` 存储 key，非 ARGB。

---

## 项目结构

```
theme/
├── lib/          # 主题库（JitPack 发布此模块）
│   └── src/main/java/net/ankio/theme/
│       ├── compat/       # Theme* 组件兼容层
│       ├── settings/     # UiSettingsScreen 等
│       └── toast/        # ThemeToast
└── app/          # 组件演示 App
```

---

## 发布新版本

```bash
git tag 1.0.0
git push origin 1.0.0
```

在 [JitPack](https://jitpack.io/#AnkioTomas/theme) 等待构建完成。

本地验证：

```bash
./gradlew :lib:publishToMavenLocal
```

---

## 设计原则

1. **一套 API，两套引擎** — 业务只依赖 `Theme*` 与 `AnkioTheme`，不直接分支 Miuix / Material。
2. **设置驱动** — `ThemeSettings` 是唯一持久化来源，`AutoTheme` 读取后注入 CompositionLocal。
3. **消除无效配置** — 如 Miuix 非 Monet 模式下隐藏主题色选项；选具体主题色时自动关闭动态色。
4. **Window 级 Popup** — Miuix 下拉/列表弹窗使用 Window 变体，不依赖 Scaffold。

完整交互示例见 `app` 模块的 `ComponentsGallery`。
