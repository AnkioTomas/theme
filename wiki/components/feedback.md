# 反馈与进度

包名：`net.ankio.theme.compat`

---

## ThemeLinearProgressIndicator

水平进度条。

```kotlin
// 确定进度（0f..1f）
ThemeLinearProgressIndicator(
    modifier = Modifier.fillMaxWidth(),
    progress = { 0.6f },
)

// 不确定（转圈条）
ThemeLinearProgressIndicator(
    modifier = Modifier.fillMaxWidth(),
    progress = null,
)
```

| 参数 | 说明 |
|------|------|
| `progress` | `null` = 不确定；非 null 为 `() -> Float` 或 `Float`（以实现为准，当前为 `Float?`） |
| `modifier` | |

| 引擎 | 实现 |
|------|------|
| Material | `LinearProgressIndicator` |
| Miuix | `MiuixLinearProgressIndicator` |

---

## ThemeCircularProgressIndicator

圆形进度指示器；`progress` 语义同线性。

```kotlin
ThemeCircularProgressIndicator(progress = { 0.5f })
ThemeCircularProgressIndicator(progress = null)
```

---

## ThemePullToRefresh

下拉刷新容器。

```kotlin
ThemePullToRefresh(
    isRefreshing = refreshing,
    onRefresh = {
        refreshing = true
        // 加载完成后 refreshing = false
    },
    modifier = Modifier.fillMaxSize(),
) {
    LazyColumn { /* 可滚动内容 */ }
}
```

| 引擎 | 实现 |
|------|------|
| Material | `PullToRefreshBox` |
| Miuix | `MiuixPullToRefresh`（内层 `Box` 包裹 content） |

---

## ThemeHorizontalDivider

水平分割线。

```kotlin
ThemeHorizontalDivider(
    modifier = Modifier.fillMaxWidth(),
    thickness = DividerDefaults.Thickness,
    color = AnkioTheme.colorScheme.outlineVariant,
)
```

---

## ThemeVerticalDivider

垂直分割线；参数同水平，用于 `Row` 内分隔。

```kotlin
ThemeVerticalDivider(
    modifier = Modifier.fillMaxHeight(),
    color = AnkioTheme.colorScheme.outlineVariant,
)
```

---

## Demo

`app` → **进度**、**下拉刷新**
