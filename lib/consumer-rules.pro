# =============================================================================
# net.ankio.theme — consumer ProGuard / R8 rules
# 随 AAR 合并到宿主 App 的 release 构建，请勿删除。
# =============================================================================

# --- 调试栈（行号；rename 仅放在 app/lib proguard-rules，不可写在 consumer 文件）---
-keepattributes SourceFile,LineNumberTable

# --- Kotlin ---
-keepattributes *Annotation*,InnerClasses,EnclosingMethod,Signature,
    RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations,KotlinMetadata
-dontwarn kotlin.**
-dontwarn kotlinx.coroutines.**

# --- 对外 API（object / enum / 可继承入口）---
-keep public class net.ankio.theme.** {
    public <methods>;
    public <fields>;
}
-keep public enum net.ankio.theme.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers class net.ankio.theme.**$Companion {
    public <methods>;
}
-keepclassmembers class net.ankio.theme.** {
    public <init>(...);
}

# 宿主可继承的 Activity 基类
-keep public class * extends net.ankio.theme.BaseComposeActivity {
    public <init>();
    public <methods>;
}

# --- 资源 ---
-keep class net.ankio.theme.R { *; }
-keep class net.ankio.theme.R$* { *; }

# --- Overlay：WindowManager + ComposeView 生命周期树 ---
-keep class net.ankio.theme.compose.OverlayLifecycleOwner { *; }
-keep class androidx.compose.ui.platform.ComposeView { *; }

# --- ThemeSheet / ThemeToast 入口 ---
-keep class net.ankio.theme.sheet.ThemeSheet { *; }
-keep class net.ankio.theme.toast.ThemeToast { *; }
-keep class net.ankio.theme.toast.ThemeToast$* { *; }
-keep class net.ankio.theme.sheet.ThemeSheetShape { *; }

# --- Material DynamicColors（ThemeSettings）---
-keep class com.google.android.material.color.DynamicColors { *; }
-dontwarn com.google.android.material.**

# --- Miuix KMP（api 传递依赖，宿主开启混淆时需保留）---
-keep class top.yukonga.miuix.** { *; }
-keep interface top.yukonga.miuix.** { *; }
-dontwarn top.yukonga.miuix.**

# --- AndroidX Lifecycle / Core（overlay、ThemeSettings）---
-keep class * implements androidx.lifecycle.LifecycleOwner {
    androidx.lifecycle.Lifecycle getLifecycle();
}
-dontwarn androidx.**

# --- Compose Runtime（由 BOM 带入；抑制可选模块告警）---
-dontwarn androidx.compose.**
-dontwarn org.jetbrains.annotations.**
