# 应用布局框架（ThemeApp）

包名：`net.ankio.theme.layout`

把 **Navigation Compose**、**可折叠 TopBar**、**BottomBar**、**预测性返回**、**列表滚动记忆** 收成单一入口。业务侧不写 `NavHost` / `rememberNavController`，只声明有哪些页面、每页如何滚动。

Demo 参考：`app` → `MainActivity.kt`。

---

## 适用场景

| 适合用 `ThemeApp` | 仍用手写 `ThemeTopAppBar` + `NavHost` |
|-------------------|--------------------------------------|
| 多 Tab + 若干子页的标准 App 壳 | 单页、无导航 |
| 需要 TopBar 折叠 + BottomBar | 完全自定义转场 / 多 NavGraph |
| 希望与 Demo 一致的预测性返回 | 嵌入 Fragment、非 Compose 根 |

---

## 依赖

`lib` 已 `api` 暴露 **Navigation Compose**，使用方 `build.gradle.kts` 无需重复声明即可配合 `ThemeApp`：

```kotlin
implementation("com.github.AnkioTomas.theme:lib:VERSION")
```

Activity 建议继承 [`BaseComposeActivity`](getting-started.md)，Manifest 开启预测性返回（见下文）。

---

## 最小示例

```kotlin
class MainActivity : BaseComposeActivity() {
    @Composable
    override fun Content() {
        ThemeApp(start = "home") {
            screen("home", "首页", tab = Icons.Default.Home to "首页") {
                HomeScreen(list = lazyList())
            }
            screen("settings", "设置", tab = Icons.Default.Settings to "设置", collapse = false) {
                scrollColumn {
                    SettingsPanel()
                }
            }
        }
    }
}
```

---

## 界面结构

从上到下，框架固定为四层：

```text
┌─────────────────────────────────────┐
│ ThemeTopAppBar（标题、返回、actions） │
├─────────────────────────────────────┤
│ header { }（可选，按当前 route 显示）  │
├─────────────────────────────────────┤
│ NavHost：当前 screen { } 里的 UI     │
├─────────────────────────────────────┤
│ ThemeNavigationBar（仅 Tab 根页）    │
└─────────────────────────────────────┘
```

实现关系：

```text
ThemeApp
├── ThemeAppShell          # 顶栏 + 内容区 + 底栏布局
│   └── NavHost            # 中间区域
└── ThemeAppBuilder        # screens { screen(...) } 收集路由表
```

---

## ThemeApp 参数

```kotlin
@Composable
fun ThemeApp(
    start: String,                                          // 首屏路由，须与某个 screen 的 route 一致
    modifier: Modifier = Modifier,
    titleAlignment: ThemeTopAppBarTitleAlignment = Center,  // 收起后小标题对齐
    actions: @Composable RowScope.() -> Unit = {},          // TopBar 右侧（全局）
    header: @Composable (route: String?) -> Unit = {},     // TopBar 下方；route 为当前 destination.route
    screens: ThemeAppBuilder.() -> Unit,                   // 注册所有页面
)
```

| 参数 | 说明 |
|------|------|
| `start` | 启动时 `NavHost` 的 `startDestination` |
| `titleAlignment` | 传给 `ThemeTopAppBar`；仅影响收起后小标题 |
| `actions` | 每个页面都会显示（如「应用主题」刷新按钮） |
| `header` | 根据 `route` 决定是否渲染（如仅首页显示标题对齐条） |
| `screens` | DSL：`screen(...)` 注册路由与 UI |

框架自动处理：

- 创建 `NavController`、`NavHost`
- 有上一级时显示返回键并 `popBackStack()`
- 根据当前 `screen` 配置更新标题、大标题、是否折叠 TopBar
- 当前页带 `tab =` 时显示 BottomBar，子页（无 tab）隐藏 BottomBar
- Tab 点击：`saveState` + `restoreState` + `launchSingleTop`

---

## 注册页面：`screen`

在 `screens { }` 内调用，每个 `screen` 对应一条 Nav 路由。

### 固定标题（Tab 根页常用）

```kotlin
screen(
    route = "catalog",
    title = "Theme Demo",           // 收起后小标题
    largeTitle = "Theme 组件目录",   // 展开大标题；默认同 title
    tab = Icons.Filled.Apps to "组件", // 非 null → 底部 Tab；null → 普通子页
    detail = false,                  // true → 详情转场（见下文）
    collapse = true,                 // false → 该页 TopBar 不随滚动折叠
    args = emptyList(),              // 路由参数，见 navArgs
) {
    // ThemeScope.() -> Unit
}
```

### 动态标题（带参路由）

