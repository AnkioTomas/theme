# Ankio Theme Wiki

Compose 主题与组件库文档。业务层只写一套 `Theme*` API，在 **Miuix** 与 **Material 3** 之间由 `LocalUiMode` 自动切换。

## 文档导航

| 文档 | 说明 |
|------|------|
| [快速开始](getting-started.md) | 依赖、初始化、`BaseComposeActivity` |
| [主题体系](theme-system.md) | `ThemeSettings`、`AutoTheme`、`AnkioTheme`、颜色与排版 |
| [组件总览](components/README.md) | 全部 `Theme*` 组件索引 |
| [按钮](components/buttons.md) | Primary / Secondary / FAB / ButtonGroup |
| [文本与图标](components/text-icons.md) | Text / Icon / IconButton / SmallTitle |
| [容器](components/containers.md) | Surface / Card / FloatingToolbar |
| [表单与输入](components/forms.md) | TextField / Switch / Spinner / SearchBar 等 |
| [导航](components/navigation.md) | TopAppBar / NavigationBar / TabRow / Rail |
| [反馈与进度](components/feedback.md) | Progress / PullToRefresh / Divider |
| [弹窗与列表](components/popups.md) | SuperListPopup / ListPopupItem |
| [设置页组件](settings-widgets.md) | SettingCard / ThemeSetting* / UiSettingsScreen |
| [Toast](toast.md) | `ThemeToast` 悬浮窗提示 |
| [底部弹层](sheet.md) | `ThemeSheet` / `ThemeBottomSheet` |
| [预览与 Demo](preview-and-demo.md) | `@PreviewAll`、演示 App 目录 |
| [架构与设计](architecture.md) | 模块结构、设计原则、Miuix/Material 差异 |

## 环境要求

| 项 | 值 |
|---|---|
| minSdk | 30 |
| Java | 17 |
| Compose | BOM 2025.08+ |
| 依赖 | Miuix KMP 0.8.7、Material 3 |

## 包名速查

| 包 | 内容 |
|----|------|
| `net.ankio.theme` | 主题入口、`ThemeSettings`、`AutoTheme` |
| `net.ankio.theme.compat` | 全部 `Theme*` 兼容组件 |
| `net.ankio.theme.settings` | 设置页原子组件、`UiSettingsScreen` |
| `net.ankio.theme.toast` | `ThemeToast` |
| `net.ankio.theme.sheet` | `ThemeSheet`、`ThemeBottomSheet` |
| `net.ankio.theme.compose` | `OverlayLifecycleOwner`（内部/悬浮窗 Compose） |

## 相关链接

- [项目 README](../README.md)（接入与发布）
- [JitPack](https://jitpack.io/#AnkioTomas/theme)
- 交互演示：运行 `app` 模块，浏览 **组件目录**
