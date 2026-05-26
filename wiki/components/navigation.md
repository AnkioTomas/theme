# 导航

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
    ?: return

LazyColumn(
    modifier = Modifier.nestedScroll(scroll.nestedScrollConnection),
) { /* items */ }

ThemeTopAppBar(..., scroll = scroll)
```

| API | 说明 |
|-----|------|
| `rememberThemeTopAppBarScroll(true)` | `false` 时返回 `null` |
| `ThemeTopAppBarScroll.nestedScrollConnection` | 挂到可滚动容器 |
| `ThemeTopAppBarScroll.isActive` | 是否启用折叠 |

### Miuix vs Material

| 场景 | Material | Miuix |
|------|----------|-------|
| 可折叠 | `LargeTopAppBar` | `MiuixTopAppBar` + scrollBehavior |
| 展开/收起标题 | `collapsedFraction > 0.5` 时用 `title`，否则 `largeTitle ?: title` | `largeTitle` 独立参数 |
| 不可折叠 + 居中 | `CenterAlignedTopAppBar` | `MiuixSmallTopAppBar` |
| 不可折叠 + 起始 | `TopAppBar` | `MiuixTopAppBar`（`largeTitle = null`） |
| 状态栏 | 折叠时 `surface` → `surfaceContainer` 渐变（`snapshotFlow` 驱动） | 无同步（系统栏由 Activity 主题控制） |

### Navigation Compose 集成（Demo）

| 要点 | 做法 |
|------|------|
| 子页标题 | **勿**用 `destination.route` 解析带参路由（模板为 `category/{categoryName}`）；用 `NavBackStackEntry.arguments` |
| 列表滚动位置 | `rememberSaveable(backStackEntry, saver = LazyListState.Saver) { LazyListState() }` |
| 折叠偏移重置 | `key(routeKey) { rememberThemeTopAppBarScroll(true) }`，避免子页沿用上一页折叠状态 |
| 预测性返回 | `NavHost` 默认转场 + `popBackStack()`；Manifest `enableOnBackInvokedCallback` |

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

`app` → **顶部栏**、**标签栏**、**底部导航**、**侧边导航**；`DemoAppShell` 演示 TopBar + BottomNav
