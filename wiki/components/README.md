# 组件总览

包名：`net.ankio.theme.compat`（设置 / Toast / Sheet 见各独立文档）。

所有组件通过 `when (LocalUiMode.current)` 在 Miuix 与 Material 间切换，**业务层不写分支**。

---

## 完整组件索引

| 组件 | 分类 | 文档 |
|------|------|------|
| `ThemePrimaryButton` | 按钮 | [buttons.md](buttons.md#themeprimarybutton) |
| `ThemeSecondaryButton` | 按钮 | [buttons.md](buttons.md#themesecondarybutton) |
| `ThemeCustomButton` | 按钮 | [buttons.md](buttons.md#themecustombutton) |
| `ThemeButtonLabel` | 按钮 | [buttons.md](buttons.md#themebuttonlabel) |
| `ThemeButtonGroup` | 按钮 | [buttons.md](buttons.md#themebuttongroup) |
| `ThemeGroupButton` | 按钮 | [buttons.md](buttons.md#themegroupbutton) |
| `ThemeGroupCustomButton` | 按钮 | [buttons.md](buttons.md#themegroupcustombutton) |
| `ThemeButtonStyle` | 按钮 | [buttons.md](buttons.md#themebuttonstyle) |
| `ThemeFloatingActionButton` | 按钮 | [buttons.md](buttons.md#themefloatingactionbutton) |
| `ThemeText` | 文本 | [text-icons.md](text-icons.md#themetext) |
| `ThemeTextStyles` | 文本 | [text-icons.md](text-icons.md#themetextstyles) |
| `ThemeSmallTitle` | 文本 | [text-icons.md](text-icons.md#themesmalltitle) |
| `ThemeIcon` | 图标 | [text-icons.md](text-icons.md#themeicon) |
| `ThemeIconButton` | 图标 | [text-icons.md](text-icons.md#themeiconbutton) |
| `ThemeSurface` | 容器 | [containers.md](containers.md#themesurface) |
| `ThemeCard` | 容器 | [containers.md](containers.md#themecard) |
| `ThemeFloatingToolbar` | 容器 | [containers.md](containers.md#themefloatingtoolbar) |
| `ThemeTextField` | 表单 | [forms.md](forms.md#themetextfield) |
| `ThemeSearchBar` | 表单 | [forms.md](forms.md#themesearchbar) |
| `ThemeSwitch` | 表单 | [forms.md](forms.md#themeswitch) |
| `ThemeCheckbox` | 表单 | [forms.md](forms.md#themecheckbox) |
| `ThemeSlider` | 表单 | [forms.md](forms.md#themeslider) |
| `ThemeNumberPicker` | 表单 | [forms.md](forms.md#themenumberpicker) |
| `ThemeSuperSpinner` | 表单 | [forms.md](forms.md#themesuperspinner) |
| `ThemeSuperDropdown` | 表单 | [forms.md](forms.md#themesuperdropdown) |
| `ThemeTopAppBar` | 导航 | [navigation.md](navigation.md#themetopappbar) |
| `ThemeTopAppBarTitleAlignment` | 导航 | [navigation.md](navigation.md#themetopappbartitlealignment) |
| `ThemeTopAppBarScroll` | 导航 | [navigation.md](navigation.md#滚动折叠) |
| `rememberThemeTopAppBarScroll` | 导航 | [navigation.md](navigation.md#滚动折叠) |
| `ThemeNavigationBar` | 导航 | [navigation.md](navigation.md#themenavigationbar) |
| `ThemeNavigationBarItem` | 导航 | [navigation.md](navigation.md#themenavigationbaritem) |
| `ThemeNavigationRail` | 导航 | [navigation.md](navigation.md#themenavigationrail) |
| `ThemeTabRow` | 导航 | [navigation.md](navigation.md#themetabrow) |
| `ThemeLinearProgressIndicator` | 反馈 | [feedback.md](feedback.md#themelinearprogressindicator) |
| `ThemeCircularProgressIndicator` | 反馈 | [feedback.md](feedback.md#themecircularprogressindicator) |
| `ThemePullToRefresh` | 反馈 | [feedback.md](feedback.md#themepulltorefresh) |
| `ThemeHorizontalDivider` | 反馈 | [feedback.md](feedback.md#themehorizontaldivider) |
| `ThemeVerticalDivider` | 反馈 | [feedback.md](feedback.md#themeverticaldivider) |
| `ThemeSuperListPopup` | 弹窗 | [popups.md](popups.md#themesuperlistpopup) |
| `ThemeListPopupColumn` | 弹窗 | [popups.md](popups.md#themelistpopupcolumn) |
| `ThemeLazyListPopupColumn` | 弹窗 | [popups.md](popups.md#themelazylistpopupcolumn) |
| `ThemeListPopupItem` | 弹窗 | [popups.md](popups.md#themelistpopupitem) |
| `ThemeColorScheme` | 令牌 | [theme-system.md](../theme-system.md#ankiotheme) |
| `SettingCard` / `ThemeSettingTextField` / `ThemeSettingDropdown` 等 | 设置 | [settings-widgets.md](../settings-widgets.md) |
| `ThemeSheet` / `ThemeBottomSheet` / `ThemeSheetShape` | Sheet | [sheet.md](../sheet.md) |
| `ThemeToast` | Toast | [toast.md](../toast.md) |

---

## 分类文档

| 分类 | 文档 |
|------|------|
| 按钮 | [buttons.md](buttons.md) |
| 文本/图标 | [text-icons.md](text-icons.md) |
| 容器 | [containers.md](containers.md) |
| 表单 | [forms.md](forms.md) |
| 导航 | [navigation.md](navigation.md) |
| 反馈 | [feedback.md](feedback.md) |
| 弹窗 | [popups.md](popups.md) |

---

## 通用约定

| 约定 | 说明 |
|------|------|
| 颜色 | `AnkioTheme.colorScheme`，含 `semantic.*` |
| 排版 | `AnkioTheme.textStyles`（`title1`–`title4`、`body1/2`、`button` 等） |
| 默认外边距 | 组件一般不偷偷加 padding；列表/页面自行 `padding` |
| Window Popup | Miuix 下拉/列表走 Window 变体，不依赖 `Scaffold` |
| 预览 | `lib/src/debug/.../preview/`，`@PreviewAll` + `PreviewHost` |

---

## 最小示例

```kotlin
@Composable
fun SampleScreen() {
    Column(Modifier.padding(16.dp)) {
        ThemePrimaryButton(onClick = {}, text = "确认")
        ThemeTextField(
            value = text,
            onValueChange = { text = it },
            label = "用户名",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
```

完整交互见 `app` 模块 **组件目录**（18+ 分类 Section）。
