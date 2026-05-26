# 预览与 Demo

## @PreviewAll

8 种主题组合（Miuix/Material × Light/Dark/MonetLight/MonetDark）：

```kotlin
@PreviewAll
@Composable
private fun MyPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewAllThemes(config) {
        MyContent()
    }
}
```

不依赖 `SharedPreferences`，适合 Android Studio 预览。

### ThemePreviewConfig

```kotlin
data class ThemePreviewConfig(
    val name: String,
    val appSettings: AppSettings,
    val darkConfig: Boolean,
    val uiMode: UiMode,
)
```

自定义变体可仿 `DemoPreviewParameterProvider`（3 种精简主题）。

## Demo App

模块：`app`，包名 `net.ankio.theme.demo`。

### 主界面结构

```
DemoAppShell
├── ThemeTopAppBar（可折叠 + 标题对齐）
├── 内容区（Catalog / 设置 / 分类详情）
└── ThemeNavigationBar（子页面自动隐藏）
```

### 组件目录（DemoCategory）

| 分组 | 分类 |
|------|------|
| 基础 | 主题概览、按钮、图标、排版、形状、颜色令牌 |
| 输入 | 表单控件、下拉与搜索 |
| 容器 | 容器、进度 |
| 导航 | 顶部栏、标签栏、底部导航、侧边导航、下拉刷新 |
| 反馈 | Toast、底部弹层 |
| 设置 | 设置项组件 |

### 运行

```bash
./gradlew :app:installDebug
```

或 Android Studio 运行 `app`。

### 预览入口

`app/.../preview/DemoPreviews.kt`：`SheetPreview`、`DemoCatalogShellPreview` 等。
