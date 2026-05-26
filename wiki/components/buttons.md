# 按钮

包名：`net.ankio.theme.compat`

---

## ThemeButtonStyle

组按钮与 `ThemeGroupButton` 共用的视觉风格（`sealed interface`）。

| 值 | 容器色（概要） | 文案色（概要） |
|----|----------------|----------------|
| `Primary` | 主题主色 | Material `onPrimary`；Miuix `onPrimary` |
| `Secondary` | Material `secondaryContainer`；Miuix `secondaryVariant` | Material `onSecondaryContainer`；Miuix `onSurfaceContainer` |
| `Custom(...)` | 调用方指定 | `contentColor` + 可选 disabled 色 |

```kotlin
ThemeButtonStyle.Custom(
    containerColor = Color.Red,
    contentColor = Color.White,
    disabledContainerColor = Color.Red.copy(alpha = 0.38f),
    disabledContentColor = Color.White.copy(alpha = 0.38f),
)
```

---

## ThemeButtonLabel

按钮内默认文案；**必须**放在 `ThemePrimaryButton` / `ThemeSecondaryButton` / `ThemeCustomButton` / `ThemeGroupButton` 内。

| 参数 | 默认 | 说明 |
|------|------|------|
| `text` | — | 显示文字 |
| `modifier` | `Modifier` | |

- 使用 `AnkioTheme.textStyles.button`
- 颜色由父按钮通过 `LocalButtonContentColor` 注入

---

## ThemePrimaryButton

主操作按钮。

```kotlin
// 推荐：text 重载
ThemePrimaryButton(onClick = { }, text = "确认")

// 自定义内容（图标 + 文字）
ThemePrimaryButton(onClick = { }) {
    ThemeButtonLabel("确认")
}
```

| 参数 | 默认 | 说明 |
|------|------|------|
| `onClick` | — | 点击回调 |
| `modifier` | `Modifier` | |
| `enabled` | `true` | |
| `content` / `text` | — | 二选一 |

| 引擎 | 实现 |
|------|------|
| Material | `Button()`，默认 primary 配色 |
| Miuix | `MiuixButton` + `buttonColorsPrimary()` |

---

## ThemeSecondaryButton

次要操作按钮；API 与 Primary 相同。

| 引擎 | 实现 |
|------|------|
| Material | `FilledTonalButton`（`secondaryContainer` / `onSecondaryContainer`） |
| Miuix | `MiuixButton` + `secondaryVariant` 容器色 |

---

## ThemeCustomButton

完全自定义容器色与文案色，不走 Primary/Secondary 规则。

```kotlin
ThemeCustomButton(
    onClick = { },
    containerColor = AnkioTheme.colorScheme.tertiaryContainer,
    contentColor = AnkioTheme.colorScheme.onTertiaryContainer,
    text = "自定义",
)
```

| 参数 | 默认 | 说明 |
|------|------|------|
| `disabledContainerColor` | `containerColor.copy(0.38f)` | |
| `disabledContentColor` | `contentColor.copy(0.38f)` | |

---

## ThemeButtonGroup

连体按钮组容器：水平 `Row`、`fillMaxWidth`、子项间距 **4dp**。

```kotlin
ThemeButtonGroup(modifier = Modifier.fillMaxWidth()) {
  // ThemeGroupButton × N
}
```

---

## ThemeGroupButton

组内单个按钮；根据 `position` 自动裁剪圆角。

| 参数 | 默认 | 说明 |
|------|------|------|
| `position` | `Solo` | `Start` / `Middle` / `End` / `Solo` |
| `style` | `Secondary` | `ThemeButtonStyle` |
| `bigRadius` | `24.dp` | 外侧大圆角 |
| `smallRadius` | `12.dp` | 接缝小圆角 |

| 引擎 | 圆角处理 |
|------|----------|
| Material | `Modifier.clip(groupShape(...))` + 对应 `RoundedCornerShape` |
| Miuix | 成组时 `cornerRadius = 0.dp`，靠外层 clip |

```kotlin
ThemeButtonGroup {
    ThemeGroupButton(
        onClick = { },
        text = "左",
        position = ButtonGroupPosition.Start,
        style = ThemeButtonStyle.Secondary,
    )
    ThemeGroupButton(
        onClick = { },
        text = "右",
        position = ButtonGroupPosition.End,
        style = ThemeButtonStyle.Primary,
    )
}
```

---

## ThemeGroupCustomButton

组内自定义色按钮（`ThemeButtonStyle.Custom` 的便捷封装），参数同 `ThemeCustomButton` + `position` / `bigRadius` / `smallRadius`。

---

## ThemeFloatingActionButton

浮动操作按钮（FAB）。

```kotlin
ThemeFloatingActionButton(onClick = { }) {
    ThemeIcon(
        imageVector = Icons.Filled.Add,
        contentDescription = null,
        tint = AnkioTheme.colorScheme.onPrimaryContainer,
    )
}
```

| 参数 | 说明 |
|------|------|
| `onClick` | 点击 |
| `modifier` | |
| `content` | 通常为 `ThemeIcon` |

**双栈统一**：`primaryContainer` 底 + `onPrimaryContainer` 前景（修正 Miuix 默认 `primary` 底在 Monet 下撞色）。

---

## 注意事项

- 按钮内文字优先 `ThemeButtonLabel` 或 `text` 重载，勿在按钮外单独算字色。
- 组按钮务必设置正确的 `ButtonGroupPosition`，否则圆角接缝会错。

## Demo

`app` → **按钮**
