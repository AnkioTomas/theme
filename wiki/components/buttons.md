# 按钮

包名：`net.ankio.theme.compat`

## ThemePrimaryButton

主操作按钮，Miuix / Material 主色填充。

```kotlin
ThemePrimaryButton(onClick = { }) {
    ThemeText(
        text = "确认",
        style = AnkioTheme.textStyles.button,
        color = themePrimaryButtonContentColor(),
    )
}
```

## ThemeSecondaryButton

次要按钮，容器色背景。文案色请用 [themeSecondaryButtonContentColor]（Miuix 为 `onSurfaceContainer`）。

```kotlin
ThemeSecondaryButton(onClick = { }) {
    ThemeText(
        text = "取消",
        style = AnkioTheme.textStyles.button,
        color = themeSecondaryButtonContentColor(),
    )
}
```

## ThemeButtonGroup / ThemeGroupButton

连体按钮组，按位置自动圆角。

```kotlin
ThemeButtonGroup {
    ThemeGroupButton(onClick = {}, position = ButtonGroupPosition.Start) {
        ThemeText("左")
    }
    ThemeGroupButton(onClick = {}, position = ButtonGroupPosition.Middle) {
        ThemeText("中")
    }
    ThemeGroupButton(onClick = {}, position = ButtonGroupPosition.End) {
        ThemeText("右")
    }
}
```

`ButtonGroupPosition`：`Start` | `Middle` | `End` | `Solo`

## ThemeFloatingActionButton

```kotlin
ThemeFloatingActionButton(onClick = { }) {
    ThemeIcon(Icons.Filled.Add, null, tint = AnkioTheme.colorScheme.onPrimary)
}
```

## Demo

`app` → **按钮** 分类
