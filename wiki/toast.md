# ThemeToast

包名：`net.ankio.theme.toast`

Compose 风格 Toast，通过 `TYPE_APPLICATION_OVERLAY` 显示；无权限时降级系统 `Toast`。

## 初始化

```kotlin
ThemeToast.init(application)
```

## 权限

```kotlin
if (!ThemeToast.hasOverlayPermission(context)) {
    ThemeToast.requestOverlayPermission(context)
}
```

在 `onResume` 中重新检查权限状态。

## 显示

```kotlin
ThemeToast.show(
    message = "操作成功",
    style = ThemeToast.Style.Success,
    config = ThemeToast.Config(
        position = ThemeToast.Position.Bottom,
        offsetX = 0,
        offsetY = 0,
    ),
    trailingContent = null, // 可选 Composable
)
```

### Style

`Debug` | `Error` | `Success` | `Warning` | `Info`

使用 `appExtraColors` 饱和色 + 白字，与页面内语义条区分。

### Position

`Top` | `Center` | `Bottom`

```kotlin
ThemeToast.Config.fromPosition("top") // 字符串便捷构造
```

## 实现说明

- `ComposeView` + `OverlayLifecycleOwner`（`net.ankio.theme.compose`）
- 默认显示 3 秒后自动消失
- 新 Toast 会替换当前悬浮层

## Demo

`app` → **Toast**（含权限引导）
