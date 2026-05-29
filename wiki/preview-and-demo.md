# 预览与 Demo

---

## 预测性返回（Demo App）

详见 **[应用布局框架](layout.md#预测性返回)**。Demo 要点：

1. Manifest：`android:enableOnBackInvokedCallback="true"`
2. **`ThemeApp`** 内部创建 `NavHost`，路由：`catalog` / `settings` / `category/{categoryName}`
3. 根 Tab 无自定义 `BackHandler` → 系统返回桌面预测动画
4. 详情页 `detail = true` → `ThemeNavTransitions` 水平转场

| 场景 | 行为 |
|------|------|
| 分类详情 | 框架返回键 `popBackStack()` + 详情转场 |
| Tab 切换 | `topLevelTab`（`saveState` / `restoreState`） |
| 组件目录 | 系统处理返回 |

### 状态与顶栏（与代码一致）

| 状态 | 实现 |
|------|------|
| 壳层 | `ThemeApp` → `ThemeAppShell` + `NavHost` |
| 顶栏标题 | 带参页 `title = { entry -> ... }`；勿解析 `destination.route` 模板 |
| 列表滚动 | `lazyList()` → `ThemeLazyListScroll` |
| 设置滚动 | `scrollColumn { UiSettingsScreen }`，`collapse = false` |
| 标题对齐 | `header` 仅在 `route == catalog` 时显示 `DemoTitleAlignmentBar` |

---

## @PreviewAll 系列

8 种主题组合（Miuix/Material × Light/Dark/MonetLight/MonetDark）。

| 注解 | 视口 | 用途 |
|------|------|------|
| `@PreviewAll` | 390×320 dp | 单控件、设置行 |
| `@PreviewAllTall` | 390×560 dp | 多行、色板、NumberPicker |
| `@PreviewAllScreen` | 390×844 dp | 整页、`UiSettingsScreen`、BottomSheet |

```kotlin
@PreviewAll
@Composable
private fun MyPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        // 必须在 PreviewHost / PreviewAllThemes 内使用 AnkioTheme
        MyComponent()
    }
}
```

### ThemePreviewConfig

```kotlin
data class ThemePreviewConfig(
    val name: String,
    val appSettings: AppSettings,
    val darkConfig: Boolean,
    val uiMode: UiMode,
)
```

### PreviewHost

```kotlin
// net.ankio.theme.preview.PreviewHost
PreviewHost(config, contentPadding = 16.dp) {
    ThemePrimaryButton(onClick = {}, text = "OK")
}
```

内部：`PreviewAllThemes` → `ThemeSettings.init` + `LocalUiMode` + `AutoTheme`。

**注意**：勿在 `PreviewHost` **外**读取 `AnkioTheme.colorScheme`（会触发 `AppExtraColors` 未初始化）。

### 不提供 Preview 的场景

- `ThemeSheet.show`（Window + 生命周期）
- `ThemeToast` overlay（权限 + WindowManager）

可预览：`ThemeBottomSheet`、`SheetContainer` 内容样式。

---

## Debug 预览目录

与 `main` 镜像对齐：

```text
lib/src/main/.../compat/Button.kt
lib/src/debug/.../preview/compat/ButtonPreview.kt
```

| 目录 | 内容 |
|------|------|
| `preview/compat/` | 全部 Theme* compat 预览 |
| `preview/settings/` | SettingCard、ThemeSetting*（含 TextField/Combo 框旁按钮）、UiSettingsScreen |
| `preview/sheet/` | `ThemeBottomSheet`、`SheetContainer`（顶圆角 / 全圆角） |
| `preview/toast/` | Toast 样式（非 overlay） |
| `preview/ShapePreview.kt` | 形状令牌 |

---

## Demo App

模块：`app`，包 `net.ankio.theme.demo`。

### 运行

```bash
./gradlew :app:installDebug
```

### 主界面

```text
MainActivity
└── ThemeApp(start = catalog)
    ├── actions / header（TopBar 全局与按路由）
    ├── screen catalog  → CatalogScreen(lazyList)
    ├── screen settings → scrollColumn { UiSettingsScreen }
    ├── screen category → CategoryDetailScreen(lazyList), detail = true
    └── BottomBar（仅 catalog / settings Tab 时显示）
```

完整 API 见 [layout.md](layout.md)。

### 组件目录（DemoCategory）

| 分组 | 分类示例 |
|------|----------|
| 基础 | 主题概览、按钮、图标、排版、形状、颜色令牌 |
| 输入 | 表单、下拉与搜索 |
| 容器 | 容器、进度 |
| 导航 | 顶栏、标签、底栏、侧栏、下拉刷新 |
| 反馈 | Toast、底部弹层 |
| 设置 | 设置项组件、UiSettingsScreen |

每个 Section 对应 `demo/gallery/*Section.kt`，与 [组件文档](components/README.md) 一一对应。

---

## 自定义预览参数

仿 `ThemePreviewParameterProvider` 增减 `ThemePreviewConfig`，或参考 `DemoPreviewParameterProvider`（精简 3 主题）。
