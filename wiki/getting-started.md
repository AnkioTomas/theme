# 快速开始

---

## 添加依赖

`settings.gradle.kts`：

```kotlin
maven { url = uri("https://jitpack.io") }
```

`build.gradle.kts`：

```kotlin
implementation("com.github.AnkioTomas.theme:lib:VERSION")
```

`VERSION` 为 Release 标签或 commit hash。

---

## 初始化

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeSettings.init(this)
        ThemeToast.init(this) // 使用 ThemeToast 时
    }
}
```

---

## 推荐：BaseComposeActivity

```kotlin
class MainActivity : BaseComposeActivity() {
    @Composable
    override fun Content() {
        MyScreen()
    }
}
```

自动处理：Edge-to-Edge、夜模式、`AutoTheme`、`LocalUiMode`、根 `ThemeSurface`。

预测性返回需在 Manifest 声明 `android:enableOnBackInvokedCallback="true"`，并在 Compose 中用 `PredictiveBackHandler` / `BackHandler` 处理自定义返回栈（见 Demo `MainActivity`）。

主题变更后：

```kotlin
recreateForThemeChange()
```

---

## 手动包裹主题

```kotlin
setContent {
    CompositionLocalProvider(LocalUiMode provides UiMode.fromValue(ThemeSettings.uiMode)) {
        AutoTheme {
            ThemeSurface(
                modifier = Modifier.fillMaxSize(),
                color = AnkioTheme.colorScheme.surface,
            ) {
                MyScreen()
            }
        }
    }
}
```

---

## 第一个界面

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

---

## 读取当前配置

```kotlin
ThemeSettings.uiMode       // "miuix" | "material"
ThemeSettings.colorMode    // 0–6
ThemeSettings.isDark
ThemeSettings.getAppSettings()
```

---

## Release 混淆（R8 / ProGuard）

库已附带 `lib/consumer-rules.pro`，宿主 App 开启 `isMinifyEnabled = true` 时会**自动合并**，一般无需再手写 keep。

| 文件 | 作用 |
|------|------|
| `lib/consumer-rules.pro` | 随 AAR 下发：公开 API、Miuix、Overlay、资源等 |
| `lib/proguard-rules.pro` | 仅 lib 自身开启混淆时使用 |
| `app/proguard-rules.pro` | Demo 参考：`-include` consumer 规则 + Application/Activity |

注意：`consumer-rules.pro` 中**不要**写 `-renamesourcefileattribute` 等全局项（应放在 App 的 `proguard-rules.pro`）。

---

## 下一步

| 文档 | 内容 |
|------|------|
| [主题体系](theme-system.md) | ColorMode、AnkioTheme、语义色 |
| [组件完整索引](components/README.md) | 全部 Theme* API |
| [设置页组件](settings-widgets.md) | SettingCard、UiSettingsScreen |
| [预览与 Demo](preview-and-demo.md) | @PreviewAll、app 演示 |
