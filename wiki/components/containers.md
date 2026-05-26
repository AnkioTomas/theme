# 容器

## ThemeSurface

基础表面容器。

```kotlin
ThemeSurface(
    modifier = Modifier.fillMaxSize(),
    shape = MaterialTheme.shapes.medium,
    color = AnkioTheme.colorScheme.surface,
    contentColor = AnkioTheme.colorScheme.onSurface,
    border = null,
    shadowElevation = 0.dp,
) {
    // content
}
```

## ThemeCard

可点击卡片，支持自定义形状与边框。

```kotlin
ThemeCard(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    containerColor = AnkioTheme.colorScheme.surfaceContainerLow,
    contentColor = AnkioTheme.colorScheme.onSurface,
    border = null,
    onClick = { /* 可选 */ },
) {
    ThemeText("卡片内容")
}
```

设置页分组请用 [SettingCard](../settings-widgets.md)，自带 `First/Middle/Last/Single` 圆角拼接。

## ThemeFloatingToolbar

悬浮工具栏（Miuix / Material 各自实现）。

```kotlin
ThemeFloatingToolbar(
    modifier = Modifier,
    containerColor = AnkioTheme.colorScheme.surfaceContainerHigh,
) {
    // 工具栏内容
}
```

## Demo

`app` → **容器**
