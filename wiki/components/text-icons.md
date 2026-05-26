# 文本与图标

包名：`net.ankio.theme.compat`

---

## ThemeText

统一文本组件：Material `Text` ↔ Miuix `MiuixText`。

```kotlin
ThemeText(
    text = "正文",
    style = AnkioTheme.textStyles.body1,
    color = AnkioTheme.colorScheme.onSurface,
    modifier = Modifier,
)
```

| 参数 | 说明 |
|------|------|
| `text` | 内容 |
| `style` | `TextStyle`，来自 `AnkioTheme.textStyles` |
| `color` | **必填**；无默认色 |
| `modifier` | |

### ThemeTextStyles

通过 `AnkioTheme.textStyles` 访问，与双栈字号对齐：

| 属性 | 典型用途 |
|------|----------|
| `title1`–`title4` | 页面/区块标题 |
| `headline1` / `headline2` | 大标题 |
| `subtitle` | 副标题 |
| `main` / `paragraph` | 主文/段落 |
| `body1` / `body2` | 正文 |
| `button` | 按钮标签 |
| `footnote1` / `footnote2` | 脚注、说明 |

Material 侧由 `Typography` 映射；Miuix 侧与 `MiuixTheme.textStyles` 一一对应。详见 [主题体系](../theme-system.md)。

---

## ThemeIcon

图标：Material `Icon` ↔ Miuix `MiuixIcon`。

### ImageVector 重载

```kotlin
ThemeIcon(
    imageVector = Icons.Filled.Home,
    contentDescription = null,
    tint = AnkioTheme.colorScheme.primary,
    modifier = Modifier.size(24.dp),
)
```

### @DrawableRes 重载

```kotlin
ThemeIcon(
    resId = R.drawable.ic_logo,
    contentDescription = "Logo",
    tint = AnkioTheme.colorScheme.onSurface,
)
```

| 参数 | 说明 |
|------|------|
| `tint` | **必填** |
| `contentDescription` | 无障碍；装饰性图标可 `null` |

Miuix 下 `ImageVector` 经 `rememberVectorPainter` 绘制。

---

## ThemeIconButton

图标按钮：Material `IconButton` ↔ Miuix `MiuixIconButton`。

```kotlin
ThemeIconButton(onClick = { }, enabled = true) {
    ThemeIcon(
        imageVector = Icons.Filled.Settings,
        contentDescription = "设置",
        tint = AnkioTheme.colorScheme.onSurface,
    )
}
```

---

## ThemeSmallTitle

列表/设置分组用的小标题。

```kotlin
ThemeSmallTitle(text = "账户设置")
```

| 参数 | 默认 | 说明 |
|------|------|------|
| `text` | — | |
| `modifier` | `Modifier` | |
| `textColor` | `onSurfaceVariant` | |
| `insideMargin` | `PaddingValues(0)` | **默认无内边距** |

| 引擎 | 行为 |
|------|------|
| Material | `ThemeText` + `subtitle` 样式 + `insideMargin` padding |
| Miuix | `MiuixSmallTitle` |

### 推荐内边距常量

| 常量 | 值 | 场景 |
|------|-----|------|
| `ThemeSmallTitleInsideMarginMiuix` | 28dp 水平 + 8dp 垂直 | Miuix 全宽设置页 |
| `ThemeSmallTitleInsideMarginMaterial` | 16dp 水平 + 8dp 垂直 | Material 列表 |

---

## Demo

`app` → **排版**、**图标**