```kotlin
screen(
    route = "category/{categoryName}",
    title = { entry -> entry.categoryTitle() },
    largeTitle = { entry -> entry.categoryTitle() }, // 可省略，默认同 title
    detail = true,
    args = navArgs { string("categoryName") },
) { /* ... */ }
```

| `screen` 参数 | 作用 |
|---------------|------|
| `route` | 与 Navigation 一致，含 `{arg}` 占位符 |
| `tab = icon to label` | 注册为底部 Tab；**仅根页**应带 tab |
| `detail = true` | 使用 `ThemeNavTransitions` 水平滑入/滑出 |
| `collapse = false` | 关闭该页 TopBar 滚动折叠（设置页常用） |
| `args` | `navArgs { string("id") }` 声明路径参数 |

路由参数辅助：

```kotlin
args = navArgs {
    string("categoryName")
    // 可按需扩展 NavArgsBuilder
}
```

---

## 页面内：`ThemeScope`

`screen { }` 大括号内，`this` 为 `ThemeScope`（由框架注入，勿自行创建）。

| API | 说明 |
|-----|------|
| `entry` | 当前 `NavBackStackEntry` |
| `route` | `entry.destination.route` |
| `arg("key")` | 读取 `arguments` 中的 String |
| `go("path")` | `navController.navigate` |
| `back()` | `popBackStack()` |
| `lazyList()` | 列表页：返回 `ThemeLazyListScroll` |
| `scrollColumn { }` | 非列表页：外包 `verticalScroll` + TopBar 联动 |

---

## 滚动：`lazyList` 与 `scrollColumn`

**核心原则**：TopBar 的 nestedScroll 必须挂在**真正发生滚动的那一层**上。  
`ThemeApp` 提供两种接法，对应两种 UI 结构，**不要混用**。

### 对比

| | `lazyList()` | `scrollColumn { }` |
|--|--------------|-------------------|
| **底层** | Compose `LazyColumn`（在**你的组件内部**） | `Box` + `verticalScroll`（在 **ThemeApp 外包**） |
| **适用** | 长列表、懒加载、项多 | `Column` 表单、设置项、内容不长 |
| **调用** | `val list = lazyList()` 传给内部 `LazyColumn` | `scrollColumn { YourColumnContent() }` |
| **状态** | `list.state` 绑定 `NavBackStackEntry`，返回恢复位置 | 无 `LazyListState` |
| **错误用法** | 外面再套 `scrollColumn`（双滚动） | 里面再有 `LazyColumn` 却不挂 `lazyList` |

### 列表页：`ThemeLazyListScroll`

`lazyList()` 返回 **`state` + `modifier` 成对对象**，避免拆成两个参数：

```kotlin
@Stable
class ThemeLazyListScroll(
    val state: LazyListState,
    val modifier: Modifier,  // 默认 fillMaxSize() + nestedScroll
)
```

业务组件建议只收一个参数：

```kotlin
@Composable
fun CatalogScreen(
    list: ThemeLazyListScroll,
    onOpenCategory: (DemoCategory) -> Unit,
) {
    LazyColumn(
        state = list.state,
        modifier = list.modifier,
    ) { /* items */ }
}
```

`screen` 内：

```kotlin
screen("catalog", "标题", tab = Icons.Apps to "组件") {
    CatalogScreen(
        list = lazyList(),
        onOpenCategory = { go("category/${it.name}") },
    )
}
```

### 表单 / 设置页：`scrollColumn`

组件**内部不滚动**（例如 `UiSettingsScreen` 仅为 `Column`）时：

```kotlin
screen("settings", "主题设置", tab = Icons.Settings to "设置", collapse = false) {
    scrollColumn {
        UiSettingsScreen(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            onThemeChanged = ::recreateForThemeChange,
        )
    }
}
```

`collapse = false` 表示该页即使滚动也不折叠 TopBar（Demo 设置页行为）。

### 决策流程

```text
页面根布局是什么？
├─ LazyColumn（在组件内部）
│    → screen { val list = lazyList(); MyList(list) }
└─ Column / 无滚动
     → screen { scrollColumn { MyPanel() } }
```

---

## 底部 Tab

- 在 `screen` 上传 `tab = ImageVector to "文案"` 即注册 Tab。
- 框架收集所有带 `tab` 的 route，渲染 `ThemeNavigationBar`。
- **仅当当前页的 `screen` 配置里含有 `tab`** 时显示底栏；进入 `detail` 子页（无 tab）时自动隐藏。
- 切换 Tab 时框架调用 `topLevelTab`（`saveState` / `restoreState` / `launchSingleTop`）。

---

## 详情页与转场

`detail = true` 时，该路由注册为：

- `enterTransition` / `exitTransition` / `popEnterTransition` / `popExitTransition` = `ThemeNavTransitions.*`

