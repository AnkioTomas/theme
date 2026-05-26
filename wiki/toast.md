# ThemeToast

包名：`net.ankio.theme.toast`

Compose 风格悬浮提示：有 **悬浮窗权限** 时用 `WindowManager` overlay；否则降级系统 `Toast`。

---

## 初始化

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeSettings.init(this)
        ThemeToast.init(this) // 传入 Application
    }
}
```

`init` 保存 `Application` 引用（非 Activity），避免 Context 泄漏。

---

## 权限

Manifest 已声明 `SYSTEM_ALERT_WINDOW`。

```kotlin
if (!ThemeToast.hasOverlayPermission(context)) {
    ThemeToast.requestOverlayPermission(context)
}
```

在 `onResume` 中重新检查；授权后下次 `show` 走 overlay。

---

## show

```kotlin
ThemeToast.show(
    message = "操作成功",
    style = ThemeToast.Style.Success,
    config = ThemeToast.Config(
        position = ThemeToast.Position.Bottom,
        offsetX = 0,
        offsetY = 0,
    ),
    trailingContent = null, // 可选右侧 Composable
)
```

| 参数 | 默认 | 说明 |
|------|------|------|
| `message` | — | 主文案 |
| `style` | `Info` | 见下表 |
| `config` | `Bottom` | 位置与偏移 |
| `trailingContent` | `null` | 右侧自定义内容 |

### Style

| 值 | 视觉 |
|----|------|
| `Debug` | `semantic.debug` 背景 + 白字 |
| `Error` | `semantic.error` |
| `Success` | `semantic.success` |
| `Warning` | `semantic.warning` |
| `Info` | `semantic.info` |

颜色来自 `appExtraColors(darkTheme)`，与页面内 `ThemeText` 语义色一致。

### Position

`Top` | `Center` | `Bottom`（默认）

```kotlin
ThemeToast.Config.fromPosition("top") // 字符串便捷构造
```

---

## 实现说明

| 项 | 说明 |
|----|------|
| 显示时长 | 3 秒后自动 `dismiss` |
| 替换策略 | 新 `show` 先关闭旧 overlay |
| 生命周期 | `OverlayLifecycleOwner` + `ComposeView` |
| 主题 | **固定 Material `MaterialTheme`**（种子色 `0xFF6750A4`），**不**跟随 `LocalUiMode` / Miuix |
| 深色 | `ThemeSettings.shouldUseDarkTheme` 影响语义色 |
| 泄漏防护 | overlay `View` 存 `WeakReference` |

---

## 与 compat 组件的区别

Toast overlay **不走** `AutoTheme` / `Theme*` 组件，避免在系统窗口层引入双栈分支。页面内提示请用 `ThemeText` + `semantic` 色或 Snackbar 类 UI。

---

## Demo

`app` → **Toast**（含权限引导）
