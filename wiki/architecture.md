# 架构与设计

---

## 模块结构

```text
theme/
├── lib/                         # JitPack 发布
│   ├── src/main/.../theme/
│   │   ├── compat/              # Theme* 组件（Miuix ↔ Material）
│   │   ├── settings/            # 设置页 + UiSettingsScreen
│   │   ├── toast/               # ThemeToast
│   │   ├── sheet/               # ThemeSheet、ThemeBottomSheet
│   │   ├── compose/             # OverlayLifecycleOwner
│   │   ├── util/                # Context 扩展
│   │   ├── Theme.kt             # AutoTheme
│   │   ├── AnkioTheme.kt
│   │   └── ThemeSettings.kt
│   └── src/debug/.../preview/   # @PreviewAll（不进 release）
└── app/                         # 演示 App（不发布）
    └── demo/catalog + gallery
```

---

## 设计原则

### 1. 一套 API，两套引擎

业务只写 `Theme*` + `AnkioTheme`。切换 `ThemeSettings.uiMode` 后 `recreate` Activity，无需改 UI 分支。

### 2. 设置驱动

`ThemeSettings` → `AutoTheme` → `CompositionLocal` → 组件读 `LocalUiMode` / `AnkioTheme`。

### 3. 消除无效配置

- Miuix 非 Monet：设置页隐藏「主题色」
- 选择具体主题色：关闭「跟随系统动态色」

### 4. 组件不偷加布局

默认无多余 padding（如 `ThemeSmallTitle`）。设置项输入：图标/标题在 `TextField` 内部。

### 5. Window 级 Popup

Miuix Spinner / ListPopup 用 Window 变体，不依赖 `Scaffold`。

### 6. Overlay 生命周期

`OverlayLifecycleOwner` 为脱离 Activity 树的 `ComposeView` 提供 `LifecycleOwner` + `SavedStateRegistryOwner`（Toast、Sheet）。Session 内 **WeakReference** 持有 View，避免 static 泄漏。

---

## Miuix vs Material 差异总表

| 能力 | Material | Miuix |
|------|----------|-------|
| 主题根 | `MaterialTheme` | `MiuixTheme` + Material 子树 |
| 默认字色 Local | `MaterialTheme` 提供 | 需 `AutoTheme` 注入 `onSurface` |
| TopAppBar 折叠 | `LargeTopAppBar` + statusBar 渐变 | `MiuixTopAppBar` + scrollBehavior |
| TextField | Outlined / Filled | 单一 `MiuixTextField`，`style` 忽略 |
| NumberPicker | 无 | 两栈均用 Miuix 控件 |
| 下拉 | `Popup` + Menu | `WindowSpinner` |
| 列表弹层 | `Popup` + `Surface` | `WindowListPopup` |
| NavigationBarItem | 自绘 Icon/Text 色 | 原生 Item |
| FAB 容器色 | `primaryContainer` | 显式覆盖（非默认 primary） |
| BottomSheet（树内） | `ModalBottomSheet` | 同左（未用 Miuix sheet） |
| Toast | N/A | overlay 固定 Material 样式 |

完整组件列表见 [组件总览](components/README.md)。

---

## 扩展新组件

1. 在 `compat/` 新增 `ThemeXxx.kt`，`when (LocalUiMode.current)` 分支
2. 颜色/字色用 `AnkioTheme`
3. `app` 增加 `gallery/XxxSection.kt` 与 `DemoCategory`
4. `lib/src/debug/.../preview/compat/XxxPreview.kt` + `@PreviewAll`
5. 更新 [组件索引](components/README.md) 与对应分类 md

---

## 发布

```bash
git tag 1.0.0 && git push origin 1.0.0
```

JitPack 构建；本地：`./gradlew :lib:publishToMavenLocal`
