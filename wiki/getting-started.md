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

## 下一步

| 文档 | 内容 |
|------|------|
| [主题体系](theme-system.md) | ColorMode、AnkioTheme、语义色 |
| [组件完整索引](components/README.md) | 全部 Theme* API |
| [设置页组件](settings-widgets.md) | SettingCard、UiSettingsScreen |
| [预览与 Demo](preview-and-demo.md) | @PreviewAll、app 演示 |
