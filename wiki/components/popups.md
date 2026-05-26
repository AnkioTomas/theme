# 弹窗与列表

Miuix 使用 **Window 级** Popup，无需 `Scaffold`。

## ThemeSuperListPopup

```kotlin
var show by remember { mutableStateOf(false) }

ThemeSuperListPopup(
    show = show,
    onDismissRequest = { show = false },
    enableContainerScroll = true, // 长列表时设为 false，配合 Lazy 列
) {
    ThemeListPopupColumn {
        ThemeListPopupItem(text = "编辑", onClick = { show = false })
        ThemeListPopupItem(text = "删除", onClick = { show = false })
    }
}
```

## ThemeListPopupColumn

固定高度可滚动的列表容器。

## ThemeLazyListPopupColumn

大数据量懒加载：

```kotlin
ThemeSuperListPopup(
    show = show,
    onDismissRequest = { show = false },
    enableContainerScroll = false,
) {
    ThemeLazyListPopupColumn(items = list) { item ->
        ThemeListPopupItem(text = item.name) { /* ... */ }
    }
}
```

## ThemeListPopupItem

单行菜单项，支持 `leadingIcon`。

## Demo

`app` → **下拉与搜索**
