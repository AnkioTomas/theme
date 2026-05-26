# 文本与图标

## ThemeText

```kotlin
ThemeText(
    text = "正文",
    style = AnkioTheme.textStyles.body1,
    color = AnkioTheme.colorScheme.onSurface,
    modifier = Modifier,
    maxLines = Int.MAX_VALUE,
)
```

样式见 [主题体系 - 排版](../theme-system.md)。

## ThemeIcon

**ImageVector** 与 **@DrawableRes** 两种重载：

```kotlin
ThemeIcon(
    imageVector = Icons.Filled.Home,
    contentDescription = null,
    tint = AnkioTheme.colorScheme.primary,
    modifier = Modifier.size(24.dp),
)

ThemeIcon(
    resId = R.drawable.ic_logo,
    contentDescription = "Logo",
    tint = AnkioTheme.colorScheme.onSurface,
)
```

## ThemeIconButton

```kotlin
ThemeIconButton(
    onClick = { },
    enabled = true,
) {
    ThemeIcon(Icons.Filled.Settings, null, tint = AnkioTheme.colorScheme.onSurface)
}
```

## ThemeSmallTitle

分组小标题。**默认无内边距**，由外层列表 `padding` 控制。

```kotlin
ThemeSmallTitle(text = "账户设置")

// Miuix 全宽设置页（无外层水平 padding）可选用：
ThemeSmallTitle(
    text = "账户设置",
    insideMargin = ThemeSmallTitleInsideMarginMiuix, // 28dp 水平
)
```

| 常量 | 用途 |
|------|------|
| `ThemeSmallTitleInsideMarginMiuix` | 28dp 水平 + 8dp 垂直 |
| `ThemeSmallTitleInsideMarginMaterial` | 16dp 水平 + 8dp 垂直 |

## Demo

`app` → **排版**、**图标**、**进度**（SmallTitle 示例）
