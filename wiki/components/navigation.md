# 导航

## ThemeTopAppBar

```kotlin
val scroll = rememberThemeTopAppBarScroll(collapseOnScroll = true)

ThemeTopAppBar(
    title = "收起后标题",
    largeTitle = "大标题", // 可折叠时显示
    titleAlignment = ThemeTopAppBarTitleAlignment.Center, // 或 Start
    scroll = scroll, // null 不折叠
    modifier = Modifier.fillMaxWidth(),
    navigationIcon = { /* IconButton */ },
    actions = { /* RowScope */ },
)
```

### 滚动折叠

列表需挂 `nestedScroll`：

```kotlin
LazyColumn(
    modifier = Modifier.nestedScroll(scroll.nestedScrollConnection),
) { /* ... */ }
```

| API | 说明 |
|-----|------|
| `rememberThemeTopAppBarScroll(true)` | 创建 Miuix/Material 对应 scrollBehavior |
| `ThemeTopAppBarScroll.nestedScrollConnection` | 传给 `Modifier.nestedScroll` |

Material 折叠时会同步 **statusBar** 颜色（`surface` ↔ `surfaceContainer` 渐变）。

## ThemeNavigationBar / ThemeNavigationBarItem

```kotlin
ThemeNavigationBar(
    modifier = Modifier.fillMaxWidth(),
    containerColor = AnkioTheme.colorScheme.surface,
    showDivider = true,
) {
    ThemeNavigationBarItem(
        selected = selected == 0,
        onClick = { selected = 0 },
        icon = Icons.Filled.Home,
        label = "首页",
    )
}
```

## ThemeNavigationRail

大屏侧边导航。

```kotlin
ThemeNavigationRail(
    containerColor = AnkioTheme.colorScheme.surface,
    header = { /* 顶部 */ },
) {
    // items
}
```

## ThemeTabRow

```kotlin
ThemeTabRow(
    tabs = listOf("首页", "发现", "我的"),
    selectedTabIndex = tabIndex,
    onTabSelected = { tabIndex = it },
)
```

## Demo

`app` → **顶部栏**、**标签栏**、**底部导航**、**侧边导航**；主界面 `DemoAppShell` 演示 TopBar + BottomNav