与系统**预测性返回**手势一致，勿在子页手写位移式 `BackHandler`。

---

## 预测性返回

与 [Android 官方指南](https://developer.android.com/guide/navigation/custom-back/predictive-back-gesture) 一致：

### 1. Manifest

```xml
<application android:enableOnBackInvokedCallback="true" ...>
    <activity android:enableOnBackInvokedCallback="true" ... />
</application>
```

### 2. 使用 Nav 回栈

- 返回键：框架 `popBackStack()`
- 子页：`go("detail/...")` 压栈
- 根 Tab 页：无自定义拦截 → 系统返回桌面动画

### 3. 列表滚动位置

`lazyList()` 内部通过 `rememberNavDestinationLazyListState(entry)` 绑定当前 `NavBackStackEntry`，切换 Tab 或从详情返回后列表位置保留。

### 4. TopBar 折叠状态

切换目的地时 `topAppBarScrollKey` 随 `screen` 的 `scrollKey`（或 `entry.id`）变化，避免上一页折叠偏移带到下一页。

---

## Demo 对照（MainActivity）

路由常量：`DemoRoutes`（`catalog` / `settings` / `category/{categoryName}`）。

```kotlin
ThemeApp(
    start = DemoRoutes.Catalog,
    titleAlignment = align,
    actions = { /* 刷新 */ },
    header = { route ->
        if (route == DemoRoutes.Catalog) {
            DemoTitleAlignmentBar(align) { align = it }
        }
    },
) {
    screen(DemoRoutes.Catalog, "Theme Demo", "Theme 组件目录", Icons.Filled.Apps to "组件") {
        CatalogScreen(list = lazyList(), onOpenCategory = { go(DemoRoutes.category(it)) })
    }
    screen(DemoRoutes.Settings, "主题设置", tab = Icons.Filled.Settings to "设置", collapse = false) {
        scrollColumn { UiSettingsScreen(...) }
    }
    screen(
        route = DemoRoutes.Category,
        title = { it.demoCategory()?.title.orEmpty() },
        detail = true,
        args = navArgs { string("categoryName") },
    ) {
        val c = demoCategory() ?: return@screen
        CategoryDetailScreen(category = c, list = lazyList())
    }
}
```

运行 Demo：

```bash
./gradlew :app:installDebug
```

---

## 不用 ThemeApp 时（进阶）

仅需壳层、自行管理 Nav 时，可拆用底层 API：

| API | 用途 |
|-----|------|
| [`ThemeAppShell`](components/navigation.md) | TopBar + 内容槽 + BottomBar |
| [`rememberThemeTopAppBarScroll`](components/navigation.md) | 顶栏折叠 |
| [`ThemeNavTransitions`](components/navigation.md) | 详情转场常量 |
| [`rememberNavDestinationLazyListState`](components/navigation.md) | 列表状态绑定 entry |
| [`NavOptionsBuilder.topLevelTab`](components/navigation.md) | Tab 切换选项 |

单页嵌入 `UiSettingsScreen` 时，外层自行 `verticalScroll` 并 `.then(nestedScroll)`，见 [设置页组件](settings-widgets.md)。

---

## 常见错误

| 现象 | 原因 | 处理 |
|------|------|------|
| TopBar 不折叠 | nestedScroll 未挂在滚动容器上 | 列表用 `lazyList().modifier`；表单用 `scrollColumn` |
| 双滚动 / 手势怪 | `LazyColumn` 外又包 `scrollColumn` | 二选一 |
| 带参标题不对 | 用 `destination.route` 解析 | 用 `title = { entry -> ... }` + `entry.arg()` |
| 底栏在详情页仍显示 | 详情 `screen` 误带 `tab` | 仅 Tab 根页传 `tab` |
| 返回无预测动画 | 未开 `enableOnBackInvokedCallback` 或手写 Back 消费手势 | 用 Nav 回栈 + Manifest |

---

## API 索引

| 类型 | 名称 |
|------|------|
| 入口 | `ThemeApp` |
| DSL | `ThemeAppBuilder.screen` |
| 页面作用域 | `ThemeScope` |
| 列表滚动 | `ThemeLazyListScroll`、`ThemeScope.lazyList()` |
| 普通滚动 | `ThemeScope.scrollColumn` |
| 路由参数 | `navArgs`、`NavArgsBuilder.string` |
| 转场 | `ThemeNavTransitions` |
| 底层壳 | `ThemeAppShell`、`ThemeShellBackButton` |

---

## 相关文档

- [导航组件（ThemeTopAppBar / NavigationBar）](components/navigation.md)
- [设置页 `UiSettingsScreen`](settings-widgets.md)
- [预览与 Demo](preview-and-demo.md)
- [快速开始](getting-started.md)
