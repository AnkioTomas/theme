# 反馈与进度

## ThemeLinearProgressIndicator

```kotlin
// 确定进度 0f..1f
ThemeLinearProgressIndicator(
    modifier = Modifier.fillMaxWidth(),
    progress = { 0.6f },
)

// 不确定进度
ThemeLinearProgressIndicator(modifier = Modifier.fillMaxWidth(), progress = null)
```

## ThemeCircularProgressIndicator

```kotlin
ThemeCircularProgressIndicator(progress = { 0.5f })
ThemeCircularProgressIndicator(progress = null) // 转圈
```

## ThemePullToRefresh

```kotlin
ThemePullToRefresh(
    isRefreshing = refreshing,
    onRefresh = { refreshing = true },
) {
    LazyColumn { /* 内容 */ }
}
```

## ThemeHorizontalDivider / ThemeVerticalDivider

```kotlin
ThemeHorizontalDivider(
    modifier = Modifier,
    thickness = 1.dp,
    color = AnkioTheme.colorScheme.outlineVariant,
)
ThemeVerticalDivider()
```

## Demo

`app` → **进度**、**下拉刷新**
