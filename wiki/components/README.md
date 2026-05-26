# 组件总览

所有 `Theme*` 组件位于 `net.ankio.theme.compat`，根据 `LocalUiMode` 在 Miuix / Material 间切换。**无需**在业务代码里写 `if (Miuix) … else …`。

## 分类索引

| 分类 | 文档 | 主要组件 |
|------|------|----------|
| 按钮 | [buttons.md](buttons.md) | `ThemePrimaryButton`, `ThemeSecondaryButton`, `ThemeButtonGroup`, `ThemeFAB` |
| 文本/图标 | [text-icons.md](text-icons.md) | `ThemeText`, `ThemeIcon`, `ThemeIconButton`, `ThemeSmallTitle` |
| 容器 | [containers.md](containers.md) | `ThemeSurface`, `ThemeCard`, `ThemeFloatingToolbar` |
| 表单 | [forms.md](forms.md) | `ThemeTextField`, `ThemeSwitch`, `ThemeSuperSpinner` 等 |
| 导航 | [navigation.md](navigation.md) | `ThemeTopAppBar`, `ThemeNavigationBar`, `ThemeTabRow` |
| 反馈 | [feedback.md](feedback.md) | `ThemeProgress*`, `ThemePullToRefresh`, `ThemeDivider` |
| 弹窗 | [popups.md](popups.md) | `ThemeSuperListPopup`, `ThemeListPopupItem` |

## 设置 / Toast / Sheet（独立包）

| 组件 | 包 | 文档 |
|------|-----|------|
| `SettingCard`, `ThemeSetting*` | `settings` | [settings-widgets.md](../settings-widgets.md) |
| `UiSettingsScreen` | `settings` | 同上 |
| `ThemeToast` | `toast` | [toast.md](../toast.md) |
| `ThemeSheet` | `sheet` | [sheet.md](../sheet.md) |
| `ThemeBottomSheet` | `sheet` | 同上 |

## 通用约定

1. **颜色**：优先 `AnkioTheme.colorScheme.*`，勿硬编码色值。
2. **文字**：使用 `AnkioTheme.textStyles.*`，保证 Miuix/Material 字号一致。
3. **无默认外边距**：组件默认不偷偷加 padding（如 `ThemeSmallTitle` 为 0），由页面布局控制。
4. **Window 级 Popup**：Miuix 下拉/列表弹窗走 Window 变体，不依赖 `Scaffold`。

## 最小示例

```kotlin
@Composable
fun SampleScreen() {
    Column(Modifier.padding(16.dp)) {
        ThemePrimaryButton(onClick = {}) {
            ThemeText("确认", style = AnkioTheme.textStyles.button,
                color = AnkioTheme.colorScheme.onPrimary)
        }
        ThemeTextField(
            value = text,
            onValueChange = { text = it },
            label = "用户名",
        )
    }
}
```

完整交互见 `app` 模块 **组件目录**（18 个分类）。
