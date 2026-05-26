# 快速开始

## 添加依赖

`settings.gradle.kts`：

```kotlin
maven { url = uri("https://jitpack.io") }
```

`build.gradle.kts`：

```kotlin
implementation("com.github.AnkioTomas.theme:lib:VERSION")
```

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

## 推荐：BaseComposeActivity

```kotlin
class MainActivity : BaseComposeActivity() {
    @Composable
    override fun Content() {
        MyScreen()
    }
}
```

自动处理：`enableEdgeToEdge`、夜模式、`AutoTheme`、`LocalUiMode`、`ThemeSurface` 包裹。

主题变更后：

```kotlin
recreateForThemeChange()
```

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

## 读取当前配置

```kotlin
ThemeSettings.uiMode       // "miuix" | "material"
ThemeSettings.colorMode    // 0–6
ThemeSettings.isDark       // 是否深色
ThemeSettings.getAppSettings() // 供 AutoTheme 使用
```

## 下一步

- [主题体系](theme-system.md)
- [组件总览](components/README.md)
- [设置页组件](settings-widgets.md)
