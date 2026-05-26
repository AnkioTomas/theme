# 弹窗与列表

包名：`net.ankio.theme.compat`

Miuix 使用 **Window 级** Popup（`WindowListPopup` / `WindowSpinner`），**不依赖 `Scaffold`**。Material 侧用 `Popup` + 自定义 `PositionProvider` 尽量对齐锚点行为。

---

## ThemeSuperListPopup

通用列表弹层。

```kotlin
var show by remember { mutableStateOf(false) }

ThemeSuperListPopup(
    show = show,
    onDismissRequest = { show = false },
    modifier = Modifier, // popup 容器
    popupPositionProvider = ContextMenuPositionProvider, // Miuix 用；Material 主要用 alignment
    alignment = Alignment.TopStart,
    offset = IntOffset.Zero,
    maxHeight = null,
    minWidth = 200.dp,
    enableContainerScroll = true,
) {
    ThemeListPopupColumn {
        ThemeListPopupItem(text = "编辑", onClick = { show = false })
        ThemeListPopupItem(text = "删除", onClick = { show = false })
    }
}
```

| 参数 | 说明 |
|------|------|
| `show` | `false` 时直接 return，不组合 |
| `enableContainerScroll` | 长列表用 `Lazy` 时设为 `false` |
| `minWidth` | 最小宽度 200dp |

| 引擎 | 行为 |
|------|------|
| Material | `Popup` + `Surface` + 可滚动 `Column` |
| Miuix | `MiuixWindowListPopup` |

---

## ThemeListPopupColumn

弹层内列表容器。

| 引擎 | 实现 |
|------|------|
| Material | 普通 `Column` |
| Miuix | `ListPopupColumn`（自适应宽度） |

---

## ThemeLazyListPopupColumn

懒加载列表（大数据量）。

```kotlin
ThemeSuperListPopup(
    show = show,
    onDismissRequest = { show = false },
    enableContainerScroll = false,
) {
    ThemeLazyListPopupColumn(items = list, key = { it.id }) { item ->
        ThemeListPopupItem(text = item.name, onClick = { /* ... */ })
    }
}
```

两栈均基于 `LazyColumn`。

---

## ThemeListPopupItem

单行菜单项：透明可点击行 + `ThemeText`（`body1`）。

```kotlin
ThemeListPopupItem(
    text = "复制",
    onClick = { show = false },
    leadingIcon = { /* 可选 */ },
)
```

---

## 与 Spinner 的关系

- 下拉选择行 UI：[ThemeSuperSpinner](forms.md#themesuperspinner) / [ThemeSuperDropdown](forms.md#themesuperdropdown)
- 上下文菜单 / 更多操作：`ThemeSuperListPopup`

---

## Demo

`app` → **下拉与搜索**（Popup 示例）
