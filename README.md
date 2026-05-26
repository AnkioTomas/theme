# Ankio Theme

Compose 主题与组件库：在 **Miuix KMP** 与 **Material 3** 之间提供统一 API。业务只写一套 `Theme*` 组件，用户在设置里切换 UI 引擎与颜色模式，无需改界面代码。

[![JitPack](https://jitpack.io/v/AnkioTomas/theme.svg)](https://jitpack.io/#AnkioTomas/theme)

---

## 文档

**完整说明、每个组件的 API / 参数 / 双栈差异，见 [Wiki](wiki/README.md)。**

| 你想… | 从这里开始 |
|--------|------------|
| 接入项目 | [快速开始](wiki/getting-started.md) |
| 查某个 `Theme*` 组件 | [组件完整索引](wiki/components/README.md) |
| 主题 / ColorMode / 语义色 | [主题体系](wiki/theme-system.md) |
| 设置页、`UiSettingsScreen` | [设置页组件](wiki/settings-widgets.md) |
| `ThemeSheet` / `ThemeToast` | [底部弹层](wiki/sheet.md) · [Toast](wiki/toast.md) |
| Studio 预览、`@PreviewAll` | [预览与 Demo](wiki/preview-and-demo.md) |
| 架构与 Miuix/Material 差异 | [架构与设计](wiki/architecture.md) |

---

## 特性

- **一套 API，两套引擎** — `LocalUiMode` 在 Miuix / Material 间切换，业务不写 `if (Miuix)`。
- **统一令牌** — `AnkioTheme.colorScheme`、`AnkioTheme.textStyles`、语义色 `semantic.*`。
- **40+ 兼容组件** — 按钮、表单、导航、弹窗、进度等，见 [组件索引](wiki/components/README.md)。
- **内置设置页** — `UiSettingsScreen` 可嵌入，改主题后 `recreateForThemeChange()`。
- **Overlay 能力** — `ThemeSheet.show(context)`、`ThemeToast`（`TYPE_APPLICATION_OVERLAY`；Toast 可降级系统 Toast，Sheet 需悬浮窗权限）。
- **8 主题预览** — `lib` debug 源集 `@PreviewAll`，覆盖 Miuix/Material × 浅/深/Monet。

---

## 环境要求

| 项 | 值 |
|---|---|
| minSdk | 30 |
| Java | 17 |
| Compose | BOM 2025.08+ |
| 依赖 | Miuix KMP 0.8.7、Material 3 |

---

## 快速接入

### 1. JitPack

`settings.gradle.kts`：

```kotlin
maven { url = uri("https://jitpack.io") }
```

`build.gradle.kts`：

```kotlin
implementation("com.github.AnkioTomas.theme:lib:VERSION")
```

`VERSION` 为 [Release 标签](https://github.com/AnkioTomas/theme/releases) 或 commit hash。

### 2. 初始化

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeSettings.init(this)
        ThemeToast.init(this) // 使用 ThemeToast 时
    }
}
```

### 3. Activity（推荐）

```kotlin
class MainActivity : BaseComposeActivity() {
    @Composable
    override fun Content() {
        MyScreen() // 已包裹 AutoTheme + ThemeSurface
    }
}
```

主题变更后：`recreateForThemeChange()`

手动包裹、`ThemeSettings` 字段说明见 [快速开始](wiki/getting-started.md)。

---

## 最小示例

```kotlin
@Composable
fun MyScreen() {
    Column(Modifier.padding(16.dp)) {
        ThemePrimaryButton(onClick = {}, text = "确认")
        ThemeTextField(
            value = text,
            onValueChange = { text = it },
            label = "用户名",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
```

约定：`ThemeText` 需显式 `color`；按钮内优先 `ThemeButtonLabel` 或 `text` 重载。详见 [Wiki 通用约定](wiki/README.md)。

---

## 组件一览（链到 Wiki）

| 分类 | 文档 | 代表 API |
|------|------|----------|
| 按钮 | [buttons](wiki/components/buttons.md) | `ThemePrimaryButton`, `ThemeButtonGroup`, `ThemeFloatingActionButton` |
| 文本/图标 | [text-icons](wiki/components/text-icons.md) | `ThemeText`, `ThemeIcon`, `ThemeSmallTitle` |
| 容器 | [containers](wiki/components/containers.md) | `ThemeSurface`, `ThemeCard`, `ThemeFloatingToolbar` |
| 表单 | [forms](wiki/components/forms.md) | `ThemeTextField`, `ThemeSearchBar`, `ThemeSuperSpinner` |
| 导航 | [navigation](wiki/components/navigation.md) | `ThemeTopAppBar`, `ThemeNavigationBar`, `ThemeTabRow` |
| 反馈 | [feedback](wiki/components/feedback.md) | `ThemePullToRefresh`, `ThemeProgressIndicator` |
| 弹窗 | [popups](wiki/components/popups.md) | `ThemeSuperListPopup`, `ThemeSuperDropdown` |
| 设置 | [settings-widgets](wiki/settings-widgets.md) | `SettingCard`, `UiSettingsScreen` |
| Sheet | [sheet](wiki/sheet.md) | `ThemeSheet.show`, `ThemeBottomSheet` |
| Toast | [toast](wiki/toast.md) | `ThemeToast.show` |

完整列表（含每个参数说明）：[wiki/components/README.md](wiki/components/README.md)

---

## 演示 App

```bash
./gradlew :app:installDebug
```

`app` 模块提供 **组件目录**（按钮、表单、导航、Toast、Sheet 等），与 Wiki 分类一一对应。结构见 [预览与 Demo](wiki/preview-and-demo.md)。

---

## 仓库结构

```text
theme/
├── lib/              # JitPack 发布模块（net.ankio.theme.*）
│   └── src/debug/    # @PreviewAll 预览（不进 release）
└── app/              # 交互演示（不发布）
```

---

## 发布

```bash
git tag 1.0.0 && git push origin 1.0.0
```

在 [JitPack](https://jitpack.io/#AnkioTomas/theme) 等待构建。本地：`./gradlew :lib:publishToMavenLocal`

---

## License

Apache License 3.0 — 见 [LICENSE](LICENSE)。
