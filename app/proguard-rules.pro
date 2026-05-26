# =============================================================================
# Demo App release 混淆
# =============================================================================

-include '../lib/consumer-rules.pro'

-renamesourcefileattribute SourceFile

# Demo 入口
-keep class net.ankio.theme.demo.** { *; }
-keep class net.ankio.theme.demo.DemoApp { *; }
-keep class net.ankio.theme.demo.MainActivity { *; }

# Application / Activity
-keep public class * extends android.app.Application {
    public <init>();
}
-keep public class * extends androidx.activity.ComponentActivity {
    public <init>();
}

# Compose Preview（debug 不打进 release，防 R8 误报）
-dontwarn androidx.compose.ui.tooling.**
