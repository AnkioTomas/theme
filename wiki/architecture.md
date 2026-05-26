# 架构与设计

## 模块结构

```
theme/
├── lib/                    # JitPack 发布模块
│   ├── src/main/.../net/ankio/theme/
│   │   ├── compat/         # Theme* 组件（Miuix ↔ Material）
│   │   ├── settings/       # 设置页组件 + UiSettingsScreen
│   │   ├── toast/          # ThemeToast
│   │   ├── sheet/          # ThemeSheet、ThemeBottomSheet
│   │   └── …
│   └── src/debug/.../preview/   # @PreviewAll 镜像（不进 release）
│       ├── PreviewHost.kt
│       ├── compat/*Preview.kt
│       ├── settings/*Preview.kt
│       └── sheet/*Preview.kt
└── app/                    # 组件演示（非发布）
    └── demo/
        ├── catalog/        # 目录与详情
        ├── gallery/        # 各分类 Section
        ├── gallery/        # SheetSection
        └── preview/        # @PreviewAll
```

## 设计原则

### 1. 一套 API，两套引擎

业务只依赖 `Theme*` 与 `AnkioTheme`。切换 `ThemeSettings.uiMode` 后 `recreate` Activity 即可，无需改 UI 代码。

### 2. 设置驱动

`ThemeSettings` 是唯一持久化来源 → `AutoTheme` → `CompositionLocal` → 组件读 `LocalUiMode` / `AnkioTheme`。

### 3. 消除无效配置

- Miuix 非 Monet：隐藏「主题色」设置
- 选择具体主题色：自动关闭「跟随系统动态色」

### 4. 组件不偷加布局

默认无多余 padding（如 `ThemeSmallTitle`）。设置项输入：图标/标题在 `TextField` 内部，而非框外重复一行。

### 5. Window 级 Popup

Miuix 的 Spinner、ListPopup 使用 Window 变体，避免必须嵌在 `Scaffold` 里。

### 6. 悬浮窗 Compose 生命周期

`OverlayLifecycleOwner` 为 `ComposeView` 提供 `LifecycleOwner` + `SavedStateRegistryOwner`，用于 Toast、Sheet（Compose Dialog overlay）等脱离 Activity 树的场景。

## Miuix vs Material 差异摘要

| 能力 | Miuix | Material |
|------|-------|----------|
| TopAppBar 折叠 | `MiuixScrollBehavior` | `LargeTopAppBar` + statusBar 同步 |
| TextField | 单 label，图标需包装 padding | Outlined / Filled |
| 下拉/弹窗 | WindowSpinner / WindowListPopup | Material 等价组件 |
| 底部导航 | MiuixNavigationBar | NavigationBar + 顶部分割线 |

## 扩展新组件

1. 在 `compat/` 新增 `ThemeXxx.kt`
2. `when (LocalUiMode.current)` 分支实现
3. 在 `app` 增加 `gallery/XxxSection.kt` 与 `DemoCategory`
4. 更新 [组件总览](components/README.md) 与本 Wiki

## 发布

```bash
git tag 1.0.0 && git push origin 1.0.0
```

JitPack 构建；本地：`./gradlew :lib:publishToMavenLocal`
