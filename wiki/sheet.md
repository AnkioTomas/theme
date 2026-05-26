# 底部弹层

包名：`net.ankio.theme.sheet`

纯 Compose：**无 ViewBinding、无 `BottomSheetDialog`、无 XML**。

提供两种入口：**Compose 树内**用 `ThemeBottomSheet`；**任意 Context** 用 `ThemeSheet.show`。

---

## 视觉规范

| 项 | 值 |
|----|-----|
| 顶部圆角 | `ThemeSheetTopCorner = 32.dp` |
| 背景色 | `AnkioTheme.colorScheme.surfaceContainerHigh` |
| 内容区内边距 | 水平 24dp、垂直 20dp |
| 底部安全区 | `navigationBarsPadding()` |

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
| `cancelable` | `true`：遮罩点击 + 返回键可关 |
| `onDismiss` | 关闭后回调（含程序 `dismiss()`） |
| `dismiss` | lambda 参数，传给内容用于关闭按钮 |

### 实现路径

```text
主线程 post
  → 查找 LifecycleOwner（Activity / Service）
  → dismiss 旧 session
  → 全屏 ComposeView
      → SheetTheme（LocalUiMode + AutoTheme）
      → Dialog（全屏）
          → 可选 32% 黑色遮罩（cancelable）
          → SheetContainer（ThemeSurface + 圆角 + padding）
              → content(dismiss)
```

| 宿主 | 挂载方式 |
|------|----------|
| `Activity` | `decorView.addView` 全屏 |
| `LifecycleService` | `WindowManager` + `TYPE_APPLICATION_OVERLAY` |
| 其他 | 不展示（销毁 overlay owner） |

- 宿主 `ON_DESTROY` 时自动关闭
- `ThemeSheet.dismiss()` 全局关闭当前 session
- Session 内 View 使用 `WeakReference`，避免静态泄漏

**不提供 Studio Preview**（依赖 Window / 生命周期）。

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
| `onDismissRequest` | 下滑 / 返回关闭 |
| `content` | `ColumnScope.(dismiss) -> Unit` |

### 与 show 的差异

| 项 | `ThemeBottomSheet` | `ThemeSheet.show` |
|----|--------------------|-------------------|
| 外壳 | `ModalBottomSheet` | `Dialog` + `SheetContainer` |
| 内容壳 | `ThemeSheetBody`（仅 padding） | `SheetContainer`（含 `ThemeSurface`） |
| 主题 | 继承页面 `AutoTheme` | 独立 `SheetTheme` |
| Miuix 手势 | Material 标准 sheet | 同左（Dialog 模拟） |

两路径圆角与背景色一致；**未**单独实现 Miuix 原生 BottomSheet。

---

## SheetContainer

`internal`，仅供 `ThemeSheet.show` 的 Dialog 路径使用：顶部圆角 `ThemeSurface` + 内边距。

---

## Demo

`app` → **底部弹层**

预览：`lib/.../preview/sheet/ThemeBottomSheetPreview.kt`（`@PreviewAllScreen`）
