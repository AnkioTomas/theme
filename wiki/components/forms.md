# 表单与输入

包名：`net.ankio.theme.compat`

---

## ThemeTextField

统一文本输入：`value` + `onValueChange`。

```kotlin
var text by remember { mutableStateOf("") }

ThemeTextField(
    value = text,
    onValueChange = { text = it },
    modifier = Modifier.fillMaxWidth(),
    style = TextFieldStyle.Outlined, // 或 Filled
    label = "用户名",
    placeholder = "请输入",
    supportingText = {
        ThemeText(
            text = "辅助说明",
            style = AnkioTheme.textStyles.footnote1,
            color = AnkioTheme.colorScheme.onSurfaceVariant,
        )
    },
    isError = false,
    enabled = true,
    readOnly = false,
    leadingIcon = {
        ThemeIcon(
            imageVector = Icons.Filled.Person,
            contentDescription = null,
            tint = AnkioTheme.colorScheme.onSurfaceVariant,
        )
    },
    trailingIcon = null,
    singleLine = true,
    maxLines = 1,
    minLines = 1,
    keyboardOptions = KeyboardOptions.Default,
    keyboardActions = KeyboardActions.Default,
    textStyle = AnkioTheme.textStyles.main,
)
```

| 参数 | 说明 |
|------|------|
| `style` | `TextFieldStyle.Outlined` / `Filled` |
| `label` | 空字符串时不显示标签 |
| `placeholder` | 占位文案 |
| `supportingText` | 框下说明；错误时配合 `isError` |
| `leadingIcon` / `trailingIcon` | 图标槽；须自行传 `ThemeIcon` + `tint` |

### Miuix vs Material

| 项 | Material | Miuix |
|----|----------|-------|
| 控件 | `OutlinedTextField` / `TextField` | 始终 `MiuixTextField` |
| `style` | 区分 Outlined / Filled | **忽略**，背景为 `secondaryContainer` |
| 占位 | 独立 `placeholder` | 无 label 时 `placeholder` 作 label + `useLabelAsPlaceholder` |
| 图标 | 原生槽 | 外包 12dp 水平 padding + `onSurfaceVariant` tint |
| 辅助文字 | 原生 `supportingText` | 手动画在 `Column` 下方 |

设置页封装：[ThemeSettingTextField](../settings-widgets.md#themesettingtextfield)。

---

## ThemeSearchBar

可展开搜索栏。

```kotlin
var query by remember { mutableStateOf("") }
var expanded by remember { mutableStateOf(false) }

ThemeSearchBar(
    query = query,
    onQueryChange = { query = it },
    expanded = expanded,
    onExpandedChange = { expanded = it },
    modifier = Modifier.fillMaxWidth(),
    onSearch = { /* 提交搜索 */ },
) {
    // expanded=true 时显示的建议列表等区域
    ThemeText(
        text = "搜索结果",
        style = AnkioTheme.textStyles.body2,
        color = AnkioTheme.colorScheme.onSurfaceVariant,
    )
}
```

| 引擎 | 实现 |
|------|------|
| Material | Material3 `SearchBar`（`active` ↔ `expanded`） |
| Miuix | `MiuixSearchBar` + `InputField` |

---

## ThemeSwitch

开关。

```kotlin
ThemeSwitch(
    checked = checked,
    onCheckedChange = { checked = it },
    modifier = Modifier,
    enabled = true,
)
```

Material `Switch` ↔ Miuix `MiuixSwitch`，行为一致。

---

## ThemeCheckbox

复选框；API 同 `ThemeSwitch`。

---

## ThemeSlider

滑块。

```kotlin
ThemeSlider(
    value = progress,
    onValueChange = { progress = it },
    modifier = Modifier,
    enabled = true,
    valueRange = 0f..1f,
    steps = 0,
    onValueChangeFinished = null,
)
```

| 引擎 | 差异 |
|------|------|
| Material | 标准 `Slider` |
| Miuix | `showKeyPoints = true` |

---

## ThemeNumberPicker

数字滚轮选择器。**两栈均使用 Miuix `NumberPicker`**（Material 无对等组件）。

```kotlin
ThemeNumberPicker(
    value = number,
    onValueChange = { number = it },
    modifier = Modifier,
    enabled = true,
    range = 0..10,
    label = { it.toString() },
    visibleItemCount = 5,
    wrapAround = false,
    textStyle = AnkioTheme.textStyles.title1,
)
```

Studio 预览在 `LocalInspectionMode` 下有静态占位（无 `onSizeChanged` 时）。

---

## ThemeSuperSpinner

带标题行的下拉选择（设置项常用）。

```kotlin
import top.yukonga.miuix.kmp.basic.SpinnerEntry

ThemeSuperSpinner(
    items = listOf(
        SpinnerEntry(title = "选项 A"),
        SpinnerEntry(title = "选项 B", summary = "说明"),
    ),
    selectedIndex = index,
    onSelectedIndexChange = { index = it },
    title = "标题",
    summary = "副标题",
    startAction = { /* 左侧 40dp 图标槽 */ },
    modifier = Modifier,
    shape = MaterialTheme.shapes.medium,
    enabled = true,
    showValue = true,
)
```

| 引擎 | 行为 |
|------|------|
| Material | 可点击 `ThemeCard` + `Popup` + `DropdownMenuItem`；锚点右对齐，空间不足时翻到上方 |
| Miuix | `ThemeCard` 包 `MiuixWindowSpinner`；`startAction` 放入 40dp 槽对齐标题 |

---

## ThemeSuperDropdown

`ThemeSuperSpinner` 的字符串列表简化版：

```kotlin
ThemeSuperDropdown(
    items = listOf("选项 A", "选项 B"),
    selectedIndex = index,
    onSelectedIndexChange = { index = it },
    title = "选择",
    summary = null,
    startAction = null,
    enabled = true,
    showValue = true,
)
```

内部将 `String` 转为 `SpinnerEntry`。

---

## Demo

`app` → **表单控件**、**下拉与搜索**
