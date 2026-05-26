# 预览与 Demo

---

## 预测性返回（Demo App）

与 [Android 官方指南](https://developer.android.com/guide/navigation/custom-back/predictive-back-gesture) 一致：

1. Manifest：`android:enableOnBackInvokedCallback="true"`
2. **Navigation Compose** `NavHost` 管理 `catalog` / `settings` / `category/{name}` 返回栈
3. 根目的地 `catalog` 无自定义 `BackHandler` → 系统「返回主屏幕」预测动画（Android 15+ 默认启用）
4. 仅当需要**自定义**滑动进度动画时，才在局部使用 `PredictiveBackHandler`（Demo 未使用手写位移）

| 场景 | 行为 |
|------|------|
| 分类详情 | `navController.popBackStack()` + Nav 默认 slide 转场 |
| 设置 Tab | `navigate` + `saveState` / `restoreState` / `launchSingleTop` |
| 组件目录 | 系统处理返回（无自定义 `BackHandler`） |

### 状态与顶栏（与代码一致）

| 状态 | 实现 |
|------|------|
| 路由 | `catalog` · `settings` · `category/{categoryName}` |
| 顶栏标题 | `arguments["categoryName"]` → `DemoCategory.title`；勿解析 `destination.route` 模板 |
| 大标题折叠 | `DemoAppShell` + `rememberThemeTopAppBarScroll`；`nestedScroll` 挂到各页 `LazyColumn` |
| 折叠重置 | `topAppBarScrollKey` 随目的地变化（`key` 包裹 scroll） |
| 列表滚动 | `rememberSaveable(NavBackStackEntry, …)` 绑定返回栈条目 |
| 标题对齐 | 仅 `catalog` 显示「居左 / 居中」；选中项 `Primary`、未选 `Secondary` 按钮 |

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
└── DemoAppShell
    ├── ThemeTopAppBar（catalog / 分类详情可折叠；settings 不折叠）
    ├── TitleAlignmentBar（仅 catalog：居左 / 居中）
    ├── DemoNavHost（NavHost）
    │   ├── catalog → CatalogScreen
    │   ├── settings → UiSettingsScreen
    │   └── category/{categoryName} → CategoryDetailScreen
    └── ThemeNavigationBar（分类详情页隐藏）
```

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
