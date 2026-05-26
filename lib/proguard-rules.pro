# =============================================================================
# lib 模块自身 release 混淆（isMinifyEnabled = true 时生效）
# =============================================================================

-include 'consumer-rules.pro'

-renamesourcefileattribute SourceFile

# 允许收缩未使用的 internal 实现（AnimatedSheetOverlay、OverlayWindow 等）
-keep,allowobfuscation,allowshrinking class net.ankio.theme.sheet.AnimatedSheetOverlay { *; }
-keep,allowobfuscation,allowshrinking class net.ankio.theme.sheet.OverlayWindowKt { *; }

# debug 预览不参与 release 打包（仅 debug source set），无需 keep
