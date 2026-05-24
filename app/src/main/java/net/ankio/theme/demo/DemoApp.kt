package net.ankio.theme.demo

import android.app.Application
import net.ankio.theme.ThemeSettings

class DemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeSettings.init(this)
    }
}
