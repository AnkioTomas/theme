# 底部弹层

包名：`net.ankio.theme.sheet`

纯 Compose：**无 ViewBinding、无 `BottomSheetDialog`、无 XML style**。

## ThemeSheet.show

从 `Context` 调起（Activity 挂到 `decorView`；`LifecycleService` 走悬浮窗）：

```kotlin
ThemeSheet.show(context) { dismiss ->
    ThemeText("标题", style = AnkioTheme.textStyles.title3)
    ThemePrimaryButton(onClick = dismiss) {
        ThemeText("关闭")
    }
}
```

| 参数 | 说明 |
|------|------|
| `cancelable` | 默认可点遮罩 / 返回键关闭 |
| `onDismiss` | 关闭后回调 |

实现：`ComposeView` + Compose [`Dialog`](https://developer.android.com/reference/kotlin/androidx/compose/ui/window/package-summary#Dialog(kotlin.Function0,androidx.compose.ui.window.DialogProperties,kotlin.Function0)) + 底部 [`SheetContainer`](SheetContainer.kt)，与 Toast 同属 overlay 方案。

全局关闭：`ThemeSheet.dismiss()`

## ThemeBottomSheet

已在 Compose 树内时使用 Material3 `ModalBottomSheet`（同样 32dp 顶部圆角）：

```kotlin
var show by remember { mutableStateOf(false) }

if (show) {
    ThemeBottomSheet(onDismissRequest = { show = false }) { dismiss ->
        ThemePrimaryButton(onClick = dismiss) { ThemeText("关闭") }
    }
}
```

## 圆角

`ThemeSheetTopCorner = 32.dp`，`show` 与 `ThemeBottomSheet` 共用 [`SheetContainer`](SheetContainer.kt)。

## Demo

`app` → **底部弹层**
