# 容器

包名：`net.ankio.theme.compat`

---

## ThemeSurface

基础表面容器：Material `Surface` ↔ Miuix `MiuixSurface`。

```kotlin
ThemeSurface(
    modifier = Modifier.fillMaxSize(),
    shape = MaterialTheme.shapes.medium,
    color = AnkioTheme.colorScheme.surface,
    contentColor = AnkioTheme.colorScheme.onSurface,
    border = null,
    shadowElevation = 0.dp,
) {
    // 内容
}
```

| 参数 | 默认 | 说明 |
|------|------|------|
| `shape` | `medium` | 圆角形状 |
| `color` | `surface` | 背景 |
| `contentColor` | `onSurface` | 默认前景（子项仍须为 `ThemeText` 传色） |
| `border` | `null` | `BorderStroke?` |
| `shadowElevation` | `0.dp` | Material 阴影；Miuix 透传 |

`BaseComposeActivity` 根布局通常用 `ThemeSurface` 铺满 `surface` 色。

---

## ThemeCard

卡片容器，支持点击与自定义形状。

```kotlin
ThemeCard(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    containerColor = AnkioTheme.colorScheme.surfaceContainerLow,
    contentColor = AnkioTheme.colorScheme.onSurface,
    elevation = 0.dp,
    border = null,
    onClick = { }, // null = 不可点
) {
    ThemeText(
        text = "卡片内容",
        style = AnkioTheme.textStyles.body1,
        color = AnkioTheme.colorScheme.onSurface,
    )
}
```

| 参数 | 默认 | 说明 |
|------|------|------|
| `containerColor` | `surfaceContainerLow` | |
| `onClick` | `null` | 非 null 时可点击 |

| 引擎 | 差异 |
|------|------|
| Material | Material3 `Card` |
| Miuix | `cornerRadius = 0` + 外层 `clip(shape)`；可带 `border`；有按压反馈 |

设置页分组请用 [SettingCard](../settings-widgets.md#settingcard)（`First/Middle/Last/Single` 圆角拼接）。

---

## ThemeFloatingToolbar

悬浮工具栏（如 TopAppBar 底部操作条）。

```kotlin
ThemeFloatingToolbar {
    ThemeIconButton(onClick = {}) {
        ThemeIcon(Icons.Filled.Share, null, tint = AnkioTheme.colorScheme.onSurface)
    }
}
```

| 参数 | 默认 | 说明 |
|------|------|------|
| `color` | `surfaceContainer` | 背景 |
| `cornerRadius` | `ThemeFloatingToolbarDefaults.CornerRadius` | |
| `shadowElevation` | `4.dp` | |
| `showDivider` | `false` | 底部分割线 |
| `expandWidth` | `false` | `true` 时 `fillMaxWidth()` |
| `horizontalArrangement` | **居中** | 子项水平排列 |

| 引擎 | 实现 |
|------|------|
| Material | `Card` 风格模拟 |
| Miuix | `MiuixFloatingToolbar` |

### ThemeFloatingToolbarDefaults

| 常量 | 值 |
|------|-----|
| `ContentPadding` | 12dp 水平 / 8dp 垂直 |
| `ItemSpacing` | 8dp |
| `ShadowElevation` | 4dp |

铺满并两端对齐示例：

```kotlin
ThemeFloatingToolbar(
    modifier = Modifier.fillMaxWidth(),
    expandWidth = true,
    horizontalArrangement = Arrangement.SpaceEvenly,
) { /* ... */ }
```

---

## Demo

`app` → **容器**
