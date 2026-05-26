/*
 * Copyright (C) 2026 ankio(ankio@ankio.net)
 */

package net.ankio.theme.demo.catalog

import androidx.compose.runtime.Composable
import net.ankio.theme.demo.gallery.ButtonsSection
import net.ankio.theme.demo.gallery.ContainersSection
import net.ankio.theme.demo.gallery.DisplaySection
import net.ankio.theme.demo.gallery.FormsSection
import net.ankio.theme.demo.gallery.IconsSection
import net.ankio.theme.demo.gallery.NavigationBarSection
import net.ankio.theme.demo.gallery.NavigationRailSection
import net.ankio.theme.demo.gallery.OverviewSection
import net.ankio.theme.demo.gallery.PopupSection
import net.ankio.theme.demo.gallery.PullToRefreshSection
import net.ankio.theme.demo.gallery.SettingsWidgetsSection
import net.ankio.theme.demo.gallery.ShapesSection
import net.ankio.theme.demo.gallery.TabRowSection
import net.ankio.theme.demo.gallery.TextStylesSection
import net.ankio.theme.demo.gallery.ThemeTokensSection
import net.ankio.theme.demo.gallery.SheetSection
import net.ankio.theme.demo.gallery.ToastSection
import net.ankio.theme.demo.gallery.TopAppBarSection

enum class DemoGroup(val label: String) {
    Foundation("基础"),
    Inputs("输入"),
    Surfaces("容器"),
    Navigation("导航"),
    Feedback("反馈"),
    Settings("设置"),
}

enum class DemoCategory(
    val group: DemoGroup,
    val title: String,
    val summary: String,
) {
    Overview(DemoGroup.Foundation, "主题概览", "CompositionLocal、ThemeSettings、颜色令牌"),
    Buttons(DemoGroup.Foundation, "按钮", "Primary / Secondary / Icon / FAB / ButtonGroup"),
    Icons(DemoGroup.Foundation, "图标", "ImageVector 与 drawable 两种重载"),
    TextStyles(DemoGroup.Foundation, "排版", "AnkioTheme.textStyles 全部 14 种"),
    Shapes(DemoGroup.Foundation, "形状", "MaterialTheme.shapes 五级圆角"),
    Tokens(DemoGroup.Foundation, "颜色令牌", "colorScheme 与 extraColors"),

    Forms(DemoGroup.Inputs, "表单控件", "Switch / Checkbox / Slider / TextField / NumberPicker"),
    Popup(DemoGroup.Inputs, "下拉与搜索", "SuperDropdown / Spinner / ListPopup / SearchBar"),

    Containers(DemoGroup.Surfaces, "容器", "Surface / Card / Divider / FloatingToolbar"),
    Display(DemoGroup.Surfaces, "进度", "Linear / Circular Progress、SmallTitle"),

    TopAppBar(DemoGroup.Navigation, "顶部栏", "标题对齐、滚动折叠、系统栏"),
    TabRow(DemoGroup.Navigation, "标签栏", "ThemeTabRow 切换"),
    NavigationBar(DemoGroup.Navigation, "底部导航", "ThemeNavigationBar + Item"),
    NavigationRail(DemoGroup.Navigation, "侧边导航", "ThemeNavigationRail 大屏布局"),
    PullToRefresh(DemoGroup.Navigation, "下拉刷新", "ThemePullToRefresh"),

    Toast(DemoGroup.Feedback, "Toast", "ThemeToast 与权限说明"),
    Sheet(DemoGroup.Feedback, "底部弹层", "ThemeSheet / ThemeBottomSheet"),

    SettingsWidgets(DemoGroup.Settings, "设置项组件", "SettingCard / Switch / Dropdown 等"),
    ;

    companion object {
        fun byGroup(): List<Pair<DemoGroup, List<DemoCategory>>> =
            DemoGroup.entries.map { group ->
                group to entries.filter { it.group == group }
            }
    }
}

@Composable
fun DemoCategory.Content() {
    when (this) {
        DemoCategory.Overview -> OverviewSection()
        DemoCategory.Buttons -> ButtonsSection()
        DemoCategory.Icons -> IconsSection()
        DemoCategory.TextStyles -> TextStylesSection()
        DemoCategory.Shapes -> ShapesSection()
        DemoCategory.Tokens -> ThemeTokensSection()
        DemoCategory.Forms -> FormsSection()
        DemoCategory.Popup -> PopupSection()
        DemoCategory.Containers -> ContainersSection()
        DemoCategory.Display -> DisplaySection()
        DemoCategory.TopAppBar -> TopAppBarSection()
        DemoCategory.TabRow -> TabRowSection()
        DemoCategory.NavigationBar -> NavigationBarSection()
        DemoCategory.NavigationRail -> NavigationRailSection()
        DemoCategory.PullToRefresh -> PullToRefreshSection()
        DemoCategory.Toast -> ToastSection()
        DemoCategory.Sheet -> SheetSection()
        DemoCategory.SettingsWidgets -> SettingsWidgetsSection()
    }
}
