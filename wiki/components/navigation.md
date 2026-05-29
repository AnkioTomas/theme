# 导航

---

## ThemeApp（推荐：整 App 壳）

包名：`net.ankio.theme.layout`

多 Tab + 子页 + 可折叠 TopBar + 预测性返回时，用 **`ThemeApp`** 一次接入，无需手写 `NavHost`。

**完整说明（含 `lazyList` / `scrollColumn`、常见错误、Demo 对照）** → **[应用布局框架](../layout.md)**

```kotlin
ThemeApp(start = "home", titleAlignment = align) {
    screen("home", "首页", tab = Icons.Home to "首页") {
        HomeList(list = lazyList())
    }
    screen("settings", "设置", tab = Icons.Settings to "设置", collapse = false) {
        scrollColumn { SettingsBody() }
    }
}
```

---

## ThemeTopAppBar（单页或自定义 Nav）

包名：`net.ankio.theme.compat`

---

## ThemeTopAppBar

顶部应用栏，支持大标题折叠。

```kotlin
val scroll = rememberThemeTopAppBarScroll(collapseOnScroll = true)

ThemeTopAppBar(
    title = "收起后标题",
    largeTitle = "大标题", // null 且 scroll 非 null 时等同 title
    titleAlignment = ThemeTopAppBarTitleAlignment.Center, // 或 Start
    scroll = scroll, // null = 不折叠
    modifier = Modifier.fillMaxWidth(),
    navigationIcon = { /* ThemeIconButton */ },
    actions = { /* RowScope */ },
)
```

| 参数 | 说明 |
|------|------|
| `title` | 小标题 / 收起后标题 |
| `largeTitle` | 展开时大标题 |
| `titleAlignment` | 收起后对齐；大标题区一般为起始对齐 |
| `scroll` | `rememberThemeTopAppBarScroll` 返回值 |

### 滚动折叠

```kotlin
val scroll = rememberThemeTopAppBarScroll(collapseOnScroll = true)

LazyColumn(Modifier.then(scroll.contentModifier)) { /* items */ }

ThemeTopAppBar(..., scroll = scroll)
```

| API | 说明 |
|-----|------|
| `rememberThemeTopAppBarScroll(true)` | `false` 时返回 `null` |
| `ThemeTopAppBarScroll.contentModifier` | 挂到 LazyColumn / `verticalScroll` 根 Modifier |

使用 **`ThemeApp`** 时，列表页用 `lazyList()`，表单页用 `scrollColumn { }`，一般不必手写 `contentModifier`。

### Miuix vs Material

| 场景 | Material | Miuix |
|------|----------|-------|
| 可折叠 | `LargeTopAppBar` | `MiuixTopAppBar` + scrollBehavior |
| 展开/收起标题 | `collapsedFraction > 0.5` 时用 `title`，否则 `largeTitle ?: title` | `largeTitle` 独立参数 |
| 不可折叠 + 居中 | `CenterAlignedTopAppBar` | `MiuixSmallTopAppBar` |
| 不可折叠 + 起始 | `TopAppBar` | `MiuixTopAppBar`（`largeTitle = null`） |
| 状态栏 | 透明状态栏 + 顶栏自绘（`enableEdgeToEdge`）；图标深浅由 `AutoTheme` 按夜模式设置 | 同左 |

### Navigation Compose 集成

| 要点 | ThemeApp 做法 | 手写 Nav 做法 |
|------|---------------|---------------|
| 子页标题 | `screen(..., title = { entry -> ... })` | 用 `arguments`，勿解析 `destination.route` 模板 |
| 列表滚动 | `lazyList()` | `rememberNavDestinationLazyListState(entry)` |
| 顶栏折叠 | 框架按 `screen` 的 `collapse` 处理 | `rememberThemeTopAppBarScroll` + `contentModifier` |
| 预测性返回 | 内置 `NavHost` + `detail` 转场 | 见 [layout.md](../layout.md) |

---

## ThemeTopAppBarTitleAlignment

```kotlin
enum class ThemeTopAppBarTitleAlignment { Start, Center }
```

仅影响**收起后**小标题对齐；可折叠时大标题区一般为起始对齐。

---

## ThemeNavigationBar

底部导航栏容器。

```kotlin
ThemeNavigationBar(
    modifier = Modifier.fillMaxWidth(),
    containerColor = AnkioTheme.colorScheme.surface,
    contentColor = AnkioTheme.colorScheme.onSurface,
    showDivider = true,
) {
    ThemeNavigationBarItem(
        selected = tab == 0,
        onClick = { tab = 0 },
        icon = Icons.Filled.Home,
        label = "首页",
    )
}
```

| 引擎 | 差异 |
|------|------|
| Material | 可选顶部 `ThemeHorizontalDivider` + `NavigationBar` |
| Miuix | `MiuixNavigationBar`（内置分割线选项） |

---

## ThemeNavigationBarItem

`RowScope` 扩展；须在 `ThemeNavigationBar` 内使用。

| 参数 | 说明 |
|------|------|
| `selected` | 选中态 |
| `onClick` | |
| `icon` | `ImageVector` |
| `label` | 文案 |

| 引擎 | 行为 |
|------|------|
| Material | `ThemeIcon` + `ThemeText`；选中 `primary` / 未选 `onSurfaceVariant`；指示器 `primaryContainer` |
| Miuix | 原生 `MiuixNavigationBarItem`（传入 `icon` + `label`） |

---

## ThemeNavigationRail

侧边导航（大屏/平板）。

```kotlin
ThemeNavigationRail(
    modifier = Modifier,
    containerColor = AnkioTheme.colorScheme.surface,
    contentColor = AnkioTheme.colorScheme.onSurface,
    header = { /* 顶部 Logo 等 */ },
    showDivider = true,
    minWidth = 80.dp,
) {
    // 自定义 item 内容
}
```

| 引擎 | 差异 |
|------|------|
| Material | `NavigationRail` + 可选右侧 `ThemeVerticalDivider` |
| Miuix | `MiuixNavigationRail` |

---

## ThemeTabRow

顶部标签栏。

```kotlin
ThemeTabRow(
    tabs = listOf("首页", "发现", "我的"),
    selectedTabIndex = index,
    onTabSelected = { index = it },
    modifier = Modifier.fillMaxWidth(),
)
```

Material `PrimaryTabRow` ↔ Miuix `MiuixTabRow`；文案过长时 Material 侧省略。

---

## Demo

`app` → **ThemeApp** 演示 TopBar + BottomNav + Nav；`gallery` 内仍有单独 **TopAppBar / TabRow / Rail** 组件示例
