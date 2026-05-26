# 按钮

包名：`net.ankio.theme.compat`

## ThemeButtonStyle

| 风格 | 说明 |
|------|------|
| `Primary` | Miuix：`buttonColorsPrimary` + `onPrimary` 文案 |
| `Secondary` | Miuix：`secondaryVariant` 底 + `onSurfaceContainer` 文案 |
| `Custom(container, content, …)` | 容器色 / 文案色完全自定义 |

## ThemePrimaryButton / ThemeSecondaryButton

文案色在组件内注入，推荐 `ThemeButtonLabel` 或 `text` 重载，无需手写 `theme*ButtonContentColor()`。

```kotlin
ThemePrimaryButton(onClick = { }, text = "确认")

ThemeSecondaryButton(onClick = { }) {
    ThemeButtonLabel("取消")
}
```

高级自定义内容仍可用 `themePrimaryButtonContentColor()` / `themeSecondaryButtonContentColor()`。

## ThemeCustomButton

不参与 Primary / Secondary 默认规则：

```kotlin
ThemeCustomButton(
    onClick = { },
    containerColor = AnkioTheme.colorScheme.tertiaryContainer,
    contentColor = AnkioTheme.colorScheme.onTertiaryContainer,
    text = "自定义",
)
```

## ThemeButtonGroup / ThemeGroupButton

组内按钮通过 `style` 选择风格，默认 `Secondary`：

```kotlin
ThemeButtonGroup {
    ThemeGroupButton(
        onClick = { },
        text = "左",
        position = ButtonGroupPosition.Start,
        style = ThemeButtonStyle.Primary,
    )
    ThemeGroupButton(
        onClick = { },
        text = "中",
        position = ButtonGroupPosition.Middle,
        style = ThemeButtonStyle.Secondary,
    )
    ThemeGroupCustomButton(
        onClick = { },
        position = ButtonGroupPosition.End,
        containerColor = Color.Red,
        contentColor = Color.White,
        text = "右",
    )
}
```

`ButtonGroupPosition`：`Start` | `Middle` | `End` | `Solo`

## ThemeFloatingActionButton

```kotlin
ThemeFloatingActionButton(onClick = { }) {
    ThemeIcon(Icons.Filled.Add, null, tint = AnkioTheme.colorScheme.onPrimaryContainer)
}
```

## Demo

`app` → **按钮** 分类
