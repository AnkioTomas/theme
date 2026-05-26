# 预览与 Demo

## @PreviewAll / @PreviewAllTall / @PreviewAllScreen

8 种主题组合（Miuix/Material × Light/Dark/MonetLight/MonetDark）。注解自带 Studio 视口尺寸，避免单组件预览缩成一小块：

| 注解 | 视口 | 用途 |
|------|------|------|
| `@PreviewAll` | 390×320 dp | 单个控件、设置项 |
| `@PreviewAllTall` | 390×560 dp | 多行排版、色板、多条 Toast |
| `@PreviewAllScreen` | 390×844 dp | 整页（`UiSettingsScreen`、BottomSheet） |

```kotlin
@PreviewAll
@Composable
private fun MyPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) config: ThemePreviewConfig,
) {
    PreviewHost(config) {
        MyContent()
    }
}
```

主题色由 `ThemePreviewConfig.appSettings` 注入；`PreviewAllThemes` 会调用 `ThemeSettings.init`（预览沙箱 SP）。

### lib 组件预览（一组件一 Preview）

每个 `Theme*` 组件的 `@PreviewAll` 写在 **debug 镜像**，目录与 `main` 对齐：

```
lib/src/main/java/net/ankio/theme/compat/Button.kt
lib/src/debug/java/net/ankio/theme/preview/compat/ButtonPreview.kt   # package net.ankio.theme.preview.compat
```

`settings/`、`sheet/`、`toast/` 同理；根目录令牌类见 `preview/ShapePreview.kt`。共享容器：`preview/PreviewHost.kt`（`net.ankio.theme.preview`）。

`ThemeSheet.show` 依赖 Window/生命周期，不提供 Preview。

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
