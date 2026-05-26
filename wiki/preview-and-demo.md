# 预览与 Demo

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
| `preview/settings/` | SettingCard、ThemeSetting*、UiSettingsScreen |
| `preview/sheet/` | ThemeBottomSheet |
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
DemoAppShell
├── ThemeTopAppBar（可折叠）
├── 内容：组件目录 / 设置 / 分类详情
└── ThemeNavigationBar（子页可隐藏）
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
