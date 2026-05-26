# 底部弹层

包名：`net.ankio.theme.sheet`

纯 Compose：**无 ViewBinding、无 `BottomSheetDialog`、无 XML**。

提供两种入口：**Compose 树内**用 `ThemeBottomSheet`；**任意 Context** 用 `ThemeSheet.show`。

---

## 视觉规范

| 项 | 值 |
|----|-----|
| 圆角半径 | `ThemeSheetTopCorner = 40.dp`（顶圆角 / 全圆角共用） |
| 背景色 | `AnkioTheme.colorScheme.surfaceContainerHigh` |
| 内容区内边距 | 水平 24dp、垂直 20dp |
| `TopRounded` 底边 | 面板贴屏幕底；**内容区** `navigationBarsPadding()` 防挡字 |
| `FullyRounded` 底边 | 整卡 `navigationBarsPadding()` + 额外 `ThemeSheetFullyRoundedBottomMargin`（24dp）；内容区不再重复导航条 padding |
| 水平留白（仅全圆角） | `ThemeSheetFullyRoundedHorizontalMargin = 16.dp` |
| 遮罩 | 全屏含状态栏 / 刘海（`OverlayWindow.applyOverlayEdgeToEdge`） |

---

## ThemeSheet.show

从 Activity / `LifecycleService` 等 imperative 调起。

```kotlin
ThemeSheet.show(
    context = context,
    cancelable = true,
    onDismiss = { /* 已关闭 */ },
) { dismiss ->
    ThemeText(
        text = "标题",
        style = AnkioTheme.textStyles.title3,
        color = AnkioTheme.colorScheme.onSurface,
    )
    ThemePrimaryButton(onClick = dismiss, text = "关闭")
}
```

| 参数 | 说明 |
|------|------|
| `shape` | `ThemeSheetShape.TopRounded`（默认）或 `FullyRounded`（四角圆角浮卡） |
| `cancelable` | `true`：遮罩点击 + 返回键可关 |
| `onDismiss` | 关闭后回调（含程序 `dismiss()`） |
| `onShowFailed` | 无悬浮窗权限或 `addView` 失败；**不会**触发 `onDismiss` |
| `dismiss` | lambda 参数，传给内容用于关闭按钮 |

### ThemeSheetShape

| 值 | 外形 |
|----|------|
| `TopRounded` | 贴底宽屏，仅顶部 `ThemeSheetTopCorner` 圆角 |
| `FullyRounded` | 四角 `ThemeSheetTopCorner` 圆角浮卡；`fullyRoundedSheetInsets()` 处理左右/底边留白 |

### 悬浮窗权限

Manifest 已合并 `SYSTEM_ALERT_WINDOW`（见 lib `AndroidManifest.xml`）。Android 6+ 还需用户在系统设置中授权「显示在其他应用上层」：

```kotlin
if (!ThemeSheet.hasOverlayPermission(context)) {
    ThemeSheet.requestOverlayPermission(context)
    return
}
ThemeSheet.show(context, onShowFailed = { /* 引导授权 */ }) { dismiss -> ... }
```

### 实现路径

```text
主线程 post
  → 查找 LifecycleOwner（Activity / Service）
  → Settings.canDrawOverlays（否 → onShowFailed）
  → 同步 detach 旧 session（勿 post dismiss，避免清掉新 session）
  → WindowManager.addView（TYPE_APPLICATION_OVERLAY，edge-to-edge）
  → 全屏 ComposeView（setupOverlayEdgeToEdge）
      → SheetTheme（LocalUiMode + AutoTheme）
      → AnimatedSheetOverlay（遮罩 spring 淡入 + 内容 spring 位移，关闭反向）
          → SheetContainer（ThemeSurface + 圆角 + 形状相关 inset）
              → content(dismiss)
```

进出场与 M3 `ModalBottomSheet` 对齐的 spring（`ThemeSheetMotion`：展开 DefaultSpatial，收起 FastEffects；内容仅位移，无额外 alpha）。关闭动画结束后再 `removeView`。

| 项 | 说明 |
|----|------|
| 挂载 | 一律 `WindowManager` + `TYPE_APPLICATION_OVERLAY` |
| 宿主 | Activity / `LifecycleService` 等带 `LifecycleOwner` 的 Context |
| 生命周期 | 宿主 `ON_DESTROY` 时自动 `dismiss()` |
| Session | View 弱引用，避免静态泄漏 |

- `ThemeSheet.dismiss()` 全局关闭当前 session
- **不提供 Studio Preview**（依赖悬浮窗与生命周期）

---

## ThemeBottomSheet

已在 `AutoTheme` 树内时使用 Material3 `ModalBottomSheet`。

```kotlin
var show by remember { mutableStateOf(false) }

if (show) {
    ThemeBottomSheet(onDismissRequest = { show = false }) { dismiss ->
        ThemeText(
            text = "内容",
            style = AnkioTheme.textStyles.body1,
            color = AnkioTheme.colorScheme.onSurface,
        )
        ThemePrimaryButton(onClick = dismiss, text = "关闭")
    }
}
```

| 参数 | 说明 |
|------|------|
| `shape` | `ThemeSheetShape.TopRounded`（默认）或 `FullyRounded` |
| `onDismissRequest` | 下滑 / 返回关闭 |
| `content` | `ColumnScope.(dismiss) -> Unit` |

### 三种风格（Demo 对照）

| 风格 | API | 外形 |
|------|-----|------|
| Overlay 顶圆角 | `ThemeSheet.show(shape = TopRounded)` | 贴底宽屏 |
| Overlay 全圆角 | `ThemeSheet.show(shape = FullyRounded)` | 四角圆角浮卡 |
| Modal 顶圆角 | `ThemeBottomSheet(shape = TopRounded)` | M3 `ModalBottomSheet` |
| Modal 全圆角 | `ThemeBottomSheet(shape = FullyRounded)` | 同 Overlay 全圆角 inset（无悬浮窗） |

| 项 | `ThemeBottomSheet` | `ThemeSheet.show` |
|----|--------------------|-------------------|
| 内容壳 | `ThemeSheetBody`（仅 padding） | `SheetContainer`（含 `ThemeSurface`） |
| 主题 | 继承页面 `AutoTheme` | 独立 `SheetTheme` |

两路径圆角与背景色一致；**未**单独实现 Miuix 原生 BottomSheet。

---

## SheetContainer

`internal`，由 `ThemeSheet.show`（经 `AnimatedSheetOverlay`）组装：`ThemeSurface` + 圆角 + 形状相关外边距/导航条 inset。

`ThemeBottomSheet` 走 M3 `ModalBottomSheet` + `ThemeSheetBody`，不经过 `SheetContainer`。

---

## 公开类型

| 类型 | 说明 |
|------|------|
| `ThemeSheetShape` | `TopRounded` / `FullyRounded` |
| `ThemeSheetTopCorner` 等 | 圆角与全圆角留白常量（`SheetContainer.kt`） |

---

## Demo

`app` → **底部弹层**（Overlay 顶圆角 / Overlay 全圆角 / Modal 顶圆角）

预览：

| 文件 | 内容 |
|------|------|
| `preview/sheet/ThemeSheetPreview.kt` | `ThemeBottomSheet`（`@PreviewAllScreen`） |
| `preview/sheet/SheetContainerPreview.kt` | 顶圆角 `SheetContainer` |
| `preview/sheet/SheetContainerFullyRoundedPreview.kt` | 全圆角 `SheetContainer` |
