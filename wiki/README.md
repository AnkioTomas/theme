# Ankio Theme Wiki

Compose 主题与组件库：**一套 `Theme*` API**，在 **Miuix KMP** 与 **Material 3** 之间由 `LocalUiMode` 自动切换。业务代码不写 `if (Miuix)`。

---

## 文档导航

### 入门与主题

| 文档 | 说明 |
|------|------|
| [快速开始](getting-started.md) | JitPack 依赖、`ThemeSettings.init`、`BaseComposeActivity` |
| [主题体系](theme-system.md) | `AutoTheme`、`AnkioTheme`、`ColorMode`、语义色、形状 |
| [架构与设计](architecture.md) | 模块划分、设计原则、Miuix/Material 差异总表 |

### 组件（compat）

| 文档 | 组件 |
|------|------|
| [组件总览与完整索引](components/README.md) | 全部 `Theme*` 一览表 |
| [按钮](components/buttons.md) | Primary / Secondary / Custom / ButtonGroup / FAB |
| [文本与图标](components/text-icons.md) | Text / Icon / IconButton / SmallTitle |
| [容器](components/containers.md) | Surface / Card / FloatingToolbar |
| [表单与输入](components/forms.md) | TextField / Switch / Slider / SearchBar / Spinner 等 |
| [导航](components/navigation.md) | TopAppBar / NavigationBar / Rail / TabRow |
| [反馈与进度](components/feedback.md) | Progress / PullToRefresh / Divider |
| [弹窗与列表](components/popups.md) | SuperListPopup / SuperSpinner / SuperDropdown |

### 独立模块

| 文档 | 说明 |
|------|------|
| [设置页组件](settings-widgets.md) | SettingCard、ThemeSetting*、`UiSettingsScreen` |
| [底部弹层](sheet.md) | `ThemeSheet.show`、`ThemeBottomSheet`、`ThemeSheetShape` |
| [Toast](toast.md) | `ThemeToast` 悬浮窗 |
| [预览与 Demo](preview-and-demo.md) | `@PreviewAll`、`app` 演示目录 |

---

## 环境要求

| 项 | 值 |
|---|---|
| minSdk | 30 |
| Java | 17 |
| Compose | BOM 2025.08+ |
| 依赖 | Miuix KMP 0.8.7、Material 3 |

---

## 包名速查

| 包 | 内容 |
|----|------|
| `net.ankio.theme` | `AutoTheme`、`ThemeSettings`、`AnkioTheme`、`BaseComposeActivity` |
| `net.ankio.theme.compat` | 全部 `Theme*` 兼容组件 |
| `net.ankio.theme.settings` | 设置列表原子组件、`UiSettingsScreen` |
| `net.ankio.theme.toast` | `ThemeToast` |
| `net.ankio.theme.sheet` | `ThemeSheet`、`ThemeBottomSheet` |
| `net.ankio.theme.compose` | `OverlayLifecycleOwner`（Toast/Sheet overlay） |

---

## 三条通用约定

1. **颜色与排版**：`AnkioTheme.colorScheme.*`、`AnkioTheme.textStyles.*`，勿硬编码。
2. **`ThemeText` 必须传 `color`**：按钮内用 `ThemeButtonLabel` 或 `text` 重载，由组件注入字色。
3. **预览与运行时**：`AnkioTheme` 须在 `AutoTheme` / `PreviewAllThemes` 子树内读取（见 [预览](preview-and-demo.md)）。

---

## 相关链接

- [项目 README](../README.md)（JitPack 版本与发布）
- [JitPack](https://jitpack.io/#AnkioTomas/theme)
- 交互演示：运行 `app` → **组件目录**
