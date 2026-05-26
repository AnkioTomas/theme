# 表单与输入

## ThemeTextField

统一 `value` + `onValueChange` API。

```kotlin
var text by remember { mutableStateOf("") }

ThemeTextField(
    value = text,
    onValueChange = { text = it },
    modifier = Modifier.fillMaxWidth(),
    style = TextFieldStyle.Outlined, // 或 Filled
    label = "用户名",
    placeholder = "请输入",
    supportingText = { Text("辅助说明") },
    isError = false,
    enabled = true,
    readOnly = false,
    leadingIcon = { ThemeIcon(Icons.Filled.Person, null) },
    trailingIcon = null,
    singleLine = true,
    maxLines = 1,
    keyboardOptions = KeyboardOptions.Default,
    textStyle = AnkioTheme.textStyles.main,
)
```

| 参数 | 说明 |
|------|------|
| `style` | `Outlined` / `Filled`（仅 Material 区分明显） |
| `label` | 空字符串时不显示标签 |
| Miuix | `placeholder` 无独立占位时可作 label 占位 |

设置页封装见 [ThemeSettingTextField](../settings-widgets.md)（图标在框内、标题为 label）。

## ThemeSearchBar

```kotlin
var query by remember { mutableStateOf("") }
var expanded by remember { mutableStateOf(false) }

ThemeSearchBar(
    query = query,
    onQueryChange = { query = it },
    expanded = expanded,
    onExpandedChange = { expanded = it },
    onSearch = { },
    placeholder = "搜索",
) {
    // 展开后建议列表等
}
```

## ThemeSwitch / ThemeCheckbox

```kotlin
ThemeSwitch(checked = checked, onCheckedChange = { checked = it })
ThemeCheckbox(checked = checked, onCheckedChange = { checked = it })
```

## ThemeSlider

```kotlin
ThemeSlider(
    value = progress,
    onValueChange = { progress = it },
    valueRange = 0f..100f,
    steps = 0,
)
```

## ThemeNumberPicker

```kotlin
ThemeNumberPicker(
    value = number,
    onValueChange = { number = it },
    range = 1..10,
)
```

## ThemeSuperSpinner

带标题、副标题的下拉（设置项常用），条目为 Miuix `SpinnerEntry`：

```kotlin
import top.yukonga.miuix.kmp.basic.SpinnerEntry

ThemeSuperSpinner(
    items = listOf(SpinnerEntry(title = "A"), SpinnerEntry(title = "B")),
    selectedIndex = index,
    onSelectedIndexChange = { index = it },
    title = "标题",
    summary = "说明",
    startAction = { /* 左侧图标 */ },
    enabled = true,
)
```

## ThemeSuperDropdown

纯字符串列表简化版：

```kotlin
ThemeSuperDropdown(
    items = listOf("选项 A", "选项 B"),
    selectedIndex = index,
    onSelectedIndexChange = { index = it },
    title = "选择",
)
```

## Demo

`app` → **表单控件**、**下拉与搜索**
